package com.hientran.sohebox.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hientran.sohebox.cache.EnglishTypeCache;
import com.hientran.sohebox.constants.DBConstants;
import com.hientran.sohebox.constants.ResponseCode;
import com.hientran.sohebox.constants.enums.EnglishTblEnum;
import com.hientran.sohebox.entity.EnglishLearnReportTbl;
import com.hientran.sohebox.entity.EnglishTbl;
import com.hientran.sohebox.exception.APIResponse;
import com.hientran.sohebox.repository.EnglishLearnReportRepository;
import com.hientran.sohebox.repository.EnglishRepository;
import com.hientran.sohebox.sco.EnglishLearnReportSCO;
import com.hientran.sohebox.sco.EnglishSCO;
import com.hientran.sohebox.sco.SearchNumberVO;
import com.hientran.sohebox.sco.SearchTextVO;
import com.hientran.sohebox.transformer.EnglishTransformer;
import com.hientran.sohebox.transformer.EnglishTypeTransformer;
import com.hientran.sohebox.utils.FileUtils;
import com.hientran.sohebox.vo.DownloadFileVO;
import com.hientran.sohebox.vo.EnglishLearnReportVO;
import com.hientran.sohebox.vo.EnglishTypeVO;
import com.hientran.sohebox.vo.EnglishVO;
import com.hientran.sohebox.vo.PageResultVO;

import lombok.RequiredArgsConstructor;

/**
 * @author hientran
 */
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class EnglishService extends BaseService {
	private final EnglishRepository englishRepository;
	private final EnglishTransformer englishTransformer;
	private final EnglishTypeCache englishTypeCache;
	private final EnglishTypeTransformer englishTypeTransformer;
	private final UserService userService;
	private final EnglishLearnReportRepository englishLearnReportRepository;

	@Value("${web.asset.english.image.path}")
	private String WEB_ENGLISH_IMAGE_PATH;

	/**
	 * 
	 * Create
	 * 
	 * @param vo
	 * @return
	 * @throws IOException
	 */
	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public APIResponse<Long> create(EnglishVO vo) {
		// Declare result
		APIResponse<Long> result = new APIResponse<Long>();

		// Validate input
		if (result.getStatus() == null) {
			List<String> errors = new ArrayList<>();

			// Keyword must not null
			if (StringUtils.isBlank(vo.getKeyWord())) {
				errors.add(ResponseCode.mapParam(ResponseCode.FILED_EMPTY, EnglishTblEnum.keyWord.name()));
			}

			// Category must not null
			if (vo.getCategory() == null) {
				errors.add(ResponseCode.mapParam(ResponseCode.FILED_EMPTY, EnglishTblEnum.category.name()));
			}

			// Image file must not null
			if (StringUtils.isBlank(vo.getImageFile())) {
				errors.add(ResponseCode.mapParam(ResponseCode.FILED_EMPTY, EnglishTblEnum.imageFile.name()));
			}

			// Vus grade must not null
			if (vo.getVusGrade() == null) {
				errors.add(ResponseCode.mapParam(ResponseCode.FILED_EMPTY, EnglishTblEnum.vusGrade.name()));
			}

			// learn day must not null
			if (vo.getLearnDay() == null) {
				errors.add(ResponseCode.mapParam(ResponseCode.FILED_EMPTY, EnglishTblEnum.learnDay.name()));
			}

			// Record error
			if (CollectionUtils.isNotEmpty(errors)) {
				result = new APIResponse<Long>(HttpStatus.BAD_REQUEST, errors);
			}
		}

		// Check if record existed already
		if (result.getStatus() == null) {
			if (recordIsExisted(vo.getKeyWord())) {
				result = new APIResponse<Long>(HttpStatus.BAD_REQUEST,
						ResponseCode.mapParam(ResponseCode.EXISTED_RECORD, "word <" + vo.getKeyWord() + ">"));
			}
		}

		////////////////
		// Save image //
		////////////////
		String imageName = formatImageName(vo.getKeyWord(), vo.getImageExtention());
		if (result.getStatus() == null) {
			if (vo.getImageFile() != null) {
				try {
					updateImage(vo.getImageFile(), imageName);
				} catch (Exception e) {
					result = new APIResponse<Long>(HttpStatus.BAD_REQUEST,
							ResponseCode.mapParam(ResponseCode.ERROR_EXCEPTION, e.getMessage()));
				}

			}
		}

		/////////////////////
		// Record new word //
		/////////////////////
		if (result.getStatus() == null) {
			// Transform
			EnglishTbl tbl = englishTransformer.convertToTbl(vo);

			// Format keyword
			tbl.setKeyWord(formatKeyWord(vo.getKeyWord().toLowerCase()));

			// Set category
			EnglishTypeVO category = englishTypeCache.getType(DBConstants.TYPE_CLASS_ENGLISH_CATEGORY,
					vo.getCategory().getTypeCode());
			tbl.setCategory(englishTypeTransformer.convertToEnglishTypeTbl(category));

			// Set word level
			EnglishTypeVO wordLevel = englishTypeCache.getType(DBConstants.TYPE_CLASS_ENGLISH_WORD_LEVEL,
					setWordLevel(vo.getKeyWord()));
			tbl.setWordLevel(englishTypeTransformer.convertToEnglishTypeTbl(wordLevel));

			// Set image name
			tbl.setImageName(imageName);

			// Set explanation en if null
			if (tbl.getExplanationEn() == null) {
				tbl.setExplanationEn(tbl.getKeyWord());
			}

			// Set grade
			EnglishTypeVO vusGrade = englishTypeCache.getType(DBConstants.TYPE_CLASS_ENGLISH_VUS_GRADE,
					vo.getVusGrade().getTypeCode());
			tbl.setVusGrade(englishTypeTransformer.convertToEnglishTypeTbl(vusGrade));

			// Set learn day
			EnglishTypeVO learnDay = englishTypeCache.getType(DBConstants.TYPE_CLASS_ENGLISH_LEARN_DAY,
					vo.getLearnDay().getTypeCode());
			tbl.setLearnDay(englishTypeTransformer.convertToEnglishTypeTbl(learnDay));

			// Create User
			tbl = englishRepository.save(tbl);

			// Set id return
			result.setData(tbl.getId());

			// Write activity type "create word"
			recordUserActivity(DBConstants.USER_ACTIVITY_ENGLISH_CREATE);
		}

		// Return
		return result;
	}

	/**
	 * Search
	 * 
	 * @param sco
	 * @return
	 */
	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public APIResponse<Object> search(EnglishSCO sco) {
		// Declare result
		APIResponse<Object> result = new APIResponse<Object>();

		// Get data
		Page<EnglishTbl> page = englishRepository.findAll(sco);

		// Transformer
		PageResultVO<EnglishVO> data = englishTransformer.convertToPageReturn(page);

		// Set data return
		result.setData(data);

		// Write activity type "English access"
		recordUserActivity(DBConstants.USER_ACTIVITY_ENGLISH_ACCESS);

		// Return
		return result;
	}

	/**
	 * 
	 * Update
	 * 
	 * @param vo
	 * @return
	 */
	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public APIResponse<Long> update(EnglishVO vo) {
		// Declare result
		APIResponse<Long> result = new APIResponse<Long>();

		// Validate input
		if (result.getStatus() == null) {
			List<String> errors = new ArrayList<>();

			// Keyword must not null
			if (StringUtils.isBlank(vo.getKeyWord())) {
				errors.add(ResponseCode.mapParam(ResponseCode.FILED_EMPTY, EnglishTblEnum.keyWord.name()));
			}

			// Record error
			if (CollectionUtils.isNotEmpty(errors)) {
				result = new APIResponse<Long>(HttpStatus.BAD_REQUEST, errors);
			}
		}

		// Get the old record
		EnglishTbl updateTbl = null;
		if (result.getStatus() == null) {
			updateTbl = getByKey(vo.getKeyWord());
			if (updateTbl == null) {
				result = new APIResponse<Long>(HttpStatus.BAD_REQUEST,
						ResponseCode.mapParam(ResponseCode.INEXISTED_RECORD, "word <" + vo.getKeyWord() + ">"));
			}
		}

		// PROCESS UPDATE

		////////////////
		// Save image //
		////////////////
		String imageName = formatImageName(vo.getKeyWord(), vo.getImageExtention());
		if (result.getStatus() == null) {
			if (vo.getImageFile() != null) {
				try {
					updateImage(vo.getImageFile(), imageName);
				} catch (Exception e) {
					result = new APIResponse<Long>(HttpStatus.BAD_REQUEST,
							ResponseCode.mapParam(ResponseCode.ERROR_EXCEPTION, e.getMessage()));
				}
			}
		}

		/////////////////////
		// Update word //
		/////////////////////
		if (result.getStatus() == null) {

			// Set category
			if (vo.getCategory() != null) {
				EnglishTypeVO category = englishTypeCache.getType(DBConstants.TYPE_CLASS_ENGLISH_CATEGORY,
						vo.getCategory().getTypeCode());
				updateTbl.setCategory(englishTypeTransformer.convertToEnglishTypeTbl(category));
			}

			// Set word level
			if (vo.getKeyWord() != null) {
				EnglishTypeVO wordLevel = englishTypeCache.getType(DBConstants.TYPE_CLASS_ENGLISH_WORD_LEVEL,
						setWordLevel(vo.getKeyWord()));
				updateTbl.setWordLevel(englishTypeTransformer.convertToEnglishTypeTbl(wordLevel));
			}

			// Set image name
			if (vo.getImageFile() != null) {
				updateTbl.setImageName(imageName);
			}

			// Set en explanation
			if (vo.getExplanationEn() != null) {
				updateTbl.setExplanationEn(vo.getExplanationEn());
			}

			// Set vn explanation
			if (vo.getExplanationVn() != null) {
				updateTbl.setExplanationVn(vo.getExplanationVn());
			}

			// Set voice UK file name
			if (vo.getVoiceUkFile() != null) {
				updateTbl.setVoiceUkFile(vo.getVoiceUkFile());
			}

			// Set voice us file name
			if (vo.getVoiceUsFile() != null) {
				updateTbl.setVoiceUsFile(vo.getVoiceUsFile());
			}

			// Set grade
			if (vo.getVusGrade() != null) {
				EnglishTypeVO vusGrade = englishTypeCache.getType(DBConstants.TYPE_CLASS_ENGLISH_VUS_GRADE,
						vo.getVusGrade().getTypeCode());
				updateTbl.setVusGrade(englishTypeTransformer.convertToEnglishTypeTbl(vusGrade));
			}

			// Set learn day
			if (vo.getLearnDay() != null) {
				EnglishTypeVO learnDay = englishTypeCache.getType(DBConstants.TYPE_CLASS_ENGLISH_LEARN_DAY,
						vo.getLearnDay().getTypeCode());
				updateTbl.setLearnDay(englishTypeTransformer.convertToEnglishTypeTbl(learnDay));
			}

			// Update word
			updateTbl = englishRepository.save(updateTbl);

			// Set id return
			result.setData(updateTbl.getId());

			// Write activity type "update word"
			recordUserActivity(DBConstants.USER_ACTIVITY_ENGLISH_UPDATE);
		}

		// Return
		return result;
	}

	/**
	 * Get by id
	 * 
	 * @param User
	 * @return
	 */
	public APIResponse<Object> getById(Long id) {
		// Declare result
		APIResponse<Object> result = new APIResponse<Object>();

		// Check existence
		Optional<EnglishTbl> englishTbl = englishRepository.findById(id);
		if (englishTbl.isPresent()) {
			EnglishVO vo = englishTransformer.convertToVO(englishTbl.get());
			result.setData(vo);
		} else {
			result = new APIResponse<Object>(HttpStatus.BAD_REQUEST,
					ResponseCode.mapParam(ResponseCode.INEXISTED_RECORD, "english"));
		}

		// Return
		return result;
	}

	/**
	 * 
	 * Update image physical file
	 *
	 * @param imageFile
	 * @param imageName
	 * @throws IOException
	 */
	private void updateImage(String imageFile, String imageName) throws IOException {
		// Create folder if not existed
		FileUtils.createDirsIfNotExist(WEB_ENGLISH_IMAGE_PATH);

		// Write file
		String[] imageDataBase = imageFile.split(";");
		String[] imageData = imageDataBase[1].split(",");
		InputStream photoStream = FileUtils.getStreamFromString(imageData[1]);
		if (photoStream != null) {
			String filePath = WEB_ENGLISH_IMAGE_PATH + imageName;

			// Delete if image is existed
			File photo = new File(filePath);
			if (photo.exists()) {
				FileUtils.deleteFile(filePath);
			}

			// Write
			FileUtils.writeFile(photoStream, photo);
		}
	}

	/**
	 * Define level of keyword
	 *
	 * @param vo
	 * @return
	 */
	private String setWordLevel(String keyWord) {
		String result;

		switch (keyWord.length()) {
		case 1:
		case 2:
			result = DBConstants.ENGLISH_LEVEL_1;
			break;

		case 3:
		case 4:
			result = DBConstants.ENGLISH_LEVEL_2;
			break;

		case 5:
		case 6:
			result = DBConstants.ENGLISH_LEVEL_3;
			break;

		case 7:
		case 8:
			result = DBConstants.ENGLISH_LEVEL_4;
			break;

		default:
			result = DBConstants.ENGLISH_LEVEL_5;
			break;
		}

		return result;
	}

	/**
	 * 
	 * Check if record is existed
	 *
	 * @param keyWord
	 * @return
	 */
	private boolean recordIsExisted(String keyWord) {
		// Declare result
		Boolean result = false;

		SearchTextVO keyWordSearch = new SearchTextVO();
		keyWordSearch.setEq(formatKeyWord(keyWord));

		EnglishSCO sco = new EnglishSCO();
		sco.setKeyWord(keyWordSearch);

		// Get data
		List<EnglishTbl> list = englishRepository.findAll(sco).getContent();
		if (CollectionUtils.isNotEmpty(list)) {
			result = true;
		}

		// Return
		return result;
	}

	/**
	 * 
	 * Get record by key
	 *
	 * @param key
	 * @return
	 */
	public EnglishTbl getByKey(String key) {
		// Declare result
		EnglishTbl result = null;

		SearchTextVO keyWordSearch = new SearchTextVO();
		keyWordSearch.setEq(formatKeyWord(key));

		EnglishSCO sco = new EnglishSCO();
		sco.setKeyWord(keyWordSearch);

		// Get data
		List<EnglishTbl> list = englishRepository.findAll(sco).getContent();
		if (CollectionUtils.isNotEmpty(list)) {
			result = list.get(0);
		}

		// Return
		return result;
	}

	/**
	 * Format key word
	 *
	 * @param vo
	 * @return
	 */
	private String formatKeyWord(String keyWord) {
		return keyWord.toLowerCase();
	}

	/**
	 * Format image name
	 *
	 * @param keyWord
	 * @return
	 */
	private String formatImageName(String keyWord, String imageExtention) {
		return keyWord.toLowerCase().replaceAll(" ", "_") + "." + imageExtention;
	}

	/**
	 * Search with low learn
	 * 
	 * @param sco
	 * @return
	 */
	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public APIResponse<Object> searchLowLearn(EnglishSCO sco) {
		// Declare result
		APIResponse<Object> result = new APIResponse<Object>();

		// Get data
		List<Object[]> searchResult = englishRepository.findLowLearn(sco, entityManager);

		// Transformer
		if (CollectionUtils.isNotEmpty(searchResult)) {
			// Format data
			List<EnglishVO> listElement = new ArrayList<>();
			for (Object[] objects : searchResult) {
				listElement.add(englishTransformer.convertToVO(objects));
			}

			// Transformer
			PageResultVO<EnglishVO> data = new PageResultVO<EnglishVO>();
			data.setElements(listElement);
			data.setCurrentPage(0);
			data.setTotalPage(1);
			data.setTotalElement(listElement.size());

			// Set data return
			result.setData(data);
		}

		// Write activity type "english access"
		recordUserActivity(DBConstants.USER_ACTIVITY_ENGLISH_ACCESS);

		// Return
		return result;
	}

	/**
	 * Download a URL mp3 file
	 * 
	 * @param sco
	 * @return
	 * @throws IOException
	 */
	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public APIResponse<Object> downloadFileMp3(DownloadFileVO vo) {
		// Declare result
		APIResponse<Object> result = new APIResponse<Object>();

		try {
			// Prepare input stream
			URLConnection conn = new URL(vo.getUrl()).openConnection();
			InputStream is = conn.getInputStream();

			// Prepare destination
			OutputStream outstream = new FileOutputStream(
					new File(vo.getDestinationFolderPath() + vo.getDestinationFileName()));

			// Download file
			byte[] buffer = new byte[4096];
			int len;
			while ((len = is.read(buffer)) > 0) {
				outstream.write(buffer, 0, len);
			}

			// Close stream
			outstream.close();

		} catch (Exception e) {
			result = new APIResponse<Object>(HttpStatus.BAD_REQUEST,
					ResponseCode.mapParam(ResponseCode.ERROR_EXCEPTION, e.getMessage()));
		}

		// Return
		return result;
	}

	/**
	 * Search English top learn
	 *
	 */
	public APIResponse<Object> searchTopLearn(Long numberUser) {
		// Declare result
		APIResponse<Object> result = new APIResponse<Object>();

		// Check data authentication
		result = isDataAuthentication(0);

		if (result.getStatus() == null) {
			// Get data
			List<Object[]> searchResult = englishRepository.findTopLearn(numberUser, entityManager);

			// Transformer
			if (CollectionUtils.isNotEmpty(searchResult)) {
				// Format data
				List<EnglishLearnReportVO> listElement = new ArrayList<>();
				EnglishLearnReportVO vo;
				for (Object[] objects : searchResult) {
					vo = new EnglishLearnReportVO();

					Long userID = (Long) objects[0];
					vo.setUser(userService.getVoById(userID));

					Date learnedDate = (Date) objects[1];
					vo.setLearnedDate(learnedDate);

					SearchNumberVO userId = new SearchNumberVO();
					userId.setEq(userID.doubleValue());
					EnglishLearnReportSCO sco = new EnglishLearnReportSCO();
					sco.setUserId(userId);
					sco.setMaxRecordPerPage(100);
					sco.setPageToGet(0);
					Page<EnglishLearnReportTbl> userLearnReport = englishLearnReportRepository.findAll(sco);
					Long countLearnTotal = (long) 0;
					if (CollectionUtils.isNotEmpty(userLearnReport.getContent())) {
						for (EnglishLearnReportTbl tbl : userLearnReport.getContent()) {
							countLearnTotal = countLearnTotal + tbl.getLearnedTotal();
						}
					}
					vo.setLearnedTotal(countLearnTotal);

					listElement.add(vo);
				}

				// Sort data by learn count
				Collections.sort(listElement, new Comparator<EnglishLearnReportVO>() {
					@Override
					public int compare(EnglishLearnReportVO a, EnglishLearnReportVO b) {
						return (int) (b.getLearnedTotal() - a.getLearnedTotal());
					}
				});

				// Transformer
				PageResultVO<EnglishLearnReportVO> data = new PageResultVO<EnglishLearnReportVO>();
				data.setElements(listElement);
				data.setCurrentPage(0);
				data.setTotalPage(1);
				data.setTotalElement(listElement.size());

				// Set data return
				result.setData(data);
			}
		}

		// Return
		return result;
	}
}

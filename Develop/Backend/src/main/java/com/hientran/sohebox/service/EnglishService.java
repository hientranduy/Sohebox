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
import com.hientran.sohebox.dto.DownloadFileVO;
import com.hientran.sohebox.dto.PageResultVO;
import com.hientran.sohebox.dto.response.APIResponse;
import com.hientran.sohebox.dto.response.ResponseCode;
import com.hientran.sohebox.entity.EnglishLearnReportTbl;
import com.hientran.sohebox.entity.EnglishTbl;
import com.hientran.sohebox.entity.EnglishTypeTbl;
import com.hientran.sohebox.repository.EnglishLearnReportRepository;
import com.hientran.sohebox.repository.EnglishRepository;
import com.hientran.sohebox.sco.EnglishLearnReportSCO;
import com.hientran.sohebox.sco.EnglishSCO;
import com.hientran.sohebox.sco.SearchNumberVO;
import com.hientran.sohebox.sco.SearchTextVO;
import com.hientran.sohebox.specification.EnglishSpecs.EnglishTblEnum;
import com.hientran.sohebox.utils.FileUtils;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class EnglishService extends BaseService {
	private final EnglishRepository englishRepository;
	private final EnglishTypeCache englishTypeCache;
	private final UserService userService;
	private final EnglishLearnReportRepository englishLearnReportRepository;

	@Value("${web.asset.english.image.path}")
	private String WEB_ENGLISH_IMAGE_PATH;

	/**
	 *
	 * Create
	 *
	 * @param rq
	 * @return
	 * @throws IOException
	 */
	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public APIResponse<Long> create(EnglishTbl rq) {
		// Declare result
		APIResponse<Long> result = new APIResponse<>();

		// Validate input
		if (result.getStatus() == null) {
			List<String> errors = new ArrayList<>();

			// Keyword must not null
			if (StringUtils.isBlank(rq.getKeyWord())) {
				errors.add(ResponseCode.mapParam(ResponseCode.FILED_EMPTY, EnglishTblEnum.keyWord.name()));
			}

			// Category must not null
			if (rq.getCategory() == null) {
				errors.add(ResponseCode.mapParam(ResponseCode.FILED_EMPTY, EnglishTblEnum.category.name()));
			}

			// Image file must not null
			if (StringUtils.isBlank(rq.getImageFile())) {
				errors.add(ResponseCode.mapParam(ResponseCode.FILED_EMPTY, EnglishTblEnum.imageFile.name()));
			}

			// Vus grade must not null
			if (rq.getVusGrade() == null) {
				errors.add(ResponseCode.mapParam(ResponseCode.FILED_EMPTY, EnglishTblEnum.vusGrade.name()));
			}

			// learn day must not null
			if (rq.getLearnDay() == null) {
				errors.add(ResponseCode.mapParam(ResponseCode.FILED_EMPTY, EnglishTblEnum.learnDay.name()));
			}

			// Record error
			if (CollectionUtils.isNotEmpty(errors)) {
				result = new APIResponse<>(HttpStatus.BAD_REQUEST, errors);
			}
		}

		// Check if record existed already
		if (result.getStatus() == null) {
			if (recordIsExisted(rq.getKeyWord())) {
				result = new APIResponse<>(HttpStatus.BAD_REQUEST,
						ResponseCode.mapParam(ResponseCode.EXISTED_RECORD, "word <" + rq.getKeyWord() + ">"));
			}
		}

		////////////////
		// Save image //
		////////////////
		String imageName = formatImageName(rq.getKeyWord(), rq.getImageExtention());
		if (result.getStatus() == null) {
			if (rq.getImageFile() != null) {
				try {
					updateImage(rq.getImageFile(), imageName);
				} catch (Exception e) {
					result = new APIResponse<>(HttpStatus.BAD_REQUEST,
							ResponseCode.mapParam(ResponseCode.ERROR_EXCEPTION, e.getMessage()));
				}

			}
		}

		/////////////////////
		// Record new word //
		/////////////////////
		if (result.getStatus() == null) {
			// Transform
			EnglishTbl tbl = rq;

			// Format keyword
			tbl.setKeyWord(formatKeyWord(rq.getKeyWord().toLowerCase()));

			// Set category
			tbl.setCategory(
					englishTypeCache.getType(DBConstants.TYPE_CLASS_ENGLISH_CATEGORY, rq.getCategory().getTypeCode()));

			// Set word level
			tbl.setWordLevel(
					englishTypeCache.getType(DBConstants.TYPE_CLASS_ENGLISH_WORD_LEVEL, setWordLevel(rq.getKeyWord())));

			// Set image name
			tbl.setImageName(imageName);

			// Set explanation en if null
			if (tbl.getExplanationEn() == null) {
				tbl.setExplanationEn(tbl.getKeyWord());
			}

			// Set grade
			tbl.setVusGrade(
					englishTypeCache.getType(DBConstants.TYPE_CLASS_ENGLISH_VUS_GRADE, rq.getVusGrade().getTypeCode()));

			// Set learn day
			tbl.setLearnDay(
					englishTypeCache.getType(DBConstants.TYPE_CLASS_ENGLISH_LEARN_DAY, rq.getLearnDay().getTypeCode()));

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
	 * Download a URL mp3 file
	 *
	 * @param sco
	 * @return
	 * @throws IOException
	 */
	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public APIResponse<Object> downloadFileMp3(DownloadFileVO vo) {
		// Declare result
		APIResponse<Object> result = new APIResponse<>();

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
			result = new APIResponse<>(HttpStatus.BAD_REQUEST,
					ResponseCode.mapParam(ResponseCode.ERROR_EXCEPTION, e.getMessage()));
		}

		// Return
		return result;
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
	 * Format key word
	 *
	 * @param vo
	 * @return
	 */
	private String formatKeyWord(String keyWord) {
		return keyWord.toLowerCase();
	}

	/**
	 * Get by id
	 *
	 * @param User
	 * @return
	 */
	public APIResponse<Object> getById(Long id) {
		// Declare result
		APIResponse<Object> result = new APIResponse<>();

		// Check existence
		Optional<EnglishTbl> englishTbl = englishRepository.findById(id);
		if (englishTbl.isPresent()) {
			result.setData(englishTbl.get());
		} else {
			result = new APIResponse<>(HttpStatus.BAD_REQUEST,
					ResponseCode.mapParam(ResponseCode.INEXISTED_RECORD, "english"));
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
	 *
	 * Check if record is existed
	 *
	 * @param keyWord
	 * @return
	 */
	private boolean recordIsExisted(String keyWord) {
		// Declare result
		boolean result = false;

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
	 * Search
	 *
	 * @param sco
	 * @return
	 */
	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public APIResponse<Object> search(EnglishSCO sco) {
		// Declare result
		APIResponse<Object> result = new APIResponse<>();

		// Get data
		Page<EnglishTbl> page = englishRepository.findAll(sco);

		// Transformer
		PageResultVO<EnglishTbl> data = new PageResultVO<>();
		if (!CollectionUtils.isEmpty(page.getContent())) {
			data.setElements(page.getContent());
			setPageHeader(page, data);
		}

		// Set data return
		result.setData(data);

		// Write activity type "English access"
		recordUserActivity(DBConstants.USER_ACTIVITY_ENGLISH_ACCESS);

		// Return
		return result;
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
		APIResponse<Object> result = new APIResponse<>();

		// Get data
		List<Object[]> searchResult = englishRepository.findLowLearn(sco, entityManager);

		// Transformer
		if (CollectionUtils.isNotEmpty(searchResult)) {
			// Format data
			List<EnglishTbl> listElement = new ArrayList<>();
			for (Object[] objects : searchResult) {
				EnglishTbl tbl = new EnglishTbl();
				// id
				if (objects[0] != null) {
					tbl.setId((Long) objects[0]);
				}
				// Keyword
				if (objects[1] != null) {
					tbl.setKeyWord((String) objects[1]);
				}
				// Image name
				if (objects[2] != null) {
					tbl.setImageName((String) objects[2]);
				}
				// Explanation en
				if (objects[3] != null) {
					tbl.setExplanationEn((String) objects[3]);
				}
				// Explanation vn
				if (objects[4] != null) {
					tbl.setExplanationVn((String) objects[4]);
				}
				// Voice Uk file name
				if (objects[5] != null) {
					tbl.setVoiceUkFile((String) objects[5]);
				}
				// Voice Us file name
				if (objects[6] != null) {
					tbl.setVoiceUsFile((String) objects[6]);
				}
				// Record time
				if (objects[7] != null) {
					tbl.setRecordTimes((Long) objects[7]);
				}
				// Category code
				if (objects[8] != null) {
					EnglishTypeTbl category = new EnglishTypeTbl();
					category.setTypeCode((String) objects[8]);
					tbl.setCategory(category);
				}
				// grade code
				if (objects[9] != null) {
					EnglishTypeTbl vusGrade = new EnglishTypeTbl();
					vusGrade.setTypeCode((String) objects[9]);
					tbl.setVusGrade(vusGrade);
				}
				listElement.add(tbl);
			}

			// Transformer
			PageResultVO<EnglishTbl> data = new PageResultVO<>();
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
	 * Search English top learn
	 *
	 */
	public APIResponse<Object> searchTopLearn(Long numberUser) {
		// Declare result
		APIResponse<Object> result = new APIResponse<>();

		// Check data authentication
		result = isDataAuthentication(0);

		if (result.getStatus() == null) {
			// Get data
			List<Object[]> searchResult = englishRepository.findTopLearn(numberUser, entityManager);

			// Transformer
			if (CollectionUtils.isNotEmpty(searchResult)) {
				// Format data
				List<EnglishLearnReportTbl> listElement = new ArrayList<>();
				EnglishLearnReportTbl tblItem;
				for (Object[] objects : searchResult) {
					tblItem = new EnglishLearnReportTbl();

					Long userID = (Long) objects[0];
					tblItem.setUser(userService.getTblById(userID));

					Date learnedDate = (Date) objects[1];
					tblItem.setLearnedDate(learnedDate);

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
					tblItem.setLearnedTotal(countLearnTotal);

					listElement.add(tblItem);
				}

				// Sort data by learn count
				Collections.sort(listElement, new Comparator<EnglishLearnReportTbl>() {
					@Override
					public int compare(EnglishLearnReportTbl a, EnglishLearnReportTbl b) {
						return (int) (b.getLearnedTotal() - a.getLearnedTotal());
					}
				});

				// Transformer
				PageResultVO<EnglishLearnReportTbl> data = new PageResultVO<>();
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
	 * Update
	 *
	 * @param rq
	 * @return
	 */
	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public APIResponse<Long> update(EnglishTbl rq) {
		// Declare result
		APIResponse<Long> result = new APIResponse<>();

		// Validate input
		if (result.getStatus() == null) {
			List<String> errors = new ArrayList<>();

			// Keyword must not null
			if (StringUtils.isBlank(rq.getKeyWord())) {
				errors.add(ResponseCode.mapParam(ResponseCode.FILED_EMPTY, EnglishTblEnum.keyWord.name()));
			}

			// Record error
			if (CollectionUtils.isNotEmpty(errors)) {
				result = new APIResponse<>(HttpStatus.BAD_REQUEST, errors);
			}
		}

		// Get the old record
		EnglishTbl updateTbl = null;
		if (result.getStatus() == null) {
			updateTbl = getByKey(rq.getKeyWord());
			if (updateTbl == null) {
				result = new APIResponse<>(HttpStatus.BAD_REQUEST,
						ResponseCode.mapParam(ResponseCode.INEXISTED_RECORD, "word <" + rq.getKeyWord() + ">"));
			}
		}

		// PROCESS UPDATE

		////////////////
		// Save image //
		////////////////
		String imageName = formatImageName(rq.getKeyWord(), rq.getImageExtention());
		if (result.getStatus() == null) {
			if (rq.getImageFile() != null) {
				try {
					updateImage(rq.getImageFile(), imageName);
				} catch (Exception e) {
					result = new APIResponse<>(HttpStatus.BAD_REQUEST,
							ResponseCode.mapParam(ResponseCode.ERROR_EXCEPTION, e.getMessage()));
				}
			}
		}

		/////////////////////
		// Update word //
		/////////////////////
		if (result.getStatus() == null) {

			// Set category
			if (rq.getCategory() != null) {
				updateTbl.setCategory(englishTypeCache.getType(DBConstants.TYPE_CLASS_ENGLISH_CATEGORY,
						rq.getCategory().getTypeCode()));
			}

			// Set word level
			if (rq.getKeyWord() != null) {
				updateTbl.setWordLevel(englishTypeCache.getType(DBConstants.TYPE_CLASS_ENGLISH_WORD_LEVEL,
						setWordLevel(rq.getKeyWord())));
			}

			// Set image name
			if (rq.getImageFile() != null) {
				updateTbl.setImageName(imageName);
			}

			// Set en explanation
			if (rq.getExplanationEn() != null) {
				updateTbl.setExplanationEn(rq.getExplanationEn());
			}

			// Set vn explanation
			if (rq.getExplanationVn() != null) {
				updateTbl.setExplanationVn(rq.getExplanationVn());
			}

			// Set voice UK file name
			if (rq.getVoiceUkFile() != null) {
				updateTbl.setVoiceUkFile(rq.getVoiceUkFile());
			}

			// Set voice us file name
			if (rq.getVoiceUsFile() != null) {
				updateTbl.setVoiceUsFile(rq.getVoiceUsFile());
			}

			// Set grade
			if (rq.getVusGrade() != null) {
				updateTbl.setVusGrade(englishTypeCache.getType(DBConstants.TYPE_CLASS_ENGLISH_VUS_GRADE,
						rq.getVusGrade().getTypeCode()));
			}

			// Set learn day
			if (rq.getLearnDay() != null) {
				updateTbl.setLearnDay(englishTypeCache.getType(DBConstants.TYPE_CLASS_ENGLISH_LEARN_DAY,
						rq.getLearnDay().getTypeCode()));
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
}

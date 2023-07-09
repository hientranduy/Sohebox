package com.hientran.sohebox.service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hientran.sohebox.cache.FoodTypeCache;
import com.hientran.sohebox.constants.DBConstants;
import com.hientran.sohebox.dto.response.APIResponse;
import com.hientran.sohebox.dto.response.ResponseCode;
import com.hientran.sohebox.entity.FoodTbl;
import com.hientran.sohebox.repository.FoodRepository;
import com.hientran.sohebox.sco.FoodSCO;
import com.hientran.sohebox.sco.SearchTextVO;
import com.hientran.sohebox.specification.FoodSpecs.FoodTblEnum;
import com.hientran.sohebox.transformer.FoodTransformer;
import com.hientran.sohebox.utils.FileUtils;
import com.hientran.sohebox.vo.FoodVO;
import com.hientran.sohebox.vo.PageResultVO;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class FoodService extends BaseService {
	private final FoodRepository foodRepository;
	private final FoodTransformer foodTransformer;
	private final FoodTypeCache foodTypeCache;

	@Value("${web.asset.food.image.path}")
	private String WEB_FOOD_IMAGE_PATH;

	/**
	 * 
	 * Create
	 * 
	 * @param vo
	 * @return
	 * @throws IOException
	 */
	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public APIResponse<Long> create(FoodVO vo) {
		// Declare result
		APIResponse<Long> result = new APIResponse<Long>();

		// Validate input
		if (result.getStatus() == null) {
			List<String> errors = new ArrayList<>();

			// Name must not null
			if (StringUtils.isBlank(vo.getName())) {
				errors.add(ResponseCode.mapParam(ResponseCode.FILED_EMPTY, FoodTblEnum.name.name()));
			}

			// Image file must not null
			if (StringUtils.isBlank(vo.getImageFile())) {
				errors.add(ResponseCode.mapParam(ResponseCode.FILED_EMPTY, FoodTblEnum.imageFile.name()));
			}

			// Type must not null
			if (vo.getType() == null) {
				errors.add(ResponseCode.mapParam(ResponseCode.FILED_EMPTY, FoodTblEnum.type.name()));
			}

			// Category must not null
			if (vo.getCategory() == null) {
				errors.add(ResponseCode.mapParam(ResponseCode.FILED_EMPTY, FoodTblEnum.category.name()));
			}

			// Record error
			if (CollectionUtils.isNotEmpty(errors)) {
				result = new APIResponse<Long>(HttpStatus.BAD_REQUEST, errors);
			}
		}

		// Check existence
		if (result.getStatus() == null) {
			if (recordIsExisted(vo.getName())) {
				result = new APIResponse<Long>(HttpStatus.BAD_REQUEST,
						ResponseCode.mapParam(ResponseCode.EXISTED_RECORD, "food <" + vo.getName() + ">"));
			}
		}

		////////////////
		// Save image //
		////////////////
		String imageName = formatImageName(vo.getName(), vo.getImageExtention());
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
		// Record new //
		/////////////////////
		if (result.getStatus() == null) {
			// Transform
			FoodTbl tbl = foodTransformer.convertToTbl(vo);

			// Name
			tbl.setName(formatName(vo.getName().toLowerCase()));

			// Image name
			tbl.setImageName(imageName);

			// Type
			tbl.setType(foodTypeCache.getType(DBConstants.TYPE_CLASS_FOOD_TYPE, vo.getType().getTypeCode()));

			// Category
			tbl.setCategory(
					foodTypeCache.getType(DBConstants.TYPE_CLASS_FOOD_CATEGORY, vo.getCategory().getTypeCode()));

			// Create
			tbl = foodRepository.save(tbl);

			// Set id return
			result.setData(tbl.getId());

			// Write activity
			recordUserActivity(DBConstants.USER_ACTIVITY_FOOD_CREATE);
		}

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
	public APIResponse<Long> update(FoodVO vo) {
		// Declare result
		APIResponse<Long> result = new APIResponse<Long>();

		// Validate input
		if (result.getStatus() == null) {
			List<String> errors = new ArrayList<>();

			// Name must not null
			if (StringUtils.isBlank(vo.getName())) {
				errors.add(ResponseCode.mapParam(ResponseCode.FILED_EMPTY, FoodTblEnum.name.name()));
			}

			// Record error
			if (CollectionUtils.isNotEmpty(errors)) {
				result = new APIResponse<Long>(HttpStatus.BAD_REQUEST, errors);
			}
		}

		// Get the old record
		FoodTbl updateTbl = null;
		if (result.getStatus() == null) {
			updateTbl = getByName(vo.getName());
			if (updateTbl == null) {
				result = new APIResponse<Long>(HttpStatus.BAD_REQUEST,
						ResponseCode.mapParam(ResponseCode.INEXISTED_RECORD, "food <" + vo.getName() + ">"));
			}
		}

		// PROCESS UPDATE

		////////////////
		// Save image //
		////////////////
		String imageName = formatImageName(vo.getName(), vo.getImageExtention());
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
		// Update //
		/////////////////////
		if (result.getStatus() == null) {

			// Image name
			if (vo.getImageFile() != null) {
				updateTbl.setImageName(imageName);
			}

			// Description
			if (vo.getDescription() != null) {
				updateTbl.setDescription(vo.getDescription());
			}

			// Location Note
			if (vo.getLocationNote() != null) {
				updateTbl.setLocationNote(vo.getLocationNote());
			}

			// Type
			if (vo.getType() != null) {
				updateTbl.setType(foodTypeCache.getType(DBConstants.TYPE_CLASS_FOOD_TYPE, vo.getType().getTypeCode()));
			}

			// Category
			if (vo.getCategory() != null) {
				updateTbl.setCategory(
						foodTypeCache.getType(DBConstants.TYPE_CLASS_FOOD_CATEGORY, vo.getCategory().getTypeCode()));
			}

			// Is fast food
			if (vo.getIsFastFood() != null) {
				updateTbl.setIsFastFood(vo.getIsFastFood());
			}

			// Recipe
			if (StringUtils.isNotBlank(vo.getRecipeString())) {
				updateTbl.setRecipe(vo.getRecipeString().getBytes(StandardCharsets.UTF_8));
			}

			// Location Note
			if (vo.getUrlReference() != null) {
				updateTbl.setUrlReference(vo.getUrlReference());
			}

			// Update
			updateTbl = foodRepository.save(updateTbl);

			// Set id return
			result.setData(updateTbl.getId());

			// Write activity
			recordUserActivity(DBConstants.USER_ACTIVITY_FOOD_UPDATE);
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
	public APIResponse<Object> search(FoodSCO sco) {
		// Declare result
		APIResponse<Object> result = new APIResponse<Object>();

		// Get data
		Page<FoodTbl> page = foodRepository.findAll(sco);

		// Transformer
		PageResultVO<FoodVO> data = foodTransformer.convertToPageReturn(page);

		// Set data return
		result.setData(data);

		// Write activity type "Food access"
		recordUserActivity(DBConstants.USER_ACTIVITY_FOOD_ACCESS);

		// Return
		return result;
	}

	/**
	 * 
	 * Check existence
	 *
	 * @param name
	 * @return
	 */
	private boolean recordIsExisted(String nameValue) {
		// Declare result
		Boolean result = false;

		SearchTextVO nameSearch = new SearchTextVO();
		nameSearch.setEq(formatName(nameValue));

		FoodSCO sco = new FoodSCO();
		sco.setName(nameSearch);

		// Get data
		List<FoodTbl> list = foodRepository.findAll(sco).getContent();
		if (CollectionUtils.isNotEmpty(list)) {
			result = true;
		}

		// Return
		return result;
	}

	/**
	 * 
	 * Get by name
	 *
	 * @param name
	 * @return
	 */
	public FoodTbl getByName(String nameValue) {
		// Declare result
		FoodTbl result = null;

		SearchTextVO nameSearch = new SearchTextVO();
		nameSearch.setEq(formatName(nameValue));

		FoodSCO sco = new FoodSCO();
		sco.setName(nameSearch);

		// Get data
		List<FoodTbl> list = foodRepository.findAll(sco).getContent();
		if (CollectionUtils.isNotEmpty(list)) {
			result = list.get(0);
		}

		// Return
		return result;
	}

	/**
	 * Get by id
	 * 
	 * @param id
	 * @return
	 */
	public APIResponse<Object> getById(Long id) {
		// Declare result
		APIResponse<Object> result = new APIResponse<Object>();

		// Check existence
		Optional<FoodTbl> foodTbl = foodRepository.findById(id);
		if (foodTbl.isPresent()) {
			FoodVO vo = foodTransformer.convertToVO(foodTbl.get());
			result.setData(vo);
		} else {
			result = new APIResponse<Object>(HttpStatus.BAD_REQUEST,
					ResponseCode.mapParam(ResponseCode.INEXISTED_RECORD, "food"));
		}

		// Return
		return result;
	}

	/**
	 * Format
	 *
	 * @param vo
	 * @return
	 */
	private String formatName(String nameValue) {
		return nameValue.toLowerCase();
	}

	/**
	 * Format image name
	 *
	 * @return
	 */
	private String formatImageName(String foodName, String imageExtention) {
		return foodName.toLowerCase().replaceAll(" ", "_") + "." + imageExtention;
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
		FileUtils.createDirsIfNotExist(WEB_FOOD_IMAGE_PATH);

		// Write file
		String[] imageDataBase = imageFile.split(";");
		String[] imageData = imageDataBase[1].split(",");
		InputStream photoStream = FileUtils.getStreamFromString(imageData[1]);
		if (photoStream != null) {
			String filePath = WEB_FOOD_IMAGE_PATH + imageName;

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

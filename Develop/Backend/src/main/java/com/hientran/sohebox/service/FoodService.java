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
import com.hientran.sohebox.dto.PageResultVO;
import com.hientran.sohebox.dto.response.APIResponse;
import com.hientran.sohebox.dto.response.ResponseCode;
import com.hientran.sohebox.entity.FoodTbl;
import com.hientran.sohebox.repository.FoodRepository;
import com.hientran.sohebox.sco.FoodSCO;
import com.hientran.sohebox.specification.FoodSpecs.FoodTblEnum;
import com.hientran.sohebox.utils.FileUtils;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class FoodService extends BaseService {
	private final FoodRepository foodRepository;
	private final FoodTypeCache foodTypeCache;

	@Value("${web.asset.food.image.path}")
	private String WEB_FOOD_IMAGE_PATH;

	/**
	 * Create
	 */
	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public APIResponse<Long> create(FoodTbl rq) {
		// Declare result
		APIResponse<Long> result = new APIResponse<>();

		// Validate input
		if (result.getStatus() == null) {
			List<String> errors = new ArrayList<>();

			// Name must not null
			if (StringUtils.isBlank(rq.getName())) {
				errors.add(ResponseCode.mapParam(ResponseCode.FILED_EMPTY, FoodTblEnum.name.name()));
			}

			// Image file must not null
			if (StringUtils.isBlank(rq.getImageFile())) {
				errors.add(ResponseCode.mapParam(ResponseCode.FILED_EMPTY, FoodTblEnum.imageFile.name()));
			}

			// Type must not null
			if (rq.getType() == null) {
				errors.add(ResponseCode.mapParam(ResponseCode.FILED_EMPTY, FoodTblEnum.type.name()));
			}

			// Category must not null
			if (rq.getCategory() == null) {
				errors.add(ResponseCode.mapParam(ResponseCode.FILED_EMPTY, FoodTblEnum.category.name()));
			}

			// Record error
			if (CollectionUtils.isNotEmpty(errors)) {
				return new APIResponse<>(HttpStatus.BAD_REQUEST, errors);
			}
		}

		// Check existence
		FoodTbl searchTbl = foodRepository.findFirstByName(rq.getName());
		if (searchTbl != null) {
			return new APIResponse<>(HttpStatus.BAD_REQUEST,
					ResponseCode.mapParam(ResponseCode.EXISTED_RECORD, "food <" + rq.getName() + ">"));
		}

		////////////////
		// Save image //
		////////////////
		String imageName = formatImageName(rq.getName(), rq.getImageExtention());
		if (rq.getImageFile() != null) {
			try {
				updateImage(rq.getImageFile(), imageName);
			} catch (Exception e) {
				result = new APIResponse<>(HttpStatus.BAD_REQUEST,
						ResponseCode.mapParam(ResponseCode.ERROR_EXCEPTION, e.getMessage()));
			}

		}

		/////////////////////
		// Record new //
		/////////////////////
		// Transform
		FoodTbl tbl = rq;
		tbl.setRecipe(rq.getRecipeString().getBytes(StandardCharsets.UTF_8));

		// Name
		tbl.setName(formatName(rq.getName().toLowerCase()));

		// Image name
		tbl.setImageName(imageName);

		// Type
		tbl.setType(foodTypeCache.getType(DBConstants.TYPE_CLASS_FOOD_TYPE, rq.getType().getTypeCode()));

		// Category
		tbl.setCategory(foodTypeCache.getType(DBConstants.TYPE_CLASS_FOOD_CATEGORY, rq.getCategory().getTypeCode()));

		// Create
		tbl = foodRepository.save(tbl);

		// Set id return
		result.setData(tbl.getId());

		// Write activity
		recordUserActivity(DBConstants.USER_ACTIVITY_FOOD_CREATE);

		// Return
		return result;
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
	 * Format
	 *
	 * @param vo
	 * @return
	 */
	private String formatName(String nameValue) {
		return nameValue.toLowerCase();
	}

	/**
	 * Get by id
	 */
	public APIResponse<Object> getById(Long id) {
		// Declare result
		APIResponse<Object> result = new APIResponse<>();

		// Check existence
		Optional<FoodTbl> foodTbl = foodRepository.findById(id);
		if (foodTbl.isPresent()) {
			FoodTbl tbl = foodTbl.get();
			tbl.setRecipeString(new String(tbl.getRecipe(), StandardCharsets.UTF_8));
			result.setData(tbl);
		} else {
			result = new APIResponse<>(HttpStatus.BAD_REQUEST,
					ResponseCode.mapParam(ResponseCode.INEXISTED_RECORD, "food"));
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
		APIResponse<Object> result = new APIResponse<>();

		// Get data
		Page<FoodTbl> page = foodRepository.findAll(sco);

		// Transformer
		PageResultVO<FoodTbl> data = new PageResultVO<>();
		if (!CollectionUtils.isEmpty(page.getContent())) {
			List<FoodTbl> formatData = new ArrayList<>();
			for (FoodTbl item : page.getContent()) {
				item.setRecipeString(new String(item.getRecipe(), StandardCharsets.UTF_8));
				formatData.add(item);
			}
			data.setElements(formatData);
			setPageHeader(page, data);
		}

		// Set data return
		result.setData(data);

		// Write activity type "Food access"
		recordUserActivity(DBConstants.USER_ACTIVITY_FOOD_ACCESS);

		// Return
		return result;
	}

	/**
	 * Update
	 */
	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public APIResponse<Long> update(FoodTbl rq) {
		// Declare result
		APIResponse<Long> result = new APIResponse<>();

		// Validate input
		if (result.getStatus() == null) {
			List<String> errors = new ArrayList<>();

			// Name must not null
			if (StringUtils.isBlank(rq.getName())) {
				errors.add(ResponseCode.mapParam(ResponseCode.FILED_EMPTY, FoodTblEnum.name.name()));
			}

			// Record error
			if (CollectionUtils.isNotEmpty(errors)) {
				return new APIResponse<>(HttpStatus.BAD_REQUEST, errors);
			}
		}

		// Get the old record
		FoodTbl updateTbl = foodRepository.findFirstByName(rq.getName());
		if (updateTbl == null) {
			return new APIResponse<>(HttpStatus.BAD_REQUEST,
					ResponseCode.mapParam(ResponseCode.INEXISTED_RECORD, "food <" + rq.getName() + ">"));
		}

		// PROCESS UPDATE

		////////////////
		// Save image //
		////////////////
		String imageName = formatImageName(rq.getName(), rq.getImageExtention());
		if (rq.getImageFile() != null) {
			try {
				updateImage(rq.getImageFile(), imageName);
			} catch (Exception e) {
				return new APIResponse<>(HttpStatus.BAD_REQUEST,
						ResponseCode.mapParam(ResponseCode.ERROR_EXCEPTION, e.getMessage()));
			}
		}

		/////////////////////
		// Update //
		/////////////////////
		// Image name
		if (rq.getImageFile() != null) {
			updateTbl.setImageName(imageName);
		}

		// Description
		if (rq.getDescription() != null) {
			updateTbl.setDescription(rq.getDescription());
		}

		// Location Note
		if (rq.getLocationNote() != null) {
			updateTbl.setLocationNote(rq.getLocationNote());
		}

		// Type
		if (rq.getType() != null) {
			updateTbl.setType(foodTypeCache.getType(DBConstants.TYPE_CLASS_FOOD_TYPE, rq.getType().getTypeCode()));
		}

		// Category
		if (rq.getCategory() != null) {
			updateTbl.setCategory(
					foodTypeCache.getType(DBConstants.TYPE_CLASS_FOOD_CATEGORY, rq.getCategory().getTypeCode()));
		}

		// Is fast food
		if (rq.getIsFastFood() != null) {
			updateTbl.setIsFastFood(rq.getIsFastFood());
		}

		// Recipe
		if (StringUtils.isNotBlank(rq.getRecipeString())) {
			updateTbl.setRecipe(rq.getRecipeString().getBytes(StandardCharsets.UTF_8));
		}

		// Location Note
		if (rq.getUrlReference() != null) {
			updateTbl.setUrlReference(rq.getUrlReference());
		}

		// Update
		updateTbl = foodRepository.save(updateTbl);

		// Set id return
		result.setData(updateTbl.getId());

		// Write activity
		recordUserActivity(DBConstants.USER_ACTIVITY_FOOD_UPDATE);

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

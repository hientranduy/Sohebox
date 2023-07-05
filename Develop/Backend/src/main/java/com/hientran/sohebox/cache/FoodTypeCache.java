package com.hientran.sohebox.cache;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.cache.CacheManager;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.hientran.sohebox.constants.DBConstants;
import com.hientran.sohebox.constants.ResponseCode;
import com.hientran.sohebox.constants.enums.FoodTypeTblEnum;
import com.hientran.sohebox.entity.FoodTypeTbl;
import com.hientran.sohebox.exception.APIResponse;
import com.hientran.sohebox.repository.FoodTypeRepository;
import com.hientran.sohebox.sco.FoodTypeSCO;
import com.hientran.sohebox.sco.SearchTextVO;
import com.hientran.sohebox.transformer.FoodTypeTransformer;
import com.hientran.sohebox.vo.FoodTypeVO;
import com.hientran.sohebox.vo.PageResultVO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FoodTypeCache extends BaseCache {

	private final FoodTypeRepository typeRepository;
	private final FoodTypeTransformer typeTransformer;

	private final CacheManager cacheManager;
	private String cacheName = "foodTypeCache";

	/**
	 * Get
	 */
	public FoodTypeVO getType(String typeClass, String typeCode) {
		// Declare result
		FoodTypeVO result = null;

		// Retrieve type cache
		result = cacheManager.getCache(cacheName).get(formatTypeMapKey(typeClass, typeCode), FoodTypeVO.class);
		if (result != null) {
			return result;
		}

		// Search DB
		FoodTypeTbl tbl = typeRepository.findFirstByTypeClassAndTypeCode(formatTypeClass(typeClass),
				formatTypeCode(typeCode));
		if (tbl != null) {
			result = typeTransformer.convertToVO(tbl);
		} else {

			// Create new type
			FoodTypeVO vo = new FoodTypeVO();
			vo.setTypeClass(formatTypeClass(typeClass));
			vo.setTypeCode(formatTypeCode(typeCode));
			vo.setTypeName(typeCode.substring(0, 1).toUpperCase() + typeCode.substring(1).toLowerCase());
			vo.setDescription(typeCode);

			if (StringUtils.isBlank(vo.getIconUrl())) {
				if (StringUtils.equals(vo.getTypeClass(), DBConstants.TYPE_CLASS_ACCOUNT)) {
					vo.setIconUrl(DBConstants.ACCOUNT_TYPE_DEFAUT_ICON);
				}
			}

			FoodTypeTbl newType = typeRepository.save(typeTransformer.convertToTbl(vo));
			result = typeTransformer.convertToVO(newType);
		}

		// Add to cache
		cacheManager.getCache(cacheName).put(formatTypeMapKey(result.getTypeClass(), result.getTypeCode()), result);

		// Return
		return result;
	}

	/**
	 * Search
	 */
	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public APIResponse<Object> search(FoodTypeSCO sco) {
		// Declare result
		APIResponse<Object> result = new APIResponse<Object>();

		// Get data
		Page<FoodTypeTbl> page = typeRepository.findAll(sco);

		// Transformer
		PageResultVO<FoodTypeVO> data = typeTransformer.convertToPageReturn(page);

		// Set data return
		result.setData(data);

		// Return
		return result;
	}

	/**
	 * Update
	 */
	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public APIResponse<Long> update(FoodTypeVO vo) {

		// Validate input
		List<String> errors = new ArrayList<>();
		if (StringUtils.isBlank(vo.getTypeClass())) {
			errors.add(ResponseCode.mapParam(ResponseCode.FILED_EMPTY, FoodTypeTblEnum.typeClass.name()));
		}
		if (StringUtils.isBlank(vo.getTypeCode())) {
			errors.add(ResponseCode.mapParam(ResponseCode.FILED_EMPTY, FoodTypeTblEnum.typeCode.name()));
		}
		if (StringUtils.isBlank(vo.getTypeName())) {
			errors.add(ResponseCode.mapParam(ResponseCode.FILED_EMPTY, FoodTypeTblEnum.typeName.name()));
		}
		if (!CollectionUtils.isEmpty(errors)) {
			return new APIResponse<Long>(HttpStatus.BAD_REQUEST, errors);
		}

		// Search old record
		FoodTypeTbl tbl = typeRepository.findFirstByTypeClassAndTypeCode(formatTypeClass(vo.getTypeClass()),
				formatTypeCode(vo.getTypeCode()));
		if (tbl == null) {
			return new APIResponse<Long>(HttpStatus.BAD_REQUEST,
					ResponseCode.mapParam(ResponseCode.INEXISTED_RECORD, "typeCode/typeClass"));
		}

		// Search old record
		SearchTextVO searchClass = new SearchTextVO();
		searchClass.setEq(formatTypeClass(vo.getTypeClass()));
		SearchTextVO searchCode = new SearchTextVO();
		searchCode.setEq(formatTypeCode(vo.getTypeCode()));

		FoodTypeSCO sco = new FoodTypeSCO();
		sco.setTypeClass(searchClass);
		sco.setTypeCode(searchCode);

		// Update
		if (vo.getTypeCode() != null) {
			tbl.setTypeCode(formatTypeCode(vo.getTypeCode()));
		}
		if (vo.getTypeName() != null) {
			tbl.setTypeName(vo.getTypeName());
		}
		if (vo.getDescription() != null) {
			tbl.setDescription(vo.getDescription());
		}
		if (vo.getIconUrl() != null) {
			tbl.setIconUrl(vo.getIconUrl());
		}
		APIResponse<Long> result = new APIResponse<Long>();
		result.setData(typeRepository.save(tbl).getId());

		// Update cache
		cacheManager.getCache(cacheName).put(formatTypeMapKey(tbl.getTypeClass(), tbl.getTypeCode()),
				typeTransformer.convertToVO(tbl));

		// Return
		return result;
	}
}
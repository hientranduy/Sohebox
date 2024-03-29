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
import com.hientran.sohebox.dto.PageResultVO;
import com.hientran.sohebox.dto.response.APIResponse;
import com.hientran.sohebox.dto.response.ResponseCode;
import com.hientran.sohebox.entity.FoodTypeTbl;
import com.hientran.sohebox.repository.FoodTypeRepository;
import com.hientran.sohebox.sco.FoodTypeSCO;
import com.hientran.sohebox.sco.SearchTextVO;
import com.hientran.sohebox.service.TransformerService;
import com.hientran.sohebox.specification.FoodTypeSpecs.FoodTypeTblEnum;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FoodTypeCache extends TransformerService {

	private final FoodTypeRepository typeRepository;

	private final CacheManager cacheManager;
	private String cacheName = "foodTypeCache";

	/**
	 * Get
	 */
	public FoodTypeTbl getType(String typeClass, String typeCode) {
		// Declare result
		FoodTypeTbl result = null;

		// Retrieve type cache
		result = cacheManager.getCache(cacheName).get(formatTypeMapKey(typeClass, typeCode), FoodTypeTbl.class);
		if (result != null) {
			return result;
		}

		// Search DB
		FoodTypeTbl tbl = typeRepository.findFirstByTypeClassAndTypeCode(formatTypeClass(typeClass),
				formatTypeCode(typeCode));
		if (tbl != null) {
			result = tbl;
		} else {

			// Create new type
			FoodTypeTbl tblNew = new FoodTypeTbl();
			tblNew.setTypeClass(formatTypeClass(typeClass));
			tblNew.setTypeCode(formatTypeCode(typeCode));
			tblNew.setTypeName(typeCode.substring(0, 1).toUpperCase() + typeCode.substring(1).toLowerCase());
			tblNew.setDescription(typeCode);

			if (StringUtils.isBlank(tblNew.getIconUrl())) {
				if (StringUtils.equals(tblNew.getTypeClass(), DBConstants.TYPE_CLASS_ACCOUNT)) {
					tblNew.setIconUrl(DBConstants.ACCOUNT_TYPE_DEFAUT_ICON);
				}
			}
			result = typeRepository.save(tblNew);
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
		APIResponse<Object> result = new APIResponse<>();

		// Get data
		Page<FoodTypeTbl> page = typeRepository.findAll(sco);

		// Transformer
		PageResultVO<FoodTypeTbl> data = new PageResultVO<>();
		data.setElements(page.getContent());
		setPageHeader(page, data);

		// Set data return
		result.setData(data);

		// Return
		return result;
	}

	/**
	 * Update
	 */
	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public APIResponse<Long> update(FoodTypeTbl rq) {

		// Validate input
		List<String> errors = new ArrayList<>();
		if (StringUtils.isBlank(rq.getTypeClass())) {
			errors.add(ResponseCode.mapParam(ResponseCode.FILED_EMPTY, FoodTypeTblEnum.typeClass.name()));
		}
		if (StringUtils.isBlank(rq.getTypeCode())) {
			errors.add(ResponseCode.mapParam(ResponseCode.FILED_EMPTY, FoodTypeTblEnum.typeCode.name()));
		}
		if (StringUtils.isBlank(rq.getTypeName())) {
			errors.add(ResponseCode.mapParam(ResponseCode.FILED_EMPTY, FoodTypeTblEnum.typeName.name()));
		}
		if (!CollectionUtils.isEmpty(errors)) {
			return new APIResponse<>(HttpStatus.BAD_REQUEST, errors);
		}

		// Search old record
		FoodTypeTbl tbl = typeRepository.findFirstByTypeClassAndTypeCode(formatTypeClass(rq.getTypeClass()),
				formatTypeCode(rq.getTypeCode()));
		if (tbl == null) {
			return new APIResponse<>(HttpStatus.BAD_REQUEST,
					ResponseCode.mapParam(ResponseCode.INEXISTED_RECORD, "typeCode/typeClass"));
		}

		// Search old record
		SearchTextVO searchClass = new SearchTextVO();
		searchClass.setEq(formatTypeClass(rq.getTypeClass()));
		SearchTextVO searchCode = new SearchTextVO();
		searchCode.setEq(formatTypeCode(rq.getTypeCode()));

		FoodTypeSCO sco = new FoodTypeSCO();
		sco.setTypeClass(searchClass);
		sco.setTypeCode(searchCode);

		// Update
		if (rq.getTypeCode() != null) {
			tbl.setTypeCode(formatTypeCode(rq.getTypeCode()));
		}
		if (rq.getTypeName() != null) {
			tbl.setTypeName(rq.getTypeName());
		}
		if (rq.getDescription() != null) {
			tbl.setDescription(rq.getDescription());
		}
		if (rq.getIconUrl() != null) {
			tbl.setIconUrl(rq.getIconUrl());
		}
		APIResponse<Long> result = new APIResponse<>();
		result.setData(typeRepository.save(tbl).getId());

		// Update cache
		cacheManager.getCache(cacheName).put(formatTypeMapKey(tbl.getTypeClass(), tbl.getTypeCode()), tbl);

		// Return
		return result;
	}
}
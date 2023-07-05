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
import com.hientran.sohebox.constants.enums.MediaTypeTblEnum;
import com.hientran.sohebox.entity.MediaTypeTbl;
import com.hientran.sohebox.exception.APIResponse;
import com.hientran.sohebox.repository.MediaTypeRepository;
import com.hientran.sohebox.sco.MediaTypeSCO;
import com.hientran.sohebox.sco.SearchTextVO;
import com.hientran.sohebox.transformer.MediaTypeTransformer;
import com.hientran.sohebox.vo.MediaTypeVO;
import com.hientran.sohebox.vo.PageResultVO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MediaTypeCache extends BaseCache {

	private final MediaTypeRepository typeRepository;
	private final MediaTypeTransformer typeTransformer;

	private final CacheManager cacheManager;
	private String cacheName = "mediaTypeCache";

	/**
	 * Get
	 */
	public MediaTypeVO getType(String typeClass, String typeCode) {
		// Declare result
		MediaTypeVO result = null;

		// Retrieve cache
		result = cacheManager.getCache(cacheName).get(formatTypeMapKey(typeClass, typeCode), MediaTypeVO.class);
		if (result != null) {
			return result;
		}

		// Search DB
		MediaTypeTbl tbl = typeRepository.findFirstByTypeClassAndTypeCode(formatTypeClass(typeClass),
				formatTypeCode(typeCode));
		if (tbl != null) {
			result = typeTransformer.convertToVO(tbl);
		} else {

			// Create new type
			MediaTypeVO vo = new MediaTypeVO();
			vo.setTypeClass(formatTypeClass(typeClass));
			vo.setTypeCode(formatTypeCode(typeCode));
			vo.setTypeName(typeCode.substring(0, 1).toUpperCase() + typeCode.substring(1).toLowerCase());
			vo.setDescription(typeCode);

			if (StringUtils.isBlank(vo.getIconUrl())) {
				if (StringUtils.equals(vo.getTypeClass(), DBConstants.TYPE_CLASS_ACCOUNT)) {
					vo.setIconUrl(DBConstants.ACCOUNT_TYPE_DEFAUT_ICON);
				}
			}

			MediaTypeTbl newType = typeRepository.save(typeTransformer.convertToTbl(vo));
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
	public APIResponse<Object> search(MediaTypeSCO sco) {
		// Declare result
		APIResponse<Object> result = new APIResponse<Object>();

		// Get data
		Page<MediaTypeTbl> page = typeRepository.findAll(sco);

		// Transformer
		PageResultVO<MediaTypeVO> data = typeTransformer.convertToPageReturn(page);

		// Set data return
		result.setData(data);

		// Return
		return result;
	}

	/**
	 * Update
	 */
	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public APIResponse<Long> update(MediaTypeVO vo) {

		// Validate input
		List<String> errors = new ArrayList<>();
		if (StringUtils.isBlank(vo.getTypeClass())) {
			errors.add(ResponseCode.mapParam(ResponseCode.FILED_EMPTY, MediaTypeTblEnum.typeClass.name()));
		}
		if (StringUtils.isBlank(vo.getTypeCode())) {
			errors.add(ResponseCode.mapParam(ResponseCode.FILED_EMPTY, MediaTypeTblEnum.typeCode.name()));
		}
		if (StringUtils.isBlank(vo.getTypeName())) {
			errors.add(ResponseCode.mapParam(ResponseCode.FILED_EMPTY, MediaTypeTblEnum.typeName.name()));
		}
		if (!CollectionUtils.isEmpty(errors)) {
			return new APIResponse<Long>(HttpStatus.BAD_REQUEST, errors);
		}

		// Search old record
		MediaTypeTbl tbl = typeRepository.findFirstByTypeClassAndTypeCode(formatTypeClass(vo.getTypeClass()),
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

		MediaTypeSCO sco = new MediaTypeSCO();
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
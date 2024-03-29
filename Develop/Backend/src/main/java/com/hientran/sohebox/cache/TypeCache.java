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
import com.hientran.sohebox.entity.TypeTbl;
import com.hientran.sohebox.repository.TypeRepository;
import com.hientran.sohebox.sco.TypeSCO;
import com.hientran.sohebox.service.TransformerService;
import com.hientran.sohebox.specification.TypeSpecs.TypeTblEnum;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TypeCache extends TransformerService {

	private final TypeRepository typeRepository;

	private final CacheManager cacheManager;
	private String cacheName = "typeCache";

	/**
	 * Create new
	 */
	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public APIResponse<Long> create(TypeTbl rq) {

		// Validate input
		List<String> errors = new ArrayList<>();
		if (StringUtils.isBlank(rq.getTypeClass())) {
			errors.add(ResponseCode.mapParam(ResponseCode.FILED_EMPTY, TypeTblEnum.typeClass.name()));
		}
		if (StringUtils.isBlank(rq.getTypeCode())) {
			errors.add(ResponseCode.mapParam(ResponseCode.FILED_EMPTY, TypeTblEnum.typeCode.name()));
		}
		if (StringUtils.isBlank(rq.getTypeName())) {
			errors.add(ResponseCode.mapParam(ResponseCode.FILED_EMPTY, TypeTblEnum.typeName.name()));
		}
		if (!CollectionUtils.isEmpty(errors)) {
			return new APIResponse<>(HttpStatus.BAD_REQUEST, errors);
		}

		// Valid existed record
		TypeTbl tblSearch = typeRepository.findFirstByTypeClassAndTypeCode(formatTypeClass(rq.getTypeClass()),
				formatTypeCode(rq.getTypeCode()));
		if (tblSearch != null) {
			return new APIResponse<>(HttpStatus.BAD_REQUEST,
					ResponseCode.mapParam(ResponseCode.EXISTED_RECORD, "typeCode/typeClass"));
		}

		// Create new type
		TypeTbl tbl = new TypeTbl();
		tbl.setTypeClass(formatTypeClass(rq.getTypeClass()));
		tbl.setTypeCode(formatTypeCode(rq.getTypeCode()));
		tbl.setTypeName(rq.getTypeCode().substring(0, 1).toUpperCase() + rq.getTypeCode().substring(1).toLowerCase());
		tbl.setDescription(rq.getDescription());
		tbl.setIconUrl(rq.getIconUrl());
		tbl.setUrl(rq.getUrl());
		tbl = typeRepository.save(tbl);

		// Return
		APIResponse<Long> result = new APIResponse<>();
		result.setData(tbl.getId());
		return result;
	}

	/**
	 * Get
	 */
	public TypeTbl getType(String typeClass, String typeCode) {
		// Declare result
		TypeTbl result = null;

		// Retrieve type cache
		result = cacheManager.getCache(cacheName).get(formatTypeMapKey(typeClass, typeCode), TypeTbl.class);
		if (result != null) {
			return result;
		}

		// Search DB
		TypeTbl tbl = typeRepository.findFirstByTypeClassAndTypeCode(formatTypeClass(typeClass),
				formatTypeCode(typeCode));
		if (tbl != null) {
			result = tbl;
		} else {
			// Create new type
			TypeTbl tblNew = new TypeTbl();
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
	public APIResponse<Object> search(TypeSCO sco) {
		// Declare result
		APIResponse<Object> result = new APIResponse<>();

		// Get data
		Page<TypeTbl> page = typeRepository.findAll(sco);

		// Transformer
		PageResultVO<TypeTbl> data = new PageResultVO<>();
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
	public APIResponse<Long> update(TypeTbl rq) {

		// Validate input
		List<String> errors = new ArrayList<>();
		if (StringUtils.isBlank(rq.getTypeClass())) {
			errors.add(ResponseCode.mapParam(ResponseCode.FILED_EMPTY, TypeTblEnum.typeClass.name()));
		}
		if (StringUtils.isBlank(rq.getTypeCode())) {
			errors.add(ResponseCode.mapParam(ResponseCode.FILED_EMPTY, TypeTblEnum.typeCode.name()));
		}
		if (StringUtils.isBlank(rq.getTypeName())) {
			errors.add(ResponseCode.mapParam(ResponseCode.FILED_EMPTY, TypeTblEnum.typeName.name()));
		}
		if (!CollectionUtils.isEmpty(errors)) {
			return new APIResponse<>(HttpStatus.BAD_REQUEST, errors);
		}

		// Search old record
		TypeTbl tbl = typeRepository.findFirstByTypeClassAndTypeCode(formatTypeClass(rq.getTypeClass()),
				formatTypeCode(rq.getTypeCode()));
		if (tbl == null) {
			return new APIResponse<>(HttpStatus.BAD_REQUEST,
					ResponseCode.mapParam(ResponseCode.INEXISTED_RECORD, "typeCode/typeClass"));
		}

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
		if (rq.getUrl() != null) {
			tbl.setUrl(rq.getUrl());
		}

		APIResponse<Long> result = new APIResponse<>();
		result.setData(typeRepository.save(tbl).getId());

		// Update cache
		cacheManager.getCache(cacheName).put(formatTypeMapKey(tbl.getTypeClass(), tbl.getTypeCode()), tbl);

		// Return
		return result;
	}
}
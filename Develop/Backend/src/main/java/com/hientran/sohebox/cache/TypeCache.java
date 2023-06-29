package com.hientran.sohebox.cache;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.hazelcast.core.HazelcastInstance;
import com.hientran.sohebox.constants.DBConstants;
import com.hientran.sohebox.constants.ResponseCode;
import com.hientran.sohebox.constants.enums.TypeTblEnum;
import com.hientran.sohebox.entity.TypeTbl;
import com.hientran.sohebox.exception.APIResponse;
import com.hientran.sohebox.repository.TypeRepository;
import com.hientran.sohebox.sco.TypeSCO;
import com.hientran.sohebox.transformer.TypeTransformer;
import com.hientran.sohebox.vo.PageResultVO;
import com.hientran.sohebox.vo.TypeVO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TypeCache extends BaseCache {

	private final HazelcastInstance instance;
	private final TypeRepository typeRepository;
	private final TypeTransformer typeTransformer;

	/**
	 * Get
	 */
	public TypeVO getType(String typeClass, String typeCode) {
		// Declare result
		TypeVO result = null;

		// Retrieve type cache
		Map<String, TypeVO> typeCache = instance.getMap("typeCache");
		result = typeCache.get(formatTypeMapKey(typeClass, typeCode));
		if (result != null) {
			return result;
		}

		// Search DB
		TypeTbl tbl = typeRepository.findFirstByTypeClassAndTypeCode(formatTypeClass(typeClass),
				formatTypeCode(typeCode));
		if (tbl != null) {
			result = typeTransformer.convertToVO(tbl);
		} else {
			// Create new type
			TypeVO vo = new TypeVO();
			vo.setTypeClass(formatTypeClass(typeClass));
			vo.setTypeCode(formatTypeCode(typeCode));
			vo.setTypeName(typeCode.substring(0, 1).toUpperCase() + typeCode.substring(1).toLowerCase());
			vo.setDescription(typeCode);
			if (StringUtils.isBlank(vo.getIconUrl())) {
				if (StringUtils.equals(vo.getTypeClass(), DBConstants.TYPE_CLASS_ACCOUNT)) {
					vo.setIconUrl(DBConstants.ACCOUNT_TYPE_DEFAUT_ICON);
				}
			}
			TypeTbl newType = typeRepository.save(typeTransformer.convertToTbl(vo));
			result = typeTransformer.convertToVO(newType);
		}

		// Add to cache
		typeCache.put(formatTypeMapKey(result.getTypeClass(), result.getTypeCode()), result);

		// Return
		return result;
	}

	/**
	 * Create new
	 */
	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public APIResponse<Long> create(TypeVO vo) {

		// Validate input
		List<String> errors = new ArrayList<>();
		if (StringUtils.isBlank(vo.getTypeClass())) {
			errors.add(ResponseCode.mapParam(ResponseCode.FILED_EMPTY, TypeTblEnum.typeClass.name()));
		}
		if (StringUtils.isBlank(vo.getTypeCode())) {
			errors.add(ResponseCode.mapParam(ResponseCode.FILED_EMPTY, TypeTblEnum.typeCode.name()));
		}
		if (StringUtils.isBlank(vo.getTypeName())) {
			errors.add(ResponseCode.mapParam(ResponseCode.FILED_EMPTY, TypeTblEnum.typeName.name()));
		}
		if (!CollectionUtils.isEmpty(errors)) {
			return new APIResponse<Long>(HttpStatus.BAD_REQUEST, errors);
		}

		// Valid existed record
		TypeTbl tblSearch = typeRepository.findFirstByTypeClassAndTypeCode(formatTypeClass(vo.getTypeClass()),
				formatTypeCode(vo.getTypeCode()));
		if (tblSearch != null) {
			return new APIResponse<Long>(HttpStatus.BAD_REQUEST,
					ResponseCode.mapParam(ResponseCode.EXISTED_RECORD, "typeCode/typeClass"));
		}

		// Create new type
		TypeTbl tbl = new TypeTbl();
		tbl.setTypeClass(formatTypeClass(vo.getTypeClass()));
		tbl.setTypeCode(formatTypeCode(vo.getTypeCode()));
		tbl.setTypeName(vo.getTypeCode().substring(0, 1).toUpperCase() + vo.getTypeCode().substring(1).toLowerCase());
		tbl.setDescription(vo.getDescription());
		tbl.setIconUrl(vo.getIconUrl());
		tbl.setUrl(vo.getUrl());
		tbl = typeRepository.save(tbl);

		// Return
		APIResponse<Long> result = new APIResponse<Long>();
		result.setData(tbl.getId());
		return result;
	}

	/**
	 * Update
	 */
	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public APIResponse<Long> update(TypeVO vo) {

		// Validate input
		List<String> errors = new ArrayList<>();
		if (StringUtils.isBlank(vo.getTypeClass())) {
			errors.add(ResponseCode.mapParam(ResponseCode.FILED_EMPTY, TypeTblEnum.typeClass.name()));
		}
		if (StringUtils.isBlank(vo.getTypeCode())) {
			errors.add(ResponseCode.mapParam(ResponseCode.FILED_EMPTY, TypeTblEnum.typeCode.name()));
		}
		if (StringUtils.isBlank(vo.getTypeName())) {
			errors.add(ResponseCode.mapParam(ResponseCode.FILED_EMPTY, TypeTblEnum.typeName.name()));
		}
		if (!CollectionUtils.isEmpty(errors)) {
			return new APIResponse<Long>(HttpStatus.BAD_REQUEST, errors);
		}

		// Search old record
		TypeTbl tbl = typeRepository.findFirstByTypeClassAndTypeCode(formatTypeClass(vo.getTypeClass()),
				formatTypeCode(vo.getTypeCode()));
		if (tbl == null) {
			return new APIResponse<Long>(HttpStatus.BAD_REQUEST,
					ResponseCode.mapParam(ResponseCode.INEXISTED_RECORD, "typeCode/typeClass"));
		}

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
		if (vo.getUrl() != null) {
			tbl.setUrl(vo.getUrl());
		}

		APIResponse<Long> result = new APIResponse<Long>();
		result.setData(typeRepository.save(tbl).getId());

		// Update cache
		Map<String, TypeVO> typeCache = instance.getMap("typeCache");
		typeCache.put(formatTypeMapKey(tbl.getTypeClass(), tbl.getTypeCode()), typeTransformer.convertToVO(tbl));

		// Return
		return result;
	}

	/**
	 * Search
	 */
	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public APIResponse<Object> search(TypeSCO sco) {
		// Declare result
		APIResponse<Object> result = new APIResponse<Object>();

		// Get data
		Page<TypeTbl> page = typeRepository.findAll(sco);

		// Transformer
		PageResultVO<TypeVO> data = typeTransformer.convertToPageReturn(page);

		// Set data return
		result.setData(data);

		// Return
		return result;
	}

	/**
	 * Search list
	 */
	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public List<TypeTbl> searchList(TypeSCO sco) {
		// Declare result
		List<TypeTbl> result = new ArrayList<TypeTbl>();

		// Get data
		Page<TypeTbl> page = typeRepository.findAll(sco);

		// Transformer
		if (!CollectionUtils.isEmpty(page.getContent())) {
			for (TypeTbl tbl : page.getContent()) {
				result.add(tbl);
			}
		}

		// Return
		return result;
	}
}
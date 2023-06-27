package com.hientran.sohebox.cache;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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

	public TypeVO getType(String typeClass, String typeCode) {
		// Declare result
		TypeVO result = null;

		// Retrieve type cache
		Map<String, TypeVO> typeCache = instance.getMap("typeCache");
		TypeVO cacheValue = typeCache.get(formatTypeMapKey(typeClass, typeCode));

		// Return if have in cache
		if (cacheValue != null) {
			result = cacheValue;
		} else {
			TypeTbl tbl = typeRepository.findFirstByTypeClassAndTypeCode(formatTypeClass(typeClass),
					formatTypeCode(typeCode));
			if (tbl != null) {
				result = typeTransformer.convertToTypeVO(tbl);
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

				// Add new type
				TypeTbl newType = typeRepository.save(typeTransformer.convertToTypeTbl(vo));

				// Result
				result = typeTransformer.convertToTypeVO(newType);
			}

			// Add to cache
			typeCache.put(formatTypeMapKey(result.getTypeClass(), result.getTypeCode()), result);
		}

		// Return
		return result;
	}

	/**
	 * 
	 * Create new
	 * 
	 */
	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public APIResponse<Long> create(TypeVO vo) {
		// Declare result
		APIResponse<Long> result = new APIResponse<Long>();

		// Validate input
		if (result.getStatus() == null) {
			List<String> errors = new ArrayList<>();

			// Type class must be not null
			if (StringUtils.isBlank(vo.getTypeClass())) {
				errors.add(ResponseCode.mapParam(ResponseCode.FILED_EMPTY, TypeTblEnum.typeClass.name()));
			}

			// Type code must be not null
			if (StringUtils.isBlank(vo.getTypeCode())) {
				errors.add(ResponseCode.mapParam(ResponseCode.FILED_EMPTY, TypeTblEnum.typeCode.name()));
			}

			// Type name must be not null
			if (StringUtils.isBlank(vo.getTypeName())) {
				errors.add(ResponseCode.mapParam(ResponseCode.FILED_EMPTY, TypeTblEnum.typeName.name()));
			}

			// Record error
			if (!CollectionUtils.isEmpty(errors)) {
				result = new APIResponse<Long>(HttpStatus.BAD_REQUEST, errors);
			}
		}

		// Check if record is not existed
		if (result.getStatus() == null) {
			TypeTbl tbl = typeRepository.findFirstByTypeClassAndTypeCode(formatTypeClass(vo.getTypeClass()),
					formatTypeCode(vo.getTypeCode()));
			if (tbl != null) {
				result = new APIResponse<Long>(HttpStatus.BAD_REQUEST,
						ResponseCode.mapParam(ResponseCode.EXISTED_RECORD, "typeCode/typeClass"));
			}
		}

		// Create default
		if (result.getStatus() == null) {
			// Create new type
			TypeTbl tbl = new TypeTbl();
			tbl.setTypeClass(formatTypeClass(vo.getTypeClass()));
			tbl.setTypeCode(formatTypeCode(vo.getTypeCode()));
			tbl.setTypeName(
					vo.getTypeCode().substring(0, 1).toUpperCase() + vo.getTypeCode().substring(1).toLowerCase());
			tbl.setDescription(vo.getDescription());
			tbl.setIconUrl(vo.getIconUrl());
			tbl.setUrl(vo.getUrl());
			tbl = typeRepository.save(tbl);

			// Set return result
			result.setData(tbl.getId());
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
	public APIResponse<Long> updateType(TypeVO vo) {
		// Declare result
		APIResponse<Long> result = new APIResponse<Long>();

		// Validate input
		if (result.getStatus() == null) {
			List<String> errors = new ArrayList<>();

			// Type Class must be not null
			if (StringUtils.isBlank(vo.getTypeClass())) {
				errors.add(ResponseCode.mapParam(ResponseCode.FILED_EMPTY, TypeTblEnum.typeClass.name()));
			}

			// Type Code must be not null
			if (StringUtils.isBlank(vo.getTypeCode())) {
				errors.add(ResponseCode.mapParam(ResponseCode.FILED_EMPTY, TypeTblEnum.typeCode.name()));
			}

			// Type Name must be not null
			if (StringUtils.isBlank(vo.getTypeName())) {
				errors.add(ResponseCode.mapParam(ResponseCode.FILED_EMPTY, TypeTblEnum.typeName.name()));
			}

			// Record error
			if (!CollectionUtils.isEmpty(errors)) {
				return new APIResponse<Long>(HttpStatus.BAD_REQUEST, errors);
			}
		}

		// Search old record
		TypeTbl tbl = typeRepository.findFirstByTypeClassAndTypeCode(formatTypeClass(vo.getTypeClass()),
				formatTypeCode(vo.getTypeCode()));

		// Update if found, else return not found exception
		if (tbl != null) {
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
			result.setData(typeRepository.save(tbl).getId());

			// Update cache
			Map<String, TypeVO> typeCache = instance.getMap("typeCache");
			typeCache.put(formatTypeMapKey(tbl.getTypeClass(), tbl.getTypeCode()),
					typeTransformer.convertToTypeVO(tbl));
			typeCache.get(formatTypeMapKey(tbl.getTypeClass(), tbl.getTypeCode()));
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
	 * Search
	 * 
	 * @param sco
	 * @return
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

	/**
	 * Get all type class
	 * 
	 * @param sco
	 * @return
	 */
	public APIResponse<Object> getAllTypeClass() {
		// Declare result
		APIResponse<Object> result = new APIResponse<Object>();

		// Get list type class
		List<Object[]> searchResult = typeRepository.getAllTypeClass(entityManager);

		// Transformer
		if (!CollectionUtils.isEmpty(searchResult)) {
			// Prepare item list
			List<String> listElement = new ArrayList<>();
			for (Object[] objects : searchResult) {
				listElement.add((String) objects[0]);
			}

			// Prepare page result
			PageResultVO<String> data = new PageResultVO<String>();
			data.setElements(listElement);
			data.setCurrentPage(0);
			data.setTotalPage(1);
			data.setTotalElement(listElement.size());

			// Set data return
			result.setData(data);
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
		Optional<TypeTbl> tbl = typeRepository.findById(id);
		if (!tbl.isPresent()) {
			result = new APIResponse<Object>(HttpStatus.BAD_REQUEST,
					ResponseCode.mapParam(ResponseCode.INEXISTED_RECORD, "type"));
		} else {
			// Set return data
			TypeVO vo = typeTransformer.convertToTypeVO(tbl.get());
			result.setData(vo);
		}

		// Return
		return result;
	}

}

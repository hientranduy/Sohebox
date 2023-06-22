package com.hientran.sohebox.cache;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.hazelcast.core.HazelcastInstance;
import com.hientran.sohebox.constants.DBConstants;
import com.hientran.sohebox.constants.MessageConstants;
import com.hientran.sohebox.constants.enums.TypeTblEnum;
import com.hientran.sohebox.entity.TypeTbl;
import com.hientran.sohebox.exception.APIResponse;
import com.hientran.sohebox.repository.TypeRepository;
import com.hientran.sohebox.sco.SearchTextVO;
import com.hientran.sohebox.sco.TypeSCO;
import com.hientran.sohebox.specification.TypeSpecs;
import com.hientran.sohebox.transformer.TypeTransformer;
import com.hientran.sohebox.vo.PageResultVO;
import com.hientran.sohebox.vo.TypeVO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class TypeCache extends BaseCache {

	@Autowired
	private TypeRepository typeRepository;

	@Autowired
	private TypeTransformer typeTransformer;

	@Autowired
	private TypeSpecs typeSpecs;

	@Autowired
	private HazelcastInstance instance;

	/**
	 * 
	 * Return vo by id
	 *
	 * @param key
	 * @return
	 */
	public TypeVO getType(String typeClass, String typeCode) {
		// Declare result
		TypeVO result = null;

		// Retrieve type cache
		Map<TypeCacheKey, TypeVO> typeCache = instance.getMap("typeCache");

		// Search type
		TypeVO cacheValue = typeCache.get(new TypeCacheKey(formatTypeClass(typeClass), formatTypeCode(typeCode)));

		// Return if have in cache
		if (cacheValue != null) {
			result = cacheValue;
		} else {

			// Get data from DB
			SearchTextVO typeClassSearch = new SearchTextVO();
			typeClassSearch.setEq(formatTypeClass(typeClass));
			SearchTextVO typeCodeSearch = new SearchTextVO();
			typeCodeSearch.setEq(formatTypeCode(typeCode));

			TypeSCO sco = new TypeSCO();
			sco.setTypeClass(typeClassSearch);
			sco.setTypeCode(typeCodeSearch);

			Page<TypeTbl> pageTbl = typeRepository.findAll(sco);
			if (!CollectionUtils.isEmpty(pageTbl.getContent())) {
				result = typeTransformer.convertToTypeVO(pageTbl.getContent().get(0));

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
			typeCache.put(new TypeCacheKey(result.getTypeClass(), result.getTypeCode()), result);
		}

		// Return
		return result;
	}

	/**
	 * 
	 * Update an existed type of DB and Cache
	 *
	 * @param vo
	 */
	public void update(TypeVO vo) {
		// Search old record
		SearchTextVO searchClass = new SearchTextVO();
		searchClass.setEq(formatTypeClass(vo.getTypeClass()));
		SearchTextVO searchCode = new SearchTextVO();
		searchCode.setEq(formatTypeCode(vo.getTypeCode()));

		TypeSCO sco = new TypeSCO();
		sco.setTypeClass(searchClass);
		sco.setTypeCode(searchCode);

		Optional<TypeTbl> searchResult = typeRepository.findOne(typeSpecs.buildSpecification(sco));

		// Update if found, else return not found exception
		if (searchResult.isPresent()) {
			TypeTbl tbl = searchResult.get();

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
			typeRepository.save(tbl);

			// Update cache
			Map<TypeCacheKey, TypeVO> typeCache = instance.getMap("typeCache");
			typeCache.put(new TypeCacheKey(tbl.getTypeClass(), tbl.getTypeCode()),
					typeTransformer.convertToTypeVO(tbl));

		} else {
			log.error("Type not found to update, typeClass: " + vo.getTypeClass() + ", typeCode: " + vo.getTypeCode());
		}
	}

	/**
	 * 
	 * Delete a type
	 *
	 * @param key
	 */
	public void delete(Long id) {
		// Check existed
		TypeTbl tbl = typeRepository.findById(id).get();

		// Delete if found, else return not found exception
		if (tbl != null) {
			typeRepository.delete(tbl);

			// Remove from cache
			Map<TypeCacheKey, TypeVO> typeCache = instance.getMap("typeCache");
			typeCache.remove(new TypeCacheKey(tbl.getTypeClass(), tbl.getTypeCode()));

		} else {
			log.error("Type not found to delete, id: " + id);
		}
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
				errors.add(buildMessage(MessageConstants.FILED_EMPTY, new String[] { TypeTblEnum.typeClass.name() }));
			}

			// Type Code must be not null
			if (StringUtils.isBlank(vo.getTypeCode())) {
				errors.add(buildMessage(MessageConstants.FILED_EMPTY, new String[] { TypeTblEnum.typeCode.name() }));
			}

			// Type Name must be not null
			if (StringUtils.isBlank(vo.getTypeName())) {
				errors.add(buildMessage(MessageConstants.FILED_EMPTY, new String[] { TypeTblEnum.typeName.name() }));
			}

			// Record error
			if (!CollectionUtils.isEmpty(errors)) {
				result = new APIResponse<Long>(HttpStatus.BAD_REQUEST, errors);
			}
		}

		// Update
		if (result.getStatus() == null) {
			update(vo);
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
					buildMessage(MessageConstants.INEXISTED_RECORD, new String[] { "type" }));
		} else {
			// Set return data
			TypeVO vo = typeTransformer.convertToTypeVO(tbl.get());
			result.setData(vo);
		}

		// Return
		return result;
	}

	/**
	 * 
	 * Create new
	 * 
	 * @param vo
	 * @return
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
				errors.add(buildMessage(MessageConstants.FILED_EMPTY, new String[] { TypeTblEnum.typeClass.name() }));
			}

			// Type code must be not null
			if (StringUtils.isBlank(vo.getTypeCode())) {
				errors.add(buildMessage(MessageConstants.FILED_EMPTY, new String[] { TypeTblEnum.typeCode.name() }));
			}

			// Type name must be not null
			if (StringUtils.isBlank(vo.getTypeName())) {
				errors.add(buildMessage(MessageConstants.FILED_EMPTY, new String[] { TypeTblEnum.typeName.name() }));
			}

			// Record error
			if (!CollectionUtils.isEmpty(errors)) {
				result = new APIResponse<Long>(HttpStatus.BAD_REQUEST, errors);
			}
		}

		// Check if record is not existed
		if (result.getStatus() == null) {
			SearchTextVO typeClassSearch = new SearchTextVO();
			typeClassSearch.setEq(vo.getTypeClass());
			SearchTextVO typeCodeSearch = new SearchTextVO();
			typeCodeSearch.setEq(vo.getTypeCode());

			TypeSCO sco = new TypeSCO();
			sco.setTypeClass(typeClassSearch);
			sco.setTypeCode(typeCodeSearch);

			List<TypeTbl> lists = searchList(sco);
			if (!CollectionUtils.isEmpty(lists)) {
				result = new APIResponse<Long>(HttpStatus.BAD_REQUEST,
						buildMessage(MessageConstants.EXISTED_RECORD, new String[] { "type" }));
			}
		}

		// Create
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
	 * Format type class
	 *
	 * @param vo
	 * @return
	 */
	private String formatTypeClass(String typeClass) {
		return typeClass.toUpperCase();
	}

	/**
	 * Format type code
	 *
	 * @param vo
	 * @return
	 */
	private String formatTypeCode(String typeCode) {
		return typeCode.replaceAll(" ", "_").toUpperCase();
	}
}

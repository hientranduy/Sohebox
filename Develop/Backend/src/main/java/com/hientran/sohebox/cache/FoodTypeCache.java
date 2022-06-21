package com.hientran.sohebox.cache;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hazelcast.core.HazelcastInstance;
import com.hientran.sohebox.constants.DBConstants;
import com.hientran.sohebox.constants.MessageConstants;
import com.hientran.sohebox.constants.enums.FoodTypeTblEnum;
import com.hientran.sohebox.entity.FoodTypeTbl;
import com.hientran.sohebox.exception.APIResponse;
import com.hientran.sohebox.repository.FoodTypeRepository;
import com.hientran.sohebox.sco.FoodTypeSCO;
import com.hientran.sohebox.sco.SearchTextVO;
import com.hientran.sohebox.specification.FoodTypeSpecs;
import com.hientran.sohebox.transformer.FoodTypeTransformer;
import com.hientran.sohebox.vo.FoodTypeVO;
import com.hientran.sohebox.vo.PageResultVO;

/**
 * Cache of table typeTbl
 *
 * @author hientran
 */
@Service
public class FoodTypeCache extends BaseCache {

    private static final long serialVersionUID = 1L;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private FoodTypeRepository typeRepository;

    @Autowired
    private FoodTypeTransformer typeTransformer;

    @Autowired
    private FoodTypeSpecs typeSpecs;

    @Autowired
    private HazelcastInstance instance;

    /**
     * 
     * Return vo by id
     *
     * @param key
     * @return
     */
    public FoodTypeVO getType(String typeClass, String typeCode) {
        // Declare result
        FoodTypeVO result = null;

        // Retrieve type cache
        Map<TypeCacheKey, FoodTypeVO> typeCache = instance.getMap("typeCache");

        // Search type
        FoodTypeVO cacheValue = typeCache.get(new TypeCacheKey(formatTypeClass(typeClass), formatTypeCode(typeCode)));

        // Return if have in cache
        if (cacheValue != null) {
            result = cacheValue;
        } else {

            // Get data from DB
            SearchTextVO typeClassSearch = new SearchTextVO();
            typeClassSearch.setEq(formatTypeClass(typeClass));
            SearchTextVO typeCodeSearch = new SearchTextVO();
            typeCodeSearch.setEq(formatTypeCode(typeCode));

            FoodTypeSCO sco = new FoodTypeSCO();
            sco.setTypeClass(typeClassSearch);
            sco.setTypeCode(typeCodeSearch);

            Page<FoodTypeTbl> pageTbl = typeRepository.findAll(sco);
            if (CollectionUtils.isNotEmpty(pageTbl.getContent())) {
                result = typeTransformer.convertToFoodTypeVO(pageTbl.getContent().get(0));

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

                // Add new type
                FoodTypeTbl newType = typeRepository.save(typeTransformer.convertToFoodTypeTbl(vo));

                // Result
                result = typeTransformer.convertToFoodTypeVO(newType);
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
    public void update(FoodTypeVO vo) {
        // Search old record
        SearchTextVO searchClass = new SearchTextVO();
        searchClass.setEq(formatTypeClass(vo.getTypeClass()));
        SearchTextVO searchCode = new SearchTextVO();
        searchCode.setEq(formatTypeCode(vo.getTypeCode()));

        FoodTypeSCO sco = new FoodTypeSCO();
        sco.setTypeClass(searchClass);
        sco.setTypeCode(searchCode);

        Optional<FoodTypeTbl> searchResult = typeRepository.findOne(typeSpecs.buildSpecification(sco));

        // Update if found, else return not found exception
        if (searchResult.isPresent()) {
            FoodTypeTbl tbl = searchResult.get();

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
            typeRepository.save(tbl);

            // Update cache
            Map<TypeCacheKey, FoodTypeVO> typeCache = instance.getMap("typeCache");
            typeCache.put(new TypeCacheKey(tbl.getTypeClass(), tbl.getTypeCode()),
                    typeTransformer.convertToFoodTypeVO(tbl));

        } else {
            logger.error(
                    "Type not found to update, typeClass: " + vo.getTypeClass() + ", typeCode: " + vo.getTypeCode());
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
        FoodTypeTbl tbl = typeRepository.findById(id).get();

        // Delete if found, else return not found exception
        if (tbl != null) {
            typeRepository.delete(tbl);

            // Remove from cache
            Map<TypeCacheKey, FoodTypeVO> typeCache = instance.getMap("typeCache");
            typeCache.remove(new TypeCacheKey(tbl.getTypeClass(), tbl.getTypeCode()));

        } else {
            logger.error("Type not found to delete, id: " + id);
        }
    }

    /**
     * Search
     * 
     * @param sco
     * @return
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
     * Get all type class
     * 
     * @param sco
     * @return
     */
    public APIResponse<Object> getAllTypeClass() {
        // Declare result
        APIResponse<Object> result = new APIResponse<Object>();

        // Get list type class
        List<Object[]> searchResult = typeRepository.getAllTypeClass();

        // Transformer
        if (CollectionUtils.isNotEmpty(searchResult)) {
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
    public APIResponse<Long> updateType(FoodTypeVO vo) {
        // Declare result
        APIResponse<Long> result = new APIResponse<Long>();

        // Validate input
        if (result.getStatus() == null) {
            List<String> errors = new ArrayList<>();

            // Type Class must be not null
            if (StringUtils.isBlank(vo.getTypeClass())) {
                errors.add(
                        buildMessage(MessageConstants.FILED_EMPTY, new String[] { FoodTypeTblEnum.typeClass.name() }));
            }

            // Type Code must be not null
            if (StringUtils.isBlank(vo.getTypeCode())) {
                errors.add(
                        buildMessage(MessageConstants.FILED_EMPTY, new String[] { FoodTypeTblEnum.typeCode.name() }));
            }

            // Type Name must be not null
            if (StringUtils.isBlank(vo.getTypeName())) {
                errors.add(
                        buildMessage(MessageConstants.FILED_EMPTY, new String[] { FoodTypeTblEnum.typeName.name() }));
            }

            // Record error
            if (CollectionUtils.isNotEmpty(errors)) {
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
        Optional<FoodTypeTbl> tbl = typeRepository.findById(id);
        if (!tbl.isPresent()) {
            result = new APIResponse<Object>(HttpStatus.BAD_REQUEST,
                    buildMessage(MessageConstants.INEXISTED_RECORD, new String[] { "type" }));
        } else {
            // Set return data
            FoodTypeVO vo = typeTransformer.convertToFoodTypeVO(tbl.get());
            result.setData(vo);
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

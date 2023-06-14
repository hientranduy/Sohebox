package com.hientran.sohebox.cache;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.hazelcast.core.HazelcastInstance;
import com.hientran.sohebox.constants.MessageConstants;
import com.hientran.sohebox.constants.enums.ConfigTblEnum;
import com.hientran.sohebox.entity.ConfigTbl;
import com.hientran.sohebox.exception.APIResponse;
import com.hientran.sohebox.repository.ConfigRepository;
import com.hientran.sohebox.sco.ConfigSCO;
import com.hientran.sohebox.sco.SearchTextVO;
import com.hientran.sohebox.specification.ConfigSpecs;
import com.hientran.sohebox.transformer.ConfigTransformer;
import com.hientran.sohebox.vo.ConfigVO;
import com.hientran.sohebox.vo.PageResultVO;

/**
 * Cache of table configTbl
 *
 * @author hientran
 */
@Service
public class ConfigCache extends BaseCache {

    private static final long serialVersionUID = 1L;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ConfigRepository configRepository;

    @Autowired
    private ConfigTransformer configTransformer;

    @Autowired
    private ConfigSpecs configSpecs;

    @Autowired
    private HazelcastInstance instance;

    /**
     * 
     * Get config value by key
     *
     * @param key
     * @return
     */
    public String getValueByKey(String key) {
        // Declare result
        String result = null;

        // Get from cache first
        Map<String, ConfigVO> configCache = instance.getMap("configCache");
        ConfigVO cacheValue = configCache.get(formatConfigKey(key));

        // Return if have in cache
        if (cacheValue != null) {
            result = cacheValue.getConfigValue();
        } else {

            // Get data from DB
            ConfigTbl tbl = searchByConfigKey(key);
            if (tbl != null) {
                result = tbl.getConfigValue();

                // Add to cache
                configCache.put(key, configTransformer.convertToConfigVO(tbl));
            }
        }

        // Return
        return result;
    }

    /**
     * 
     * Delete a config
     *
     * @param key
     */
    public void delete(String key) {
        // Check existed
        ConfigTbl tbl = searchByConfigKey(key);

        // Delete if found, else return not found exception
        if (tbl != null) {
            configRepository.delete(tbl);

            // Remove from cache
            Map<String, ConfigVO> configCache = instance.getMap("configCache");
            configCache.remove(key);

        } else {
            logger.error("Config not found to delete, key: " + key);
        }
    }

    /**
     * Search
     * 
     * @param sco
     * @return
     */
    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public APIResponse<Object> search(ConfigSCO sco) {
        // Declare result
        APIResponse<Object> result = new APIResponse<Object>();

        // Get data
        Page<ConfigTbl> page = configRepository.findAll(sco);

        // Transformer
        PageResultVO<ConfigVO> data = configTransformer.convertToPageReturn(page);

        // Set data return
        result.setData(data);

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
    public APIResponse<Long> update(ConfigVO vo) {
        // Declare result
        APIResponse<Long> result = new APIResponse<Long>();

        // Validate input
        if (result.getStatus() == null) {
            List<String> errors = new ArrayList<>();

            // Config Key must be not null
            if (StringUtils.isBlank(vo.getConfigKey())) {
                errors.add(buildMessage(MessageConstants.FILED_EMPTY, new String[] { ConfigTblEnum.configKey.name() }));
            }

            // Config Value must be not null
            if (StringUtils.isBlank(vo.getConfigValue())) {
                errors.add(
                        buildMessage(MessageConstants.FILED_EMPTY, new String[] { ConfigTblEnum.configValue.name() }));
            }

            // Record error
            if (!CollectionUtils.isEmpty(errors)) {
                result = new APIResponse<Long>(HttpStatus.BAD_REQUEST, errors);
            }
        }

        // Check if record is existed
        ConfigTbl updateTbl = null;
        if (result.getStatus() == null) {
            updateTbl = searchByConfigKey(vo.getConfigKey());
            if (updateTbl == null) {
                result = new APIResponse<Long>(HttpStatus.BAD_REQUEST,
                        buildMessage(MessageConstants.INEXISTED_RECORD, new String[] { "config" }));
            }
        }

        // Update
        if (result.getStatus() == null) {
            updateTbl.setConfigValue(vo.getConfigValue());
            if (vo.getDescription() != null) {
                updateTbl.setDescription(vo.getDescription());
            }
            updateTbl = configRepository.save(updateTbl);

            // Update cache
            Map<String, ConfigVO> configCache = instance.getMap("configCache");
            configCache.put(vo.getConfigKey(), configTransformer.convertToConfigVO(updateTbl));
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
        Optional<ConfigTbl> tbl = configRepository.findById(id);
        if (!tbl.isPresent()) {
            result = new APIResponse<Object>(HttpStatus.BAD_REQUEST,
                    buildMessage(MessageConstants.INEXISTED_RECORD, new String[] { "config" }));
        } else {
            // Set return data
            ConfigVO vo = configTransformer.convertToConfigVO(tbl.get());
            result.setData(vo);

            // Add to cache
            Map<String, ConfigVO> configCache = instance.getMap("configCache");
            configCache.put(vo.getConfigKey(), vo);
        }

        // Return
        return result;
    }

    /**
     * 
     * Create new
     * 
     * Anyone
     *
     * @param vo
     * @return
     */
    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public APIResponse<Long> create(ConfigVO vo) {
        // Declare result
        APIResponse<Long> result = new APIResponse<Long>();

        // Validate input
        if (result.getStatus() == null) {
            List<String> errors = new ArrayList<>();

            // Config Key must be not null
            if (StringUtils.isBlank(vo.getConfigKey())) {
                errors.add(buildMessage(MessageConstants.FILED_EMPTY, new String[] { ConfigTblEnum.configKey.name() }));
            }

            // Config Value must be not null
            if (StringUtils.isBlank(vo.getConfigValue())) {
                errors.add(
                        buildMessage(MessageConstants.FILED_EMPTY, new String[] { ConfigTblEnum.configValue.name() }));
            }

            // Record error
            if (!CollectionUtils.isEmpty(errors)) {
                result = new APIResponse<Long>(HttpStatus.BAD_REQUEST, errors);
            }
        }

        // Check if record is not existed
        ConfigTbl updateTbl = null;
        if (result.getStatus() == null) {
            updateTbl = searchByConfigKey(vo.getConfigKey());
            if (updateTbl != null) {
                result = new APIResponse<Long>(HttpStatus.BAD_REQUEST,
                        buildMessage(MessageConstants.EXISTED_RECORD, new String[] { "config" }));
            }
        }

        // Create
        if (result.getStatus() == null) {
            // Transform
            ConfigTbl tbl = configTransformer.convertToConfigTbl(vo);
            tbl.setConfigKey(formatConfigKey(tbl.getConfigKey()));

            tbl = configRepository.save(tbl);

            // Add to cache
            Map<String, ConfigVO> configCache = instance.getMap("configCache");
            configCache.put(vo.getConfigKey(), configTransformer.convertToConfigVO(tbl));

            // Set return result
            result.setData(tbl.getId());
        }

        // Return
        return result;
    }

    /**
     * 
     * Search configTbl by config key
     *
     * @param key
     * @return
     */
    private ConfigTbl searchByConfigKey(String key) {
        // Declare result
        ConfigTbl result = null;

        // Get Data
        SearchTextVO keySearch = new SearchTextVO();
        keySearch.setEq(formatConfigKey(key));
        ConfigSCO sco = new ConfigSCO();
        sco.setConfigKey(keySearch);

        Optional<ConfigTbl> tbl = configRepository.findOne(configSpecs.buildSpecification(sco));

        // Transformer
        if (tbl.isPresent()) {
            result = tbl.get();
        }

        // Return
        return result;
    }

    /**
     * Format type code
     *
     * @param vo
     * @return
     */
    private String formatConfigKey(String configKey) {
        return configKey.replaceAll(" ", "_").toUpperCase();
    }
}

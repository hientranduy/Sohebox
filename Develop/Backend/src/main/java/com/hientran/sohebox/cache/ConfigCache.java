package com.hientran.sohebox.cache;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.cache.CacheManager;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.hientran.sohebox.dto.ConfigVO;
import com.hientran.sohebox.dto.PageResultVO;
import com.hientran.sohebox.dto.response.APIResponse;
import com.hientran.sohebox.dto.response.ResponseCode;
import com.hientran.sohebox.entity.ConfigTbl;
import com.hientran.sohebox.repository.ConfigRepository;
import com.hientran.sohebox.sco.ConfigSCO;
import com.hientran.sohebox.specification.ConfigSpecs.ConfigTblEnum;
import com.hientran.sohebox.transformer.BaseTransformer;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ConfigCache extends BaseTransformer {

	private final ConfigRepository configRepository;

	private final CacheManager cacheManager;
	private String cacheName = "configCache";

	/**
	 * Get
	 */
	public String getValueByKey(String key) {
		// Declare result
		String result = null;

		// Get cache
		result = cacheManager.getCache(cacheName).get(formatConfigKey(key), String.class);
		if (result != null) {
			return result;
		}

		// Get from DB
		ConfigTbl tbl = configRepository.findFirstByConfigKey(key);
		if (tbl != null) {
			result = tbl.getConfigValue();

			// Add to cache
			cacheManager.getCache(cacheName).put(formatConfigKey(key), tbl.getConfigValue());
		}

		// Return
		return result;
	}

	/**
	 * Create
	 */
	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public APIResponse<Long> create(ConfigVO vo) {

		// Validate input
		List<String> errors = new ArrayList<>();
		if (StringUtils.isBlank(vo.getConfigKey())) {
			errors.add(ResponseCode.mapParam(ResponseCode.FILED_EMPTY, ConfigTblEnum.configKey.name()));
		}
		if (StringUtils.isBlank(vo.getConfigValue())) {
			errors.add(ResponseCode.mapParam(ResponseCode.FILED_EMPTY, ConfigTblEnum.configValue.name()));
		}
		if (!CollectionUtils.isEmpty(errors)) {
			return new APIResponse<>(HttpStatus.BAD_REQUEST, errors);
		}

		// Valid existed record
		ConfigTbl searchTbl = configRepository.findFirstByConfigKey(vo.getConfigKey());
		if (searchTbl != null) {
			return new APIResponse<>(HttpStatus.BAD_REQUEST,
					ResponseCode.mapParam(ResponseCode.EXISTED_RECORD, "config"));
		}

		// Create
		ConfigTbl tbl = new ConfigTbl();
		BeanUtils.copyProperties(vo, tbl);
		tbl.setConfigKey(formatConfigKey(tbl.getConfigKey()));
		tbl = configRepository.save(tbl);

		// Return
		APIResponse<Long> result = new APIResponse<>();
		result.setData(tbl.getId());
		return result;
	}

	/**
	 * Update
	 */
	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public APIResponse<Long> update(ConfigVO vo) {

		// Validate input
		List<String> errors = new ArrayList<>();
		if (StringUtils.isBlank(vo.getConfigKey())) {
			errors.add(ResponseCode.mapParam(ResponseCode.FILED_EMPTY, ConfigTblEnum.configKey.name()));
		}
		if (StringUtils.isBlank(vo.getConfigValue())) {
			errors.add(ResponseCode.mapParam(ResponseCode.FILED_EMPTY, ConfigTblEnum.configValue.name()));
		}
		if (!CollectionUtils.isEmpty(errors)) {
			return new APIResponse<>(HttpStatus.BAD_REQUEST, errors);
		}

		// Valid existed record
		ConfigTbl updateTbl = configRepository.findFirstByConfigKey(vo.getConfigKey());
		if (updateTbl == null) {
			return new APIResponse<>(HttpStatus.BAD_REQUEST,
					ResponseCode.mapParam(ResponseCode.INEXISTED_RECORD, "config"));
		}

		// Update
		updateTbl.setConfigValue(vo.getConfigValue());
		if (vo.getDescription() != null) {
			updateTbl.setDescription(vo.getDescription());
		}

		APIResponse<Long> result = new APIResponse<>();
		result.setData(configRepository.save(updateTbl).getId());

		// Update cache
		cacheManager.getCache(cacheName).put(formatConfigKey(vo.getConfigKey()), updateTbl.getConfigValue());

		// Return
		return result;
	}

	/**
	 * Search
	 */
	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public APIResponse<Object> search(ConfigSCO sco) {
		// Declare result
		APIResponse<Object> result = new APIResponse<>();

		// Get data
		Page<ConfigTbl> page = configRepository.findAll(sco);

		// Transformer
		PageResultVO<ConfigTbl> data = new PageResultVO<>();
		data.setElements(page.getContent());
		setPageHeader(page, data);

		// Set data return
		result.setData(data);

		// Return
		return result;
	}

	/**
	 * Format config key
	 */
	private String formatConfigKey(String configKey) {
		return configKey.replaceAll(" ", "_").toUpperCase();
	}
}

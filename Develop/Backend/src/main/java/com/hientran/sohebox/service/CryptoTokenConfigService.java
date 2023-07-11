package com.hientran.sohebox.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hientran.sohebox.constants.CosmosConstants;
import com.hientran.sohebox.constants.DBConstants;
import com.hientran.sohebox.dto.PageResultVO;
import com.hientran.sohebox.dto.response.APIResponse;
import com.hientran.sohebox.dto.response.ResponseCode;
import com.hientran.sohebox.entity.CryptoTokenConfigTbl;
import com.hientran.sohebox.repository.CryptoTokenConfigRepository;
import com.hientran.sohebox.sco.CryptoTokenConfigSCO;
import com.hientran.sohebox.sco.SearchTextVO;
import com.hientran.sohebox.specification.CryptoTokenConfigSpecs.CryptoTokenConfigTblEnum;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CryptoTokenConfigService extends BaseService {
	private final CryptoTokenConfigRepository cryptoTokenConfigRepository;

	/**
	 *
	 * Create
	 *
	 * @param rq
	 * @return
	 * @throws IOException
	 */
	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public APIResponse<Long> create(CryptoTokenConfigTbl rq) {
		// Declare result
		APIResponse<Long> result = new APIResponse<>();

		// Validate input
		if (result.getStatus() == null) {
			List<String> errors = new ArrayList<>();

			if (StringUtils.isBlank(rq.getTokenCode())) {
				errors.add(ResponseCode.mapParam(ResponseCode.FILED_EMPTY, CryptoTokenConfigTblEnum.tokenCode.name()));
			}

			if (StringUtils.isBlank(rq.getTokenName())) {
				errors.add(ResponseCode.mapParam(ResponseCode.FILED_EMPTY, CryptoTokenConfigTblEnum.tokenName.name()));
			}

			// Record error
			if (CollectionUtils.isNotEmpty(errors)) {
				result = new APIResponse<>(HttpStatus.BAD_REQUEST, errors);
			}
		}

		// Check existence
		if (result.getStatus() == null) {
			if (getByName(rq.getTokenCode()) != null) {
				result = new APIResponse<>(HttpStatus.BAD_REQUEST,
						ResponseCode.mapParam(ResponseCode.EXISTED_RECORD, "token <" + rq.getTokenCode() + ">"));
			}
		}

		/////////////////////
		// Record new //
		/////////////////////
		if (result.getStatus() == null) {
			// Transform
			CryptoTokenConfigTbl tbl = rq;

			// Create
			tbl = cryptoTokenConfigRepository.save(tbl);

			// Set default
			if (tbl.getDecimalExponent() <= 0) {
				tbl.setDecimalExponent(Long.valueOf(CosmosConstants.COSMOS_DECIMAL_EXPONENT));
			}

			// Set id return
			result.setData(tbl.getId());

			// Write activity
			recordUserActivity(DBConstants.USER_ACTIVITY_CRYPTO_TOKEN_CONFIG_CREATE);
		}

		// Return
		return result;
	}

	/**
	 *
	 * Update
	 *
	 * @param rq
	 * @return
	 */
	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public APIResponse<Long> update(CryptoTokenConfigTbl rq) {
		// Declare result
		APIResponse<Long> result = new APIResponse<>();

		// Validate input
		if (result.getStatus() == null) {
			List<String> errors = new ArrayList<>();

			if (StringUtils.isBlank(rq.getTokenCode())) {
				errors.add(ResponseCode.mapParam(ResponseCode.FILED_EMPTY, CryptoTokenConfigTblEnum.tokenCode.name()));
			}

			// Record error
			if (CollectionUtils.isNotEmpty(errors)) {
				result = new APIResponse<>(HttpStatus.BAD_REQUEST, errors);
			}
		}

		// Get the old record
		CryptoTokenConfigTbl updateTbl = null;
		if (result.getStatus() == null) {
			updateTbl = getByName(rq.getTokenCode());
			if (updateTbl == null) {
				result = new APIResponse<>(HttpStatus.BAD_REQUEST,
						ResponseCode.mapParam(ResponseCode.INEXISTED_RECORD, "token <" + rq.getTokenCode() + ">"));
			}
		}

		/////////////////////
		// Update //
		/////////////////////
		if (result.getStatus() == null) {

			if (rq.getTokenName() != null) {
				updateTbl.setTokenName(rq.getTokenName());
			}

			if (rq.getIconUrl() != null) {
				updateTbl.setIconUrl(rq.getIconUrl());
			}

			if (rq.getNodeUrl() != null) {
				updateTbl.setNodeUrl(rq.getNodeUrl());
			}

			if (rq.getRpcUrl() != null) {
				updateTbl.setRpcUrl(rq.getRpcUrl());
			}

			if (rq.getDenom() != null) {
				updateTbl.setDenom(rq.getDenom());
			}

			if (rq.getDecimalExponent() != null && rq.getDecimalExponent() > 0) {
				updateTbl.setDecimalExponent(rq.getDecimalExponent());
			}

			if (rq.getAddressPrefix() != null) {
				updateTbl.setAddressPrefix(rq.getAddressPrefix());
			}

			if (rq.getMintscanPrefix() != null) {
				updateTbl.setMintscanPrefix(rq.getMintscanPrefix());
			}

			if (rq.getDeligateUrl() != null) {
				updateTbl.setDeligateUrl(rq.getDeligateUrl());
			}

			// Update
			updateTbl = cryptoTokenConfigRepository.save(updateTbl);

			// Set id return
			result.setData(updateTbl.getId());

			// Write activity
			recordUserActivity(DBConstants.USER_ACTIVITY_CRYPTO_TOKEN_CONFIG_UPDATE);
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
	public APIResponse<Object> search(CryptoTokenConfigSCO sco) {
		// Declare result
		APIResponse<Object> result = new APIResponse<>();

		// Get data
		Page<CryptoTokenConfigTbl> page = cryptoTokenConfigRepository.findAll(sco);

		// Transformer
		PageResultVO<CryptoTokenConfigTbl> data = new PageResultVO<>();
		if (!CollectionUtils.isEmpty(page.getContent())) {
			data.setElements(page.getContent());
			setPageHeader(page, data);
		}

		// Set data return
		result.setData(data);

		// Write activity
		recordUserActivity(DBConstants.USER_ACTIVITY_CRYPTO_TOKEN_CONFIG_ACCESS);

		// Return
		return result;
	}

	/**
	 *
	 * Get by name
	 *
	 * @param name
	 * @return
	 */
	public CryptoTokenConfigTbl getByName(String nameValue) {
		// Declare result
		CryptoTokenConfigTbl result = null;

		SearchTextVO nameSearch = new SearchTextVO();
		nameSearch.setEq(nameValue);

		CryptoTokenConfigSCO sco = new CryptoTokenConfigSCO();
		sco.setTokenCode(nameSearch);

		// Get data
		List<CryptoTokenConfigTbl> list = cryptoTokenConfigRepository.findAll(sco).getContent();
		if (CollectionUtils.isNotEmpty(list)) {
			result = list.get(0);
		}

		// Return
		return result;
	}

	/**
	 * Get by id
	 *
	 * @param id
	 * @return
	 */
	public APIResponse<Object> getById(Long id) {
		// Declare result
		APIResponse<Object> result = new APIResponse<>();

		// Check existence
		Optional<CryptoTokenConfigTbl> CryptoTokenConfigTbl = cryptoTokenConfigRepository.findById(id);
		if (CryptoTokenConfigTbl.isPresent()) {
			result.setData(CryptoTokenConfigTbl.get());
		} else {
			result = new APIResponse<>(HttpStatus.BAD_REQUEST,
					ResponseCode.mapParam(ResponseCode.INEXISTED_RECORD, "token"));
		}

		// Return
		return result;
	}
}

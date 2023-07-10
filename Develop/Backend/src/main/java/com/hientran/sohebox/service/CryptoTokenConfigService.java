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
import com.hientran.sohebox.dto.CryptoTokenConfigVO;
import com.hientran.sohebox.dto.PageResultVO;
import com.hientran.sohebox.dto.response.APIResponse;
import com.hientran.sohebox.dto.response.ResponseCode;
import com.hientran.sohebox.entity.CryptoTokenConfigTbl;
import com.hientran.sohebox.repository.CryptoTokenConfigRepository;
import com.hientran.sohebox.sco.CryptoTokenConfigSCO;
import com.hientran.sohebox.sco.SearchTextVO;
import com.hientran.sohebox.specification.CryptoTokenConfigSpecs.CryptoTokenConfigTblEnum;
import com.hientran.sohebox.transformer.CryptoTokenConfigTransformer;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CryptoTokenConfigService extends BaseService {
	private final CryptoTokenConfigRepository cryptoTokenConfigRepository;
	private final CryptoTokenConfigTransformer cryptoTokenConfigTransformer;

	/**
	 *
	 * Create
	 *
	 * @param vo
	 * @return
	 * @throws IOException
	 */
	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public APIResponse<Long> create(CryptoTokenConfigVO vo) {
		// Declare result
		APIResponse<Long> result = new APIResponse<>();

		// Validate input
		if (result.getStatus() == null) {
			List<String> errors = new ArrayList<>();

			if (StringUtils.isBlank(vo.getTokenCode())) {
				errors.add(ResponseCode.mapParam(ResponseCode.FILED_EMPTY, CryptoTokenConfigTblEnum.tokenCode.name()));
			}

			if (StringUtils.isBlank(vo.getTokenName())) {
				errors.add(ResponseCode.mapParam(ResponseCode.FILED_EMPTY, CryptoTokenConfigTblEnum.tokenName.name()));
			}

			// Record error
			if (CollectionUtils.isNotEmpty(errors)) {
				result = new APIResponse<>(HttpStatus.BAD_REQUEST, errors);
			}
		}

		// Check existence
		if (result.getStatus() == null) {
			if (getByName(vo.getTokenCode()) != null) {
				result = new APIResponse<>(HttpStatus.BAD_REQUEST,
						ResponseCode.mapParam(ResponseCode.EXISTED_RECORD, "token <" + vo.getTokenCode() + ">"));
			}
		}

		/////////////////////
		// Record new //
		/////////////////////
		if (result.getStatus() == null) {
			// Transform
			CryptoTokenConfigTbl tbl = cryptoTokenConfigTransformer.convertToTbl(vo);

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
	 * @param vo
	 * @return
	 */
	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public APIResponse<Long> update(CryptoTokenConfigVO vo) {
		// Declare result
		APIResponse<Long> result = new APIResponse<>();

		// Validate input
		if (result.getStatus() == null) {
			List<String> errors = new ArrayList<>();

			if (StringUtils.isBlank(vo.getTokenCode())) {
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
			updateTbl = getByName(vo.getTokenCode());
			if (updateTbl == null) {
				result = new APIResponse<>(HttpStatus.BAD_REQUEST,
						ResponseCode.mapParam(ResponseCode.INEXISTED_RECORD, "token <" + vo.getTokenCode() + ">"));
			}
		}

		/////////////////////
		// Update //
		/////////////////////
		if (result.getStatus() == null) {

			if (vo.getTokenName() != null) {
				updateTbl.setTokenName(vo.getTokenName());
			}

			if (vo.getIconUrl() != null) {
				updateTbl.setIconUrl(vo.getIconUrl());
			}

			if (vo.getNodeUrl() != null) {
				updateTbl.setNodeUrl(vo.getNodeUrl());
			}

			if (vo.getRpcUrl() != null) {
				updateTbl.setRpcUrl(vo.getRpcUrl());
			}

			if (vo.getDenom() != null) {
				updateTbl.setDenom(vo.getDenom());
			}

			if (vo.getDecimalExponent() != null && vo.getDecimalExponent() > 0) {
				updateTbl.setDecimalExponent(vo.getDecimalExponent());
			}

			if (vo.getAddressPrefix() != null) {
				updateTbl.setAddressPrefix(vo.getAddressPrefix());
			}

			if (vo.getMintscanPrefix() != null) {
				updateTbl.setMintscanPrefix(vo.getMintscanPrefix());
			}

			if (vo.getDeligateUrl() != null) {
				updateTbl.setDeligateUrl(vo.getDeligateUrl());
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
		PageResultVO<CryptoTokenConfigVO> data = cryptoTokenConfigTransformer.convertToPageReturn(page);

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
			CryptoTokenConfigVO vo = cryptoTokenConfigTransformer.convertToVO(CryptoTokenConfigTbl.get());
			result.setData(vo);
		} else {
			result = new APIResponse<>(HttpStatus.BAD_REQUEST,
					ResponseCode.mapParam(ResponseCode.INEXISTED_RECORD, "token"));
		}

		// Return
		return result;
	}
}

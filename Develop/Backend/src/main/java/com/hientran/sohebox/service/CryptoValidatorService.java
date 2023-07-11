package com.hientran.sohebox.service;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.hc.core5.net.URIBuilder;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hientran.sohebox.cache.ConfigCache;
import com.hientran.sohebox.constants.CosmosConstants;
import com.hientran.sohebox.constants.DataExternalConstants;
import com.hientran.sohebox.dto.PageResultVO;
import com.hientran.sohebox.dto.response.APIResponse;
import com.hientran.sohebox.dto.response.ResponseCode;
import com.hientran.sohebox.entity.CryptoPortfolioTbl;
import com.hientran.sohebox.entity.CryptoValidatorTbl;
import com.hientran.sohebox.repository.CryptoValidatorRepository;
import com.hientran.sohebox.sco.CryptoValidatorSCO;
import com.hientran.sohebox.sco.SearchTextVO;
import com.hientran.sohebox.specification.CryptoValidatorSpecs.CryptoValidatorTblEnum;
import com.hientran.sohebox.webservice.CosmosWebService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional(readOnly = true)
@Slf4j
@RequiredArgsConstructor
public class CryptoValidatorService extends BaseService {

	private final CryptoValidatorRepository cryptoValidatorRepository;
	private final ConfigCache configCache;
	private final CosmosWebService cosmosWebService;

	DecimalFormat df = new DecimalFormat("#.###");

	/**
	 *
	 * Create
	 *
	 * @param rq
	 * @return
	 * @throws IOException
	 */
	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public APIResponse<Long> create(CryptoValidatorTbl rq) {
		// Declare result
		APIResponse<Long> result = new APIResponse<>();

		// Validate input
		if (result.getStatus() == null) {
			List<String> errors = new ArrayList<>();

			if (rq.getValidatorAddress() == null) {
				errors.add(ResponseCode.mapParam(ResponseCode.FILED_EMPTY,
						CryptoValidatorTblEnum.validatorAddress.name()));
			}

			if (StringUtils.isBlank(rq.getValidatorName())) {
				errors.add(
						ResponseCode.mapParam(ResponseCode.FILED_EMPTY, CryptoValidatorTblEnum.validatorName.name()));
			}

			// Record error
			if (CollectionUtils.isNotEmpty(errors)) {
				result = new APIResponse<>(HttpStatus.BAD_REQUEST, errors);
			}
		}

		// Check existence
		if (result.getStatus() == null) {
			if (recordIsExisted(rq)) {
				result = new APIResponse<>(HttpStatus.BAD_REQUEST, ResponseCode.mapParam(ResponseCode.EXISTED_RECORD,
						" validator address " + rq.getValidatorAddress()));
			}
		}

		/////////////////////
		// Record new //
		/////////////////////
		if (result.getStatus() == null) {
			// Transform
			CryptoValidatorTbl tbl = rq;
			tbl.setSyncDate(new Date());

			// Create
			tbl = cryptoValidatorRepository.save(tbl);

			// Set id return
			result.setData(tbl.getId());
		}

		// Return
		return result;
	}

	/**
	 *
	 * Check if record is existed
	 *
	 * @return
	 */
	private boolean recordIsExisted(CryptoValidatorTbl rq) {
		// Declare result
		boolean result = false;

		// Prepare search
		SearchTextVO validatorAddressSearch = new SearchTextVO();
		validatorAddressSearch.setEq(rq.getValidatorAddress());

		CryptoValidatorSCO sco = new CryptoValidatorSCO();
		sco.setValidatorAddress(validatorAddressSearch);

		// Get data
		List<CryptoValidatorTbl> listSearch = cryptoValidatorRepository.findAll(sco).getContent();
		if (CollectionUtils.isNotEmpty(listSearch)) {
			result = true;
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
	public APIResponse<Long> update(CryptoValidatorTbl rq) {
		// Declare result
		APIResponse<Long> result = new APIResponse<>();

		// Validate input
		if (result.getStatus() == null) {
			List<String> errors = new ArrayList<>();

			if (rq.getValidatorAddress() == null) {
				errors.add(ResponseCode.mapParam(ResponseCode.FILED_EMPTY,
						CryptoValidatorTblEnum.validatorAddress.name()));
			}

			if (StringUtils.isBlank(rq.getValidatorName())) {
				errors.add(
						ResponseCode.mapParam(ResponseCode.FILED_EMPTY, CryptoValidatorTblEnum.validatorName.name()));
			}

			// Record error
			if (CollectionUtils.isNotEmpty(errors)) {
				result = new APIResponse<>(HttpStatus.BAD_REQUEST, errors);
			}
		}

		// Get updated item
		CryptoValidatorTbl updateTbl = cryptoValidatorRepository.findById(rq.getId()).get();
		if (updateTbl == null) {
			result = new APIResponse<>(HttpStatus.BAD_REQUEST, ResponseCode.mapParam(ResponseCode.INEXISTED_RECORD,
					"Validator wallet " + rq.getValidatorAddress()));
		}

		// Update
		if (result.getStatus() == null) {
			if (!StringUtils.equals(rq.getValidatorName(), updateTbl.getValidatorName())) {
				updateTbl.setValidatorName(rq.getValidatorName());
			}

			if (!StringUtils.equals(rq.getValidatorWebsite(), updateTbl.getValidatorWebsite())) {
				updateTbl.setValidatorWebsite(rq.getValidatorWebsite());
			}

			if (rq.getCommissionRate() != null) {
				updateTbl.setCommissionRate(rq.getCommissionRate());
			}

			if (rq.getTotalDeligated() != null) {
				updateTbl.setTotalDeligated(rq.getTotalDeligated());
			}

			updateTbl.setSyncDate(new Date());

			cryptoValidatorRepository.save(updateTbl);
		}

		// Return
		return result;
	}

	/**
	 * Search
	 *
	 * @param sco
	 * @return
	 * @throws Exception
	 */
	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public APIResponse<Object> search(CryptoValidatorSCO sco) {
		// Declare result
		APIResponse<Object> result = new APIResponse<>();

		// Get data
		Page<CryptoValidatorTbl> page = cryptoValidatorRepository.findAll(sco);

		// Transformer
		PageResultVO<CryptoValidatorTbl> data = new PageResultVO<>();
		if (!CollectionUtils.isEmpty(page.getContent())) {
			data.setElements(page.getContent());
			setPageHeader(page, data);
		}

		// Set data return
		result.setData(data);

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
		Optional<CryptoValidatorTbl> CryptoValidatorTbl = cryptoValidatorRepository.findById(id);
		if (CryptoValidatorTbl.isPresent()) {
			result.setData(CryptoValidatorTbl.get());
		} else {
			result = new APIResponse<>(HttpStatus.BAD_REQUEST,
					ResponseCode.mapParam(ResponseCode.INEXISTED_RECORD, "token"));
		}

		// Return
		return result;
	}

	public CryptoValidatorTbl getValidator(String validatorAddress, CryptoPortfolioTbl cryptoPortfolioTbl) {
		// Declare result
		CryptoValidatorTbl returnValidator = null;
		boolean isNewValidator = false;

		// Get current validator info
		if (cryptoPortfolioTbl.getValidator() != null
				&& StringUtils.equals(validatorAddress, cryptoPortfolioTbl.getValidator().getValidatorAddress())) {
			returnValidator = cryptoPortfolioTbl.getValidator();
		} else {
			CryptoValidatorTbl tbl = cryptoValidatorRepository.findByValidatorAddress(validatorAddress);
			if (tbl != null) {
				returnValidator = tbl;
			} else {
				isNewValidator = true;
			}
		}

		// Check if need sync
		boolean isSyncValidator = false;
		if (returnValidator != null) {
			int lateTimeSecond = Integer.parseInt(
					configCache.getValueByKey(DataExternalConstants.CRYPTO_PORTFOLIO_SYNC_VALIDATOR_LATE_TIME_SECOND));
			long diffInSecond = (new Date().getTime() - returnValidator.getUpdatedDate().getTime()) / 1000;
			if (diffInSecond > lateTimeSecond) {
				isSyncValidator = true;
			}
		} else {
			isSyncValidator = true;
		}

		// Process if have sync flag
		if (isSyncValidator) {
			// Get new infos
//            URIBuilder builder = new URIBuilder(cryptoPortfolioVO.getToken().getNodeUrl()
//                    + CosmosConstants.COSMOS_STAKING_VALIDATORS + "/" + validatorAddress);
//            String responseString = cosmosWebService.get(builder);
//            JSONObject jsonObject = new JSONObject(responseString);
//
//            String validatorName = jsonObject.getJSONObject("result").getJSONObject("description").get("moniker")
//                    .toString();
//            String validatorWebsite = jsonObject.getJSONObject("result").getJSONObject("description").get("website")
//                    .toString();
//            Double totalDeligated = jsonObject.getJSONObject("result").getDouble("tokens") / cryptoPortfolioVO.getToken().getDecimalExponent();
//            Double commissionRate = jsonObject.getJSONObject("result").getJSONObject("commission")
//                    .getJSONObject("commission_rates").getDouble("rate");

			// Sync new value
			try {
				URIBuilder builder = new URIBuilder(cryptoPortfolioTbl.getToken().getNodeUrl()
						+ CosmosConstants.COSMOS_STAKING_V1BETA1_VALIDATORS + "/" + validatorAddress);
				String responseString = cosmosWebService.get(builder);
				JSONObject jsonObject = new JSONObject(responseString);

				String validatorName = jsonObject.getJSONObject("validator").getJSONObject("description").get("moniker")
						.toString();
				String validatorWebsite = jsonObject.getJSONObject("validator").getJSONObject("description")
						.get("website").toString();
				Double totalDeligated = jsonObject.getJSONObject("validator").getDouble("tokens")
						/ cryptoPortfolioTbl.getToken().getDecimalExponent();
				Double commissionRate = Double.parseDouble(df.format(jsonObject.getJSONObject("validator")
						.getJSONObject("commission").getJSONObject("commission_rates").getDouble("rate")));

				// Record DB
				if (isNewValidator) {
					returnValidator = new CryptoValidatorTbl();
					returnValidator.setValidatorAddress(validatorAddress);
					returnValidator.setValidatorName(validatorName);
					returnValidator.setValidatorWebsite(validatorWebsite);
					returnValidator.setTotalDeligated(totalDeligated);
					returnValidator.setCommissionRate(commissionRate);

					Long idResult = create(returnValidator).getData();
					returnValidator = cryptoValidatorRepository.findById(idResult).get();
				} else {
					returnValidator.setValidatorName(validatorName);
					returnValidator.setValidatorWebsite(validatorWebsite);
					returnValidator.setTotalDeligated(totalDeligated);
					returnValidator.setCommissionRate(commissionRate);
					update(returnValidator);
				}
			} catch (Exception e) {
				returnValidator = cryptoPortfolioTbl.getValidator();
				log.error("getValidator syncData onchain Error Message: {}", e.getMessage());
				e.printStackTrace();
			}
		}

		// Return
		return returnValidator;
	}
}

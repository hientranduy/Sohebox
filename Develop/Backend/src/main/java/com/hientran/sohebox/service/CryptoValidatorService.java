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
import com.hientran.sohebox.dto.response.APIResponse;
import com.hientran.sohebox.dto.response.ResponseCode;
import com.hientran.sohebox.entity.CryptoValidatorTbl;
import com.hientran.sohebox.repository.CryptoValidatorRepository;
import com.hientran.sohebox.sco.CryptoValidatorSCO;
import com.hientran.sohebox.sco.SearchTextVO;
import com.hientran.sohebox.specification.CryptoValidatorSpecs.CryptoValidatorTblEnum;
import com.hientran.sohebox.transformer.CryptoValidatorTransformer;
import com.hientran.sohebox.vo.CryptoPortfolioVO;
import com.hientran.sohebox.vo.CryptoValidatorVO;
import com.hientran.sohebox.vo.PageResultVO;
import com.hientran.sohebox.webservice.CosmosWebService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional(readOnly = true)
@Slf4j
@RequiredArgsConstructor
public class CryptoValidatorService extends BaseService {

	private final CryptoValidatorRepository cryptoValidatorRepository;
	private final CryptoValidatorTransformer cryptoValidatorTransformer;
	private final ConfigCache configCache;
	private final CosmosWebService cosmosWebService;

	DecimalFormat df = new DecimalFormat("#.###");

	/**
	 * 
	 * Create
	 * 
	 * @param vo
	 * @return
	 * @throws IOException
	 */
	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public APIResponse<Long> create(CryptoValidatorVO vo) {
		// Declare result
		APIResponse<Long> result = new APIResponse<Long>();

		// Validate input
		if (result.getStatus() == null) {
			List<String> errors = new ArrayList<>();

			if (vo.getValidatorAddress() == null) {
				errors.add(ResponseCode.mapParam(ResponseCode.FILED_EMPTY,
						CryptoValidatorTblEnum.validatorAddress.name()));
			}

			if (StringUtils.isBlank(vo.getValidatorName())) {
				errors.add(
						ResponseCode.mapParam(ResponseCode.FILED_EMPTY, CryptoValidatorTblEnum.validatorName.name()));
			}

			// Record error
			if (CollectionUtils.isNotEmpty(errors)) {
				result = new APIResponse<Long>(HttpStatus.BAD_REQUEST, errors);
			}
		}

		// Check existence
		if (result.getStatus() == null) {
			if (recordIsExisted(vo)) {
				result = new APIResponse<Long>(HttpStatus.BAD_REQUEST, ResponseCode
						.mapParam(ResponseCode.EXISTED_RECORD, " validator address " + vo.getValidatorAddress()));
			}
		}

		/////////////////////
		// Record new //
		/////////////////////
		if (result.getStatus() == null) {
			// Transform
			CryptoValidatorTbl tbl = cryptoValidatorTransformer.convertToTbl(vo);
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
	private boolean recordIsExisted(CryptoValidatorVO vo) {
		// Declare result
		Boolean result = false;

		// Prepare search
		SearchTextVO validatorAddressSearch = new SearchTextVO();
		validatorAddressSearch.setEq(vo.getValidatorAddress());

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
	 * @param vo
	 * @return
	 */
	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public APIResponse<Long> update(CryptoValidatorVO vo) {
		// Declare result
		APIResponse<Long> result = new APIResponse<Long>();

		// Validate input
		if (result.getStatus() == null) {
			List<String> errors = new ArrayList<>();

			if (vo.getValidatorAddress() == null) {
				errors.add(ResponseCode.mapParam(ResponseCode.FILED_EMPTY,
						CryptoValidatorTblEnum.validatorAddress.name()));
			}

			if (StringUtils.isBlank(vo.getValidatorName())) {
				errors.add(
						ResponseCode.mapParam(ResponseCode.FILED_EMPTY, CryptoValidatorTblEnum.validatorName.name()));
			}

			// Record error
			if (CollectionUtils.isNotEmpty(errors)) {
				result = new APIResponse<Long>(HttpStatus.BAD_REQUEST, errors);
			}
		}

		// Get updated item
		CryptoValidatorTbl updateTbl = cryptoValidatorRepository.findById(vo.getId()).get();
		if (updateTbl == null) {
			result = new APIResponse<Long>(HttpStatus.BAD_REQUEST, ResponseCode.mapParam(ResponseCode.INEXISTED_RECORD,
					"Validator wallet " + vo.getValidatorAddress()));
		}

		// Update
		if (result.getStatus() == null) {
			if (!StringUtils.equals(vo.getValidatorName(), updateTbl.getValidatorName())) {
				updateTbl.setValidatorName(vo.getValidatorName());
			}

			if (!StringUtils.equals(vo.getValidatorWebsite(), updateTbl.getValidatorWebsite())) {
				updateTbl.setValidatorWebsite(vo.getValidatorWebsite());
			}

			if (vo.getCommissionRate() != null) {
				updateTbl.setCommissionRate(vo.getCommissionRate());
			}

			if (vo.getTotalDeligated() != null) {
				updateTbl.setTotalDeligated(vo.getTotalDeligated());
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
		APIResponse<Object> result = new APIResponse<Object>();

		// Get data
		Page<CryptoValidatorTbl> page = cryptoValidatorRepository.findAll(sco);

		// Transformer
		PageResultVO<CryptoValidatorVO> data = cryptoValidatorTransformer.convertToPageReturn(page);

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
		APIResponse<Object> result = new APIResponse<Object>();

		// Check existence
		Optional<CryptoValidatorTbl> CryptoValidatorTbl = cryptoValidatorRepository.findById(id);
		if (CryptoValidatorTbl.isPresent()) {
			CryptoValidatorVO vo = cryptoValidatorTransformer.convertToVO(CryptoValidatorTbl.get());
			result.setData(vo);
		} else {
			result = new APIResponse<Object>(HttpStatus.BAD_REQUEST,
					ResponseCode.mapParam(ResponseCode.INEXISTED_RECORD, "token"));
		}

		// Return
		return result;
	}

	public CryptoValidatorVO getValidator(String validatorAddress, CryptoPortfolioVO cryptoPortfolioVO) {
		// Declare result
		CryptoValidatorVO returnValidator = null;
		Boolean isNewValidator = false;

		// Get current validator info
		if (cryptoPortfolioVO.getValidator() != null
				&& StringUtils.equals(validatorAddress, cryptoPortfolioVO.getValidator().getValidatorAddress())) {
			returnValidator = cryptoPortfolioVO.getValidator();
		} else {
			CryptoValidatorTbl tbl = cryptoValidatorRepository.findByValidatorAddress(validatorAddress);
			if (tbl != null) {
				returnValidator = cryptoValidatorTransformer.convertToVO(tbl);
			} else {
				isNewValidator = true;
			}
		}

		// Check if need sync
		Boolean isSyncValidator = false;
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
				URIBuilder builder = new URIBuilder(cryptoPortfolioVO.getToken().getNodeUrl()
						+ CosmosConstants.COSMOS_STAKING_V1BETA1_VALIDATORS + "/" + validatorAddress);
				String responseString = cosmosWebService.get(builder);
				JSONObject jsonObject = new JSONObject(responseString);

				String validatorName = jsonObject.getJSONObject("validator").getJSONObject("description").get("moniker")
						.toString();
				String validatorWebsite = jsonObject.getJSONObject("validator").getJSONObject("description")
						.get("website").toString();
				Double totalDeligated = jsonObject.getJSONObject("validator").getDouble("tokens")
						/ cryptoPortfolioVO.getToken().getDecimalExponent();
				Double commissionRate = Double.parseDouble(df.format(jsonObject.getJSONObject("validator")
						.getJSONObject("commission").getJSONObject("commission_rates").getDouble("rate")));

				// Record DB
				if (isNewValidator) {
					returnValidator = new CryptoValidatorVO();
					returnValidator.setValidatorAddress(validatorAddress);
					returnValidator.setValidatorName(validatorName);
					returnValidator.setValidatorWebsite(validatorWebsite);
					returnValidator.setTotalDeligated(totalDeligated);
					returnValidator.setCommissionRate(commissionRate);

					Long idResult = create(returnValidator).getData();
					returnValidator = cryptoValidatorTransformer
							.convertToVO(cryptoValidatorRepository.findById(idResult).get());
				} else {
					returnValidator.setValidatorName(validatorName);
					returnValidator.setValidatorWebsite(validatorWebsite);
					returnValidator.setTotalDeligated(totalDeligated);
					returnValidator.setCommissionRate(commissionRate);
					update(returnValidator);
				}
			} catch (Exception e) {
				returnValidator = cryptoPortfolioVO.getValidator();
				log.error("getValidator syncData onchain Error Message: {}", e.getMessage());
				e.printStackTrace();
			}
		}

		// Return
		return returnValidator;
	}
}

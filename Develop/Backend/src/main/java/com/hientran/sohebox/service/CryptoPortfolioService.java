package com.hientran.sohebox.service;

import java.io.IOException;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.hc.core5.net.URIBuilder;
import org.springframework.boot.configurationprocessor.json.JSONArray;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.type.TypeReference;
import com.hientran.sohebox.authentication.UserDetailsServiceImpl;
import com.hientran.sohebox.cache.ConfigCache;
import com.hientran.sohebox.constants.CosmosConstants;
import com.hientran.sohebox.constants.DBConstants;
import com.hientran.sohebox.constants.DataExternalConstants;
import com.hientran.sohebox.dto.CryptoPortfolioValidatorDelegationVO;
import com.hientran.sohebox.dto.PageResultVO;
import com.hientran.sohebox.dto.response.APIResponse;
import com.hientran.sohebox.dto.response.ResponseCode;
import com.hientran.sohebox.entity.CryptoPortfolioTbl;
import com.hientran.sohebox.entity.UserTbl;
import com.hientran.sohebox.repository.CryptoPortfolioRepository;
import com.hientran.sohebox.sco.CryptoPortfolioSCO;
import com.hientran.sohebox.sco.SearchNumberVO;
import com.hientran.sohebox.sco.SearchTextVO;
import com.hientran.sohebox.specification.CryptoPortfolioSpecs.CryptoPortfolioTblEnum;
import com.hientran.sohebox.utils.ObjectMapperUtil;
import com.hientran.sohebox.webservice.CosmosWebService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional(readOnly = true)
@Slf4j
@RequiredArgsConstructor
public class CryptoPortfolioService extends BaseService {
	private final CryptoPortfolioRepository cryptoPortfolioRepository;
	private final UserService userService;
	private final UserDetailsServiceImpl userDetailsServiceImpl;
	private final CosmosWebService cosmosWebService;
	private final ObjectMapperUtil objectMapperUtil;
	private final ConfigCache configCache;
	private final CryptoValidatorService cryptoValidatorService;

	/**
	 *
	 * Create
	 *
	 * @param rq
	 * @return
	 * @throws IOException
	 */
	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public APIResponse<Long> create(CryptoPortfolioTbl rq) {
		// Declare result
		APIResponse<Long> result = new APIResponse<>();

		// Validate input
		if (result.getStatus() == null) {
			List<String> errors = new ArrayList<>();

			if (rq.getToken() == null) {
				errors.add(ResponseCode.mapParam(ResponseCode.FILED_EMPTY, CryptoPortfolioTblEnum.token.name()));
			}

			if (StringUtils.isBlank(rq.getWallet())) {
				errors.add(ResponseCode.mapParam(ResponseCode.FILED_EMPTY, CryptoPortfolioTblEnum.wallet.name()));
			}

			// Record error
			if (CollectionUtils.isNotEmpty(errors)) {
				result = new APIResponse<>(HttpStatus.BAD_REQUEST, errors);
			}
		}

		// Get logged user
		UserTbl loggedUser = userDetailsServiceImpl.getCurrentLoginUser();

		// Check existence
		if (result.getStatus() == null) {
			if (recordIsExisted(loggedUser, rq)) {
				result = new APIResponse<>(HttpStatus.BAD_REQUEST, ResponseCode.mapParam(ResponseCode.EXISTED_RECORD,
						" wallet " + rq.getWallet() + "<token " + rq.getToken().getTokenCode() + ">"));
			}
		}

		/////////////////////
		// Record new //
		/////////////////////
		if (result.getStatus() == null) {
			// Transform
			CryptoPortfolioTbl tbl = rq;
			tbl.setUser(loggedUser);

			// Create
			tbl = cryptoPortfolioRepository.save(tbl);

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
	private boolean recordIsExisted(UserTbl user, CryptoPortfolioTbl rq) {
		// Declare result
		boolean result = false;

		// Prepare search
		SearchNumberVO userIdSearch = new SearchNumberVO();
		userIdSearch.setEq(user.getId().doubleValue());

		SearchNumberVO tokenIdSearch = new SearchNumberVO();
		tokenIdSearch.setEq(rq.getToken().getId().doubleValue());

		SearchTextVO walletSearch = new SearchTextVO();
		walletSearch.setEq(rq.getWallet());

		CryptoPortfolioSCO sco = new CryptoPortfolioSCO();
		sco.setUser(userIdSearch);
		sco.setToken(tokenIdSearch);
		sco.setWallet(walletSearch);

		// Get data
		List<CryptoPortfolioTbl> listSearch = cryptoPortfolioRepository.findAll(sco).getContent();
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
	public APIResponse<Long> update(CryptoPortfolioTbl rq) {
		// Declare result
		APIResponse<Long> result = new APIResponse<>();

		// Validate input
		if (result.getStatus() == null) {
			List<String> errors = new ArrayList<>();

			if (rq.getToken() == null) {
				errors.add(ResponseCode.mapParam(ResponseCode.FILED_EMPTY, CryptoPortfolioTblEnum.token.name()));
			}

			if (StringUtils.isBlank(rq.getWallet())) {
				errors.add(ResponseCode.mapParam(ResponseCode.FILED_EMPTY, CryptoPortfolioTblEnum.wallet.name()));
			}

			// Record error
			if (CollectionUtils.isNotEmpty(errors)) {
				result = new APIResponse<>(HttpStatus.BAD_REQUEST, errors);
			}
		}

		// Get logged user
		UserTbl loggedUser = userDetailsServiceImpl.getCurrentLoginUser();

		// Get updated account
		CryptoPortfolioTbl updateTbl = getTokenPortfolioByUser(loggedUser, rq.getId());
		if (updateTbl == null) {
			result = new APIResponse<>(HttpStatus.BAD_REQUEST, ResponseCode.mapParam(ResponseCode.INEXISTED_RECORD,
					"portfolio token " + rq.getToken().getTokenCode()));
		}

		// Update
		if (result.getStatus() == null) {
			if (!StringUtils.equals(rq.getWallet(), updateTbl.getWallet())) {
				updateTbl.setWallet(rq.getWallet());
			}
			if (!StringUtils.equals(rq.getStarname(), updateTbl.getStarname())) {
				updateTbl.setStarname(rq.getStarname());
			}

			if (rq.getAmtAvailable() != null) {
				updateTbl.setAmtAvailable(rq.getAmtAvailable());
			}

			if (rq.getAmtTotalDelegated() != null) {
				updateTbl.setAmtTotalDelegated(rq.getAmtTotalDelegated());
			}

			if (rq.getAmtTotalReward() != null) {
				updateTbl.setAmtTotalReward(rq.getAmtTotalReward());
			}

			if (rq.getAmtTotalUnbonding() != null) {
				updateTbl.setAmtTotalUnbonding(rq.getAmtTotalUnbonding());
			}

			if (rq.getValidator() != null) {
				updateTbl.setValidator(rq.getValidator());
			}

			updateTbl.setSyncDate(new Date());

			cryptoPortfolioRepository.save(updateTbl);
		}

		// Return
		return result;
	}

	/**
	 *
	 * Get account
	 *
	 * @param userOwnerId
	 * @param accountTypeId
	 * @param accountName
	 * @return
	 */
	private CryptoPortfolioTbl getTokenPortfolioByUser(UserTbl loggedUser, Long tokenId) {
		// Declare result
		CryptoPortfolioTbl result = null;

		// Prepare search
		SearchNumberVO userSearch = new SearchNumberVO();
		userSearch.setEq(loggedUser.getId().doubleValue());
		SearchNumberVO id = new SearchNumberVO();
		id.setEq(tokenId.doubleValue());

		CryptoPortfolioSCO sco = new CryptoPortfolioSCO();
		sco.setId(id);
		if (StringUtils.equals(loggedUser.getRole().getRoleName(), DBConstants.USER_ROLE_USER)) {
			sco.setUser(userSearch);
		}

		// Get data
		List<CryptoPortfolioTbl> listAccount = cryptoPortfolioRepository.findAll(sco).getContent();
		if (CollectionUtils.isNotEmpty(listAccount)) {
			result = listAccount.get(0);
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
	public APIResponse<Object> search(CryptoPortfolioSCO sco) {
		// Declare result
		APIResponse<Object> result = new APIResponse<>();

		// Get logged user
		UserTbl loggedUser = userDetailsServiceImpl.getCurrentLoginUser();
		SearchNumberVO userIdSearch = new SearchNumberVO();
		userIdSearch.setEq(loggedUser.getId().doubleValue());
		sco.setUser(userIdSearch);

		// Get data
		Page<CryptoPortfolioTbl> page = cryptoPortfolioRepository.findAll(sco);

		// Transformer
		PageResultVO<CryptoPortfolioTbl> data = new PageResultVO<>();
		if (!CollectionUtils.isEmpty(page.getContent())) {
			data.setElements(page.getContent());
			setPageHeader(page, data);
		}

		// Get data on chain
		if (CollectionUtils.isNotEmpty(data.getElements())) {
			int lateTimeSecond = Integer.parseInt(
					configCache.getValueByKey(DataExternalConstants.CRYPTO_PORTFOLIO_SYNC_ONCHAIN_LATE_TIME_SECOND));

			for (CryptoPortfolioTbl item : data.getElements()) {
				// Get if out update
				long diffInSecond = (new Date().getTime() - item.getUpdatedDate().getTime()) / 1000;
				if (diffInSecond > lateTimeSecond) {
					try {
						if (StringUtils.isNotEmpty(item.getToken().getNodeUrl())) {
							this.setDataOnChain(item);

							// Update DB
							this.update(item);
						}
					} catch (Exception e) {
						e.printStackTrace();
						return new APIResponse<>(HttpStatus.BAD_REQUEST,
								ResponseCode.mapParam(ResponseCode.ERROR_EXCEPTION, e.getMessage()));
					}
				}
			}
		}

		// Set data return
		result.setData(data);

		// Return
		return result;
	}

	private void setDataOnChain(CryptoPortfolioTbl tbl) {
		DecimalFormat df = new DecimalFormat("#.###");
		df.setRoundingMode(RoundingMode.CEILING);
		URIBuilder builder;
		JSONObject jsonObject;

		// Get available
		log.info("setDataOnChain - get available- wallet {}", tbl.getWallet());
		try {
			builder = new URIBuilder(
					tbl.getToken().getNodeUrl() + CosmosConstants.COSMOS_BANK_V1BETA1_BALANCES + "/" + tbl.getWallet());
			log.info("setDataOnChain - get available- builder {}", builder.toString());
			jsonObject = new JSONObject(cosmosWebService.get(builder));

			if (jsonObject.getJSONArray("balances").length() > 0) {
				JSONArray listObject = jsonObject.getJSONArray("balances");

				for (int i = 0; i < listObject.length(); i++) {
					String demon = listObject.getJSONObject(i).getString("denom");
					if (!demon.contains("ibc")) {
						tbl.setAmtAvailable(Double.parseDouble(df.format(listObject.getJSONObject(i).getDouble("amount")
								/ tbl.getToken().getDecimalExponent())));
						break;
					}
				}
			} else {
				tbl.setAmtAvailable(Double.valueOf(0));
			}
		} catch (Exception e) {
			log.error("setDataOnChain AmtAvailable Error Message: {}", e.getMessage());
			e.printStackTrace();
		}

		// Get reward
		try {
			builder = new URIBuilder(
					tbl.getToken().getNodeUrl() + CosmosConstants.COSMOS_DISTRIBUTION_V1BETA1_DELEGATORS + "/"
							+ tbl.getWallet() + CosmosConstants.COSMOS_REWARDS);
			log.info("setDataOnChain - get reward- builder {}", builder.toString());
			jsonObject = new JSONObject(cosmosWebService.get(builder));

			if (jsonObject.getJSONArray("total").length() > 0) {
				tbl.setAmtTotalReward(Double
						.parseDouble(df.format(jsonObject.getJSONArray("total").getJSONObject(0).getDouble("amount")
								/ tbl.getToken().getDecimalExponent())));
			} else {
				tbl.setAmtTotalReward(Double.valueOf(0));
			}
		} catch (Exception e) {
			log.error("setDataOnChain AmtTotalReward Error Message: {}", e.getMessage());
			e.printStackTrace();
		}

		// Get delegated
		try {
			builder = new URIBuilder(tbl.getToken().getNodeUrl() + CosmosConstants.COSMOS_STAKING_V1BETA1_DELEGATION
					+ "/" + tbl.getWallet());
			log.info("setDataOnChain - get delegated- builder {}", builder.toString());
			jsonObject = new JSONObject(cosmosWebService.get(builder));

			List<CryptoPortfolioValidatorDelegationVO> validatorDelegation = objectMapperUtil.readValue(
					jsonObject.getJSONArray("delegation_responses").toString(),
					new TypeReference<List<CryptoPortfolioValidatorDelegationVO>>() {
					});

			Double amtTotalDelegated = Double.valueOf(0);
			String validatorAddress = null;
			if (CollectionUtils.isNotEmpty(validatorDelegation)) {
				Double maxAmount = Double.valueOf(0);
				for (CryptoPortfolioValidatorDelegationVO item : validatorDelegation) {
					Double amount = item.getBalance().getAmount();
					if (amount > 0) {
						amtTotalDelegated = amtTotalDelegated
								+ Double.parseDouble(df.format(amount / tbl.getToken().getDecimalExponent()));
						if (amount > maxAmount) {
							maxAmount = amount;
							validatorAddress = item.getDelegation().getValidator_address();
						}
					}
				}
			}
			tbl.setAmtTotalDelegated(amtTotalDelegated);

			// Get validator info
			if (validatorAddress != null) {
				tbl.setValidator(cryptoValidatorService.getValidator(validatorAddress, tbl));
			}
		} catch (Exception e) {
			log.error("setDataOnChain AmtTotalDelegated Error Message: {}", e.getMessage());
			e.printStackTrace();
		}
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
		Optional<CryptoPortfolioTbl> CryptoPortfolioTbl = cryptoPortfolioRepository.findById(id);
		if (CryptoPortfolioTbl.isPresent()) {
			result.setData(CryptoPortfolioTbl.get());
		} else {
			result = new APIResponse<>(HttpStatus.BAD_REQUEST,
					ResponseCode.mapParam(ResponseCode.INEXISTED_RECORD, "token"));
		}

		// Return
		return result;
	}

	/**
	 * Delete by id
	 *
	 * Only role creator
	 *
	 * @param User
	 * @return
	 */
	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public APIResponse<Object> deleteById(Long id) {
		// Declare result
		APIResponse<Object> result = new APIResponse<>();

		// Check existed
		Optional<CryptoPortfolioTbl> deleteItemTbl = cryptoPortfolioRepository.findById(id);
		if (!deleteItemTbl.isPresent()) {
			result = new APIResponse<>(HttpStatus.BAD_REQUEST,
					ResponseCode.mapParam(ResponseCode.UNAUTHORIZED_DATA, null));
		}

		// Check logged user have permission to delete
		if (result.getStatus() == null && !userService.isDataOwner(deleteItemTbl.get().getUser().getUsername())) {
			result = new APIResponse<>(HttpStatus.BAD_REQUEST,
					ResponseCode.mapParam(ResponseCode.UNAUTHORIZED_DATA, null));
		}

		// Process delete
		if (result.getStatus() == null) {
			cryptoPortfolioRepository.delete(deleteItemTbl.get());
		}

		// Return
		return result;
	}
}
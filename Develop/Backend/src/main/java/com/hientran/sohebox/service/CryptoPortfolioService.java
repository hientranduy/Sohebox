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
import org.apache.http.client.utils.URIBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.type.TypeReference;
import com.hazelcast.org.json.JSONObject;
import com.hientran.sohebox.cache.ConfigCache;
import com.hientran.sohebox.constants.CosmosConstants;
import com.hientran.sohebox.constants.DBConstants;
import com.hientran.sohebox.constants.DataExternalConstants;
import com.hientran.sohebox.constants.MessageConstants;
import com.hientran.sohebox.constants.enums.CryptoPortfolioTblEnum;
import com.hientran.sohebox.entity.CryptoPortfolioTbl;
import com.hientran.sohebox.entity.UserTbl;
import com.hientran.sohebox.exception.APIResponse;
import com.hientran.sohebox.repository.CryptoPortfolioRepository;
import com.hientran.sohebox.sco.CryptoPortfolioSCO;
import com.hientran.sohebox.sco.SearchNumberVO;
import com.hientran.sohebox.sco.SearchTextVO;
import com.hientran.sohebox.security.UserService;
import com.hientran.sohebox.transformer.CryptoPortfolioTransformer;
import com.hientran.sohebox.transformer.CryptoValidatorTransformer;
import com.hientran.sohebox.utils.ObjectMapperUtil;
import com.hientran.sohebox.vo.CryptoPortfolioVO;
import com.hientran.sohebox.vo.CryptoPortfolioValidatorDelegationVO;
import com.hientran.sohebox.vo.PageResultVO;
import com.hientran.sohebox.webservice.CosmosWebService;

import lombok.extern.slf4j.Slf4j;

/**
 * @author hientran
 */
@Service
@Transactional(readOnly = true)
@Slf4j
public class CryptoPortfolioService extends BaseService {

    private static final long serialVersionUID = 1L;

    @Autowired
    private CryptoPortfolioRepository cryptoPortfolioRepository;

    @Autowired
    private CryptoPortfolioTransformer cryptoPortfolioTransformer;

    @Autowired
    private CryptoValidatorTransformer cryptoValidatorTransformer;

    @Autowired
    private UserService userService;

    @Autowired
    private CosmosWebService cosmosWebService;

    @Autowired
    private ObjectMapperUtil objectMapperUtil;

    @Autowired
    private ConfigCache configCache;

    @Autowired
    private CryptoValidatorService cryptoValidatorService;

    /**
     * 
     * Create
     * 
     * @param vo
     * @return
     * @throws IOException
     */
    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public APIResponse<Long> create(CryptoPortfolioVO vo) {
        // Declare result
        APIResponse<Long> result = new APIResponse<Long>();

        // Validate input
        if (result.getStatus() == null) {
            List<String> errors = new ArrayList<>();

            if (vo.getToken() == null) {
                errors.add(buildMessage(MessageConstants.FILED_EMPTY,
                        new String[] { CryptoPortfolioTblEnum.token.name() }));
            }

            if (StringUtils.isBlank(vo.getWallet())) {
                errors.add(buildMessage(MessageConstants.FILED_EMPTY,
                        new String[] { CryptoPortfolioTblEnum.wallet.name() }));
            }

            // Record error
            if (CollectionUtils.isNotEmpty(errors)) {
                result = new APIResponse<Long>(HttpStatus.BAD_REQUEST, errors);
            }
        }

        // Get logged user
        UserTbl loggedUser = userService.getCurrentLoginUser();

        // Check existence
        if (result.getStatus() == null) {
            if (recordIsExisted(loggedUser, vo)) {
                result = new APIResponse<Long>(HttpStatus.BAD_REQUEST, buildMessage(MessageConstants.EXISTED_RECORD,
                        new String[] { " wallet " + vo.getWallet() + "<token " + vo.getToken().getTokenCode() + ">" }));
            }
        }

        /////////////////////
        // Record new //
        /////////////////////
        if (result.getStatus() == null) {
            // Transform
            CryptoPortfolioTbl tbl = cryptoPortfolioTransformer.convertToTbl(vo);
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
    private boolean recordIsExisted(UserTbl user, CryptoPortfolioVO vo) {
        // Declare result
        Boolean result = false;

        // Prepare search
        SearchNumberVO userIdSearch = new SearchNumberVO();
        userIdSearch.setEq(user.getId().doubleValue());

        SearchNumberVO tokenIdSearch = new SearchNumberVO();
        tokenIdSearch.setEq(vo.getToken().getId().doubleValue());

        SearchTextVO walletSearch = new SearchTextVO();
        walletSearch.setEq(vo.getWallet());

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
     * @param vo
     * @return
     */
    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public APIResponse<Long> update(CryptoPortfolioVO vo) {
        // Declare result
        APIResponse<Long> result = new APIResponse<Long>();

        // Validate input
        if (result.getStatus() == null) {
            List<String> errors = new ArrayList<>();

            if (vo.getToken() == null) {
                errors.add(buildMessage(MessageConstants.FILED_EMPTY,
                        new String[] { CryptoPortfolioTblEnum.token.name() }));
            }

            if (StringUtils.isBlank(vo.getWallet())) {
                errors.add(buildMessage(MessageConstants.FILED_EMPTY,
                        new String[] { CryptoPortfolioTblEnum.wallet.name() }));
            }

            // Record error
            if (CollectionUtils.isNotEmpty(errors)) {
                result = new APIResponse<Long>(HttpStatus.BAD_REQUEST, errors);
            }
        }

        // Get logged user
        UserTbl loggedUser = userService.getCurrentLoginUser();

        // Get updated account
        CryptoPortfolioTbl updateTbl = getTokenPortfolioByUser(loggedUser, vo.getId());
        if (updateTbl == null) {
            result = new APIResponse<Long>(HttpStatus.BAD_REQUEST, buildMessage(MessageConstants.INEXISTED_RECORD,
                    new String[] { "portfolio token " + vo.getToken().getTokenCode() }));
        }

        // Update
        if (result.getStatus() == null) {
            if (!StringUtils.equals(vo.getWallet(), updateTbl.getWallet())) {
                updateTbl.setWallet(vo.getWallet());
            }
            if (!StringUtils.equals(vo.getStarname(), updateTbl.getStarname())) {
                updateTbl.setStarname(vo.getStarname());
            }

            if (vo.getAmtAvailable() != null) {
                updateTbl.setAmtAvailable(vo.getAmtAvailable());
            }

            if (vo.getAmtTotalDelegated() != null) {
                updateTbl.setAmtTotalDelegated(vo.getAmtTotalDelegated());
            }

            if (vo.getAmtTotalReward() != null) {
                updateTbl.setAmtTotalReward(vo.getAmtTotalReward());
            }

            if (vo.getAmtTotalUnbonding() != null) {
                updateTbl.setAmtTotalUnbonding(vo.getAmtTotalUnbonding());
            }

            if (vo.getValidator() != null) {
                updateTbl.setValidator(cryptoValidatorTransformer.convertToTbl(vo.getValidator()));
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
        APIResponse<Object> result = new APIResponse<Object>();

        // Get logged user
        UserTbl loggedUser = userService.getCurrentLoginUser();
        SearchNumberVO userIdSearch = new SearchNumberVO();
        userIdSearch.setEq(loggedUser.getId().doubleValue());
        sco.setUser(userIdSearch);

        // Get data
        Page<CryptoPortfolioTbl> page = cryptoPortfolioRepository.findAll(sco);

        // Transformer
        PageResultVO<CryptoPortfolioVO> data = cryptoPortfolioTransformer.convertToPageReturn(page);

        // Get data on chain
        if (CollectionUtils.isNotEmpty(data.getElements())) {
            int lateTimeSecond = Integer.parseInt(
                    configCache.getValueByKey(DataExternalConstants.CRYPTO_PORTFOLIO_SYNC_ONCHAIN_LATE_TIME_SECOND));

            for (CryptoPortfolioVO item : data.getElements()) {
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
                        return new APIResponse<Object>(HttpStatus.BAD_REQUEST,
                                buildMessage(MessageConstants.ERROR_EXCEPTION, new String[] { e.getMessage() }));
                    }
                }
            }
        }

        // Set data return
        result.setData(data);

        // Return
        return result;
    }

    private void setDataOnChain(CryptoPortfolioVO cryptoPortfolioVO) {
        DecimalFormat df = new DecimalFormat("#.###");
        df.setRoundingMode(RoundingMode.CEILING);
        URIBuilder builder;
        JSONObject jsonObject;

        // Get available
//        builder = new URIBuilder(cryptoPortfolioVO.getToken().getNodeUrl() + CosmosConstants.COSMOS_BANK_BALANCES + "/"
//                + cryptoPortfolioVO.getWallet());
//        jsonObject = new JSONObject(cosmosWebService.get(builder));
//
//        if (jsonObject.getJSONArray("result").length() > 0) {
//            cryptoPortfolioVO.setAmtAvailable(Double.parseDouble(
//                    df.format(jsonObject.getJSONArray("result").getJSONObject(0).getDouble("amount") / cryptoPortfolioVO.getToken().getDecimalExponent())));
//        } else {
//            cryptoPortfolioVO.setAmtAvailable(Double.valueOf(0));
//        }

        try {
            builder = new URIBuilder(cryptoPortfolioVO.getToken().getNodeUrl()
                    + CosmosConstants.COSMOS_BANK_V1BETA1_BALANCES + "/" + cryptoPortfolioVO.getWallet());
            jsonObject = new JSONObject(cosmosWebService.get(builder));

            if (jsonObject.getJSONArray("balances").length() > 0) {
                cryptoPortfolioVO.setAmtAvailable(Double
                        .parseDouble(df.format(jsonObject.getJSONArray("balances").getJSONObject(0).getDouble("amount")
                                / cryptoPortfolioVO.getToken().getDecimalExponent())));
            } else {
                cryptoPortfolioVO.setAmtAvailable(Double.valueOf(0));
            }
        } catch (Exception e) {
            log.error("setDataOnChain AmtAvailable Error Message: {}", e.getMessage());
            e.printStackTrace();
        }

        // Get reward
//        builder = new URIBuilder(
//                cryptoPortfolioVO.getToken().getNodeUrl() + CosmosConstants.COSMOS_DISTRIBUTION_DELEGATORS + "/"
//                        + cryptoPortfolioVO.getWallet() + CosmosConstants.COSMOS_REWARDS);
//        jsonObject = new JSONObject(cosmosWebService.get(builder));
//
//        if (jsonObject.getJSONObject("result").getJSONArray("total").length() > 0) {
//            cryptoPortfolioVO.setAmtTotalReward(Double.parseDouble(df.format(
//                    jsonObject.getJSONObject("result").getJSONArray("total").getJSONObject(0).getDouble("amount")
//                            / cryptoPortfolioVO.getToken().getDecimalExponent())));
//        } else {
//            cryptoPortfolioVO.setAmtTotalReward(Double.valueOf(0));
//        }

        try {
            builder = new URIBuilder(
                    cryptoPortfolioVO.getToken().getNodeUrl() + CosmosConstants.COSMOS_DISTRIBUTION_V1BETA1_DELEGATORS
                            + "/" + cryptoPortfolioVO.getWallet() + CosmosConstants.COSMOS_REWARDS);
            jsonObject = new JSONObject(cosmosWebService.get(builder));

            if (jsonObject.getJSONArray("total").length() > 0) {
                cryptoPortfolioVO.setAmtTotalReward(Double
                        .parseDouble(df.format(jsonObject.getJSONArray("total").getJSONObject(0).getDouble("amount")
                                / cryptoPortfolioVO.getToken().getDecimalExponent())));
            } else {
                cryptoPortfolioVO.setAmtTotalReward(Double.valueOf(0));
            }
        } catch (Exception e) {
            log.error("setDataOnChain AmtTotalReward Error Message: {}", e.getMessage());
            e.printStackTrace();
        }

        // Get delegated
//        builder = new URIBuilder(cryptoPortfolioVO.getToken().getNodeUrl() + CosmosConstants.COSMOS_STAKING_DELEGATORS
//                + "/" + cryptoPortfolioVO.getWallet() + CosmosConstants.COSMOS_DELEGATIONS);
//        jsonObject = new JSONObject(cosmosWebService.get(builder));
//
//        List<CryptoPortfolioValidatorDelegationVO> validatorDelegation = objectMapperUtil.readValue(
//                jsonObject.getJSONArray("result").toString(),
//                new TypeReference<List<CryptoPortfolioValidatorDelegationVO>>() {
//                });
//
//        Double amtTotalDelegated = Double.valueOf(0);
//        String validatorAddress = null;
//        if (CollectionUtils.isNotEmpty(validatorDelegation)) {
//            Double maxAmount = Double.valueOf(0);
//            for (CryptoPortfolioValidatorDelegationVO item : validatorDelegation) {
//                Double amount = item.getBalance().getAmount();
//                if (amount > 0) {
//                    amtTotalDelegated = amtTotalDelegated + Double.parseDouble(df.format(amount / cryptoPortfolioVO.getToken().getDecimalExponent()));
//                    if (amount > maxAmount) {
//                        maxAmount = amount;
//                        validatorAddress = item.getDelegation().getValidator_address();
//                    }
//                }
//            }
//        }
//        cryptoPortfolioVO.setAmtTotalDelegated(amtTotalDelegated);

        try {
            builder = new URIBuilder(cryptoPortfolioVO.getToken().getNodeUrl()
                    + CosmosConstants.COSMOS_STAKING_V1BETA1_DELEGATION + "/" + cryptoPortfolioVO.getWallet());
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
                        amtTotalDelegated = amtTotalDelegated + Double
                                .parseDouble(df.format(amount / cryptoPortfolioVO.getToken().getDecimalExponent()));
                        if (amount > maxAmount) {
                            maxAmount = amount;
                            validatorAddress = item.getDelegation().getValidator_address();
                        }
                    }
                }
            }
            cryptoPortfolioVO.setAmtTotalDelegated(amtTotalDelegated);

            // Get validator info
            if (validatorAddress != null) {
                cryptoPortfolioVO
                        .setValidator(cryptoValidatorService.getValidator(validatorAddress, cryptoPortfolioVO));
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
        APIResponse<Object> result = new APIResponse<Object>();

        // Check existence
        Optional<CryptoPortfolioTbl> CryptoPortfolioTbl = cryptoPortfolioRepository.findById(id);
        if (CryptoPortfolioTbl.isPresent()) {
            CryptoPortfolioVO vo = cryptoPortfolioTransformer.convertToVO(CryptoPortfolioTbl.get());
            result.setData(vo);
        } else {
            result = new APIResponse<Object>(HttpStatus.BAD_REQUEST,
                    buildMessage(MessageConstants.INEXISTED_RECORD, new String[] { "token" }));
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
        APIResponse<Object> result = new APIResponse<Object>();

        // Check existed
        Optional<CryptoPortfolioTbl> deleteItemTbl = cryptoPortfolioRepository.findById(id);
        if (!deleteItemTbl.isPresent()) {
            result = new APIResponse<Object>(HttpStatus.BAD_REQUEST,
                    buildMessage(MessageConstants.UNAUTHORIZED_DATA, null));
        }

        // Check logged user have permission to delete
        if (result.getStatus() == null && !userService.isDataOwner(deleteItemTbl.get().getUser().getUsername())) {
            result = new APIResponse<Object>(HttpStatus.BAD_REQUEST,
                    buildMessage(MessageConstants.UNAUTHORIZED_DATA, null));
        }

        // Process delete
        if (result.getStatus() == null) {
            cryptoPortfolioRepository.delete(deleteItemTbl.get());
        }

        // Return
        return result;
    }
}

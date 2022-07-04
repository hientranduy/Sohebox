package com.hientran.sohebox.service;

import java.io.IOException;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
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

import com.hientran.sohebox.constants.CosmosConstants;
import com.hientran.sohebox.constants.DBConstants;
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
import com.hientran.sohebox.utils.ObjectMapperUtil;
import com.hientran.sohebox.vo.CryptoPortfolioBankAvailableVO;
import com.hientran.sohebox.vo.CryptoPortfolioBankDelegateVO;
import com.hientran.sohebox.vo.CryptoPortfolioBankRewardVO;
import com.hientran.sohebox.vo.CryptoPortfolioOnChainDataVO;
import com.hientran.sohebox.vo.CryptoPortfolioVO;
import com.hientran.sohebox.vo.CryptoPortfolioValidatorDelegationVO;
import com.hientran.sohebox.vo.PageResultVO;
import com.hientran.sohebox.webservice.CosmosWebService;

/**
 * @author hientran
 */
@Service
@Transactional(readOnly = true)
public class CryptoPortfolioService extends BaseService {

    private static final long serialVersionUID = 1L;

    @Autowired
    private CryptoPortfolioRepository cryptoPortfolioRepository;

    @Autowired
    private CryptoPortfolioTransformer cryptoPortfolioTransformer;

    @Autowired
    private UserService userService;

    @Autowired
    private CosmosWebService cosmosWebService;

    @Autowired
    private ObjectMapperUtil objectMapperUtil;

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

            // Write activity
            recordUserActivity(DBConstants.USER_ACTIVITY_CRYPTO_PORTFOLIO_CREATE);
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

            cryptoPortfolioRepository.save(updateTbl);

            // Write activity
            recordUserActivity(DBConstants.USER_ACTIVITY_CRYPTO_PORTFOLIO_UPDATE);
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
        for (CryptoPortfolioVO item : data.getElements()) {
            try {
                if (StringUtils.isNotEmpty(item.getToken().getNodeUrl())) {
                    CryptoPortfolioOnChainDataVO onChainData = this.getDataOnChain(item);
                    data.getElements().get(data.getElements().indexOf(item)).setOnChainData(onChainData);
                }
            } catch (Exception e) {
                return new APIResponse<Object>(HttpStatus.BAD_REQUEST,
                        buildMessage(MessageConstants.ERROR_EXCEPTION, new String[] { e.getMessage() }));
            }
        }

        // Set data return
        result.setData(data);

        // Write activity
        recordUserActivity(DBConstants.USER_ACTIVITY_CRYPTO_TOKEN_CONFIG_ACCESS);

        // Return
        return result;
    }

    private CryptoPortfolioOnChainDataVO getDataOnChain(CryptoPortfolioVO cryptoPortfolioVO) throws Exception {
        CryptoPortfolioOnChainDataVO result = new CryptoPortfolioOnChainDataVO();
        DecimalFormat df = new DecimalFormat("#.####");
        df.setRoundingMode(RoundingMode.CEILING);
        URIBuilder builder;
        String responseString;

        // Get available
        builder = new URIBuilder(cryptoPortfolioVO.getToken().getNodeUrl() + CosmosConstants.COSMOS_BANK_BALANCES + "/"
                + cryptoPortfolioVO.getWallet());
        responseString = cosmosWebService.get(builder);
        CryptoPortfolioBankAvailableVO bankBalance = objectMapperUtil.readValue(responseString,
                CryptoPortfolioBankAvailableVO.class);
        result.setAmtAvailable(Double.parseDouble(df.format(bankBalance.getResult().get(0).getAmount() / 1000000)));

        // Get delegated
        builder = new URIBuilder(cryptoPortfolioVO.getToken().getNodeUrl() + CosmosConstants.COSMOS_STAKING_DELEGATORS
                + "/" + cryptoPortfolioVO.getWallet() + CosmosConstants.COSMOS_DELEGATIONS);
        responseString = cosmosWebService.get(builder);
        CryptoPortfolioBankDelegateVO bankDelegated = objectMapperUtil.readValue(responseString,
                CryptoPortfolioBankDelegateVO.class);

        Double amtTotalDelegated = Double.valueOf(0);
        for (CryptoPortfolioValidatorDelegationVO item : bankDelegated.getResult()) {
            Double amount = item.getBalance().getAmount();
            if (amount > 0) {
                amtTotalDelegated = amtTotalDelegated + Double.parseDouble(df.format(amount / 1000000));
            }
        }
        result.setAmtTotalDelegated(amtTotalDelegated);

        // Get reward
        builder = new URIBuilder(
                cryptoPortfolioVO.getToken().getNodeUrl() + CosmosConstants.COSMOS_DISTRIBUTION_DELEGATORS + "/"
                        + cryptoPortfolioVO.getWallet() + CosmosConstants.COSMOS_REWARDS);
        responseString = cosmosWebService.get(builder);
        CryptoPortfolioBankRewardVO bankReward = objectMapperUtil.readValue(responseString,
                CryptoPortfolioBankRewardVO.class);

        result.setAmtTotalReward(
                Double.parseDouble(df.format(bankReward.getResult().getTotal().get(0).getAmount() / 1000000)));

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

        // Write activity type "delete account"
        if (deleteItemTbl.isPresent()) {
            recordUserActivity(DBConstants.USER_ACTIVITY_CRYPTO_PORTFOLIO_DELETE);
        }

        // Return
        return result;
    }
}

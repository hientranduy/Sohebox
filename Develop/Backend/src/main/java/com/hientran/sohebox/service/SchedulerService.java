package com.hientran.sohebox.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author hientran
 */
@Service
public class SchedulerService extends BaseService {

    private static final long serialVersionUID = 1L;

    @Autowired
    private BittrexService bittrexService;

    @Autowired
    private EnglishLearnReportService englishLearnReportService;

    @Autowired
    private CryptoPortfolioHistoryService cryptoPortfolioHistoryService;

    /**
     * Get update data master from BITTREX
     * 
     * @throws Exception
     *
     */
    public void bittrexGetUpdateDataMaster() {
        bittrexService.checkAndUpdatePair();
        bittrexService.checkAndUpdateCrypto();
    }

    /**
     * Update market summary all pair
     * 
     * @throws Exception
     *
     */
    public void bittrexUpdateMarketSummaryAll() {
        bittrexService.checkAndUpdateMarketSummaryAll();
    }

    /**
     * Update market summary by selected pairs
     * 
     * @throws Exception
     *
     */
    public void bittrexUpdateMarketSummaryPair() {
        bittrexService.checkAndUpdateMarketSummaryPair();
    }

    /**
     * Update market history
     *
     */
    public void bittrexUpdateMarketHistory() {
        bittrexService.checkAndUpdateMarketHistory();
    }

    /**
     * Extend english learn report
     *
     */
    public void englishExtendLearnReport() {
        englishLearnReportService.fillDailyEnglishLearn();
    }

    /**
     * Calculate crypto portfolio total
     *
     */
    public void cronjobCalculTotalPortfolio() {
        cryptoPortfolioHistoryService.cronjobCalculTotalPortfolio();
    }
}

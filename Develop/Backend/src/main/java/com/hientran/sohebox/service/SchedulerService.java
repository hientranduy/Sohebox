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
	private EnglishLearnReportService englishLearnReportService;

	@Autowired
	private CryptoPortfolioHistoryService cryptoPortfolioHistoryService;

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

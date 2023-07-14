package com.hientran.sohebox.service;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SchedulerService extends BaseService {

	private final EnglishLearnReportService englishLearnReportService;
	private final CryptoPortfolioHistoryService cryptoPortfolioHistoryService;

	/**
	 * Calculate crypto portfolio total
	 *
	 */
	public void cronjobCalculTotalPortfolio() {
		cryptoPortfolioHistoryService.cronjobCalculTotalPortfolio();
	}

	/**
	 * Extend english learn report
	 *
	 */
	public void englishExtendLearnReport() {
		englishLearnReportService.fillDailyEnglishLearn();
	}
}

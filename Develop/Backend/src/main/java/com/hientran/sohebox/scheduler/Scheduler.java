package com.hientran.sohebox.scheduler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.hientran.sohebox.service.SchedulerService;

@Component
public class Scheduler {

	@Autowired
	private SchedulerService schedulerService;

	/**
	 * 
	 * CRON schedule_ Extend english learn report
	 *
	 * @throws Exception
	 */
	@Scheduled(cron = "${schedule.english.learn_report}")
	public void englishExtendLearnReport() throws Exception {
		schedulerService.englishExtendLearnReport();
	}

	/**
	 * 
	 * CRON schedule_ Calculate crypto portfolio total
	 *
	 * @throws Exception
	 */
	@Scheduled(cron = "${schedule.english.learn_report}")
	public void cronjobCalculTotalPortfolio() throws Exception {
		schedulerService.cronjobCalculTotalPortfolio();
	}
}

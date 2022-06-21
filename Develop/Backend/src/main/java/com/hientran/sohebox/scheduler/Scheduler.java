package com.hientran.sohebox.scheduler;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.hientran.sohebox.service.SchedulerService;

/**
 * @author hientran
 */
@Component
public class Scheduler implements Serializable {

    private static final long serialVersionUID = 1L;

    @Autowired
    private SchedulerService schedulerService;

    /**
     * 
     * CRON schedule_ Update BITTREX data master
     *
     * @throws Exception
     */
//    @Scheduled(cron = "${schedule.bittrex.master}")
//    public void bittrexUpdateDataMaster() throws Exception {
//        schedulerService.bittrexGetUpdateDataMaster();
//    }

    /**
     * 
     * Delay schedule_ Update BITTREX market summary all pair
     *
     * @throws Exception
     */
//    @Scheduled(fixedDelayString = "${schedule.bittrex.summary.all}")
//    public void bittrexUpdateMarketSummaryAll() throws Exception {
//        schedulerService.bittrexUpdateMarketSummaryAll();
//    }

    /**
     * 
     * Delay schedule_ Update BITTREX market summary by selected pairs
     *
     * @throws Exception
     */
//    @Scheduled(fixedDelayString = "${schedule.bittrex.summary.pair}")
//    public void bittrexUpdateMarketSummaryPair() throws Exception {
//        schedulerService.bittrexUpdateMarketSummaryPair();
//    }

    /**
     * 
     * Delay schedule_ Update BITTREX market history
     *
     * @throws Exception
     */
//    @Scheduled(fixedDelayString = "${schedule.bittrex.history}")
//    public void bittrexUpdateMarketHistory() throws Exception {
//        schedulerService.bittrexUpdateMarketHistory();
//    }

    /**
     * 
     * CRON schedule_ Extend english learn report
     *
     * @throws Exception
     */
    @Scheduled(cron = "${schedule.english.learn.report}")
    public void englishExtendLearnReport() throws Exception {
        schedulerService.englishExtendLearnReport();
    }
}

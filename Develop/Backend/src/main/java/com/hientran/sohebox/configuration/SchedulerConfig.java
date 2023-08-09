package com.hientran.sohebox.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

@Configuration
@EnableScheduling
public class SchedulerConfig {

	@Value(value = "${schedule.threadCount}")
	private String threadCount;

	/**
	 * Running Tasks in Parallel
	 */
	@Bean
	public TaskScheduler taskScheduler() {
		ThreadPoolTaskScheduler threadPoolTaskScheduler = new ThreadPoolTaskScheduler();
		threadPoolTaskScheduler.setPoolSize(Integer.parseInt(threadCount));
		threadPoolTaskScheduler.setThreadNamePrefix("Sohebox_ThreadPoolTaskScheduler");
		return threadPoolTaskScheduler;
	}
}

package com.hientran.sohebox.configuration;

import java.util.Properties;

import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.velocity.app.VelocityEngine;
import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@Configuration
@EnableScheduling
public class BeanConfig {

	@Value(value = "${schedule.threadCount}")
	private String threadCount;

	/**
	 * 
	 * Bean taskScheduler
	 *
	 */
	@Bean(name = "taskScheduler")
	public ThreadPoolTaskScheduler threadPoolTaskScheduler() {
		ThreadPoolTaskScheduler threadPoolTaskScheduler = new ThreadPoolTaskScheduler();
		threadPoolTaskScheduler.setPoolSize(Integer.parseInt(threadCount));
		return threadPoolTaskScheduler;
	}

	/**
	 * 
	 * Bean httpClient
	 *
	 */
	@Bean(name = "httpClient")
	public CloseableHttpClient httpheaderEcom() {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		return httpClient;
	}

	/**
	 * 
	 * Bean objectMapper
	 *
	 */
	@Bean(name = "objectMapper")
	public Mapper convertObject() {
		Mapper mapper = new DozerBeanMapper();
		return mapper;
	}

	@Bean
	public ObjectMapper objectMapperNew() {
		return new ObjectMapper().setSerializationInclusion(JsonInclude.Include.NON_NULL)
				.registerModule(new JavaTimeModule());
	}

	/**
	 * 
	 * Bean velocityEngine
	 *
	 */
	@Bean(name = "velocityEngine")
	public VelocityEngine velocityEngine() {
		Properties props = new Properties();
		props.put("resource.loader", "class");
		props.put("class.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
		props.put("runtime.log", "./logs/velocity.log");

		VelocityEngine result = new VelocityEngine();
		result.init(props);
		return result;
	}
}

package com.hientran.sohebox.configuration;

import java.io.Serializable;
import java.util.Properties;

import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.velocity.app.VelocityEngine;
import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

/**
 * 
 * Application configure, including bean defination
 *
 * @author hientran
 */
@Configuration
@EnableScheduling
public class ApplicationConfig implements Serializable {

    private static final long serialVersionUID = 1L;

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

    /**
     * 
     * Bean messagesource
     *
     */
    @Bean
    public ResourceBundleMessageSource messageSource() {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasenames("mail", "sysconfig", "message");
        return messageSource;
    }
}

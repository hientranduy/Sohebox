package com.hientran.sohebox.configuration;

import java.io.Serializable;
import java.util.Properties;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

/**
 * Application properties
 *
 * @author hientran
 */
@Configuration
@PropertySource("classpath:sohebox.properties")
public class SoheboxProperties implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 
     * Active @value
     *
     * @return
     */
    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    /**
     * 
     * Get a configuration value by key
     *
     * @param inputKey
     * @return
     */
    public static String getProperty(String inputKey) {
        Properties properties = new Properties();
        try {
            properties.load(SoheboxProperties.class.getClassLoader().getResourceAsStream("sohebox.properties"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return (String) properties.get(inputKey);
    }

    /**
     * 
     * Get a configuration value by key from file application.properties
     *
     * @param inputKey
     * @return
     */
    public static String getPropertyApplication(String inputKey) {
        Properties properties = new Properties();
        try {
            properties.load(SoheboxProperties.class.getClassLoader().getResourceAsStream("application.properties"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return (String) properties.get(inputKey);
    }
}

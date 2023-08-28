package com.hientran.sohebox.configuration;

import java.util.Properties;

import org.apache.velocity.app.VelocityEngine;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@EnableScheduling
public class BeanConfig {

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

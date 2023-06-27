package com.hientran.sohebox;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import lombok.extern.slf4j.Slf4j;

/**
 *
 * @author hientran
 */
@SpringBootApplication
@EnableJpaAuditing // ==> Auto update DB
@Slf4j
public class SoheboxApplication extends SpringBootServletInitializer {

	/**
	 * 
	 * Main entry point of Spring Boot application: => need to jar package type
	 *
	 * @param args
	 */
	public static void main(String[] args) {
		ConfigurableEnvironment env = SpringApplication.run(SoheboxApplication.class, args).getEnvironment();
		String protocol = "http";

		env.getSystemProperties();
		env.getSystemEnvironment();
		env.getPropertySources();
		env.getDefaultProfiles();

		if (env.getProperty("server.ssl.key-store") != null) {
			protocol = "https";
		}
		log.info(
				"\n-----------------------------------------------------------\n\t" 
		        + "Application {} is running \n\t"
				+ "Swagger   : \t{}://localhost:{}/swagger-ui/index.html\n\t"
				+ "Prolile(s): \t{}\n-----------------------------------------------------------",
				SoheboxApplication.class.getSimpleName(), protocol, env.getProperty("server.port"), env.getActiveProfiles());
	}

	/**
	 * {@inheritDoc} ==> Extends SpringBootServletInitializer and override to able
	 * run in Tomcat
	 */
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(SoheboxApplication.class);
	}
}

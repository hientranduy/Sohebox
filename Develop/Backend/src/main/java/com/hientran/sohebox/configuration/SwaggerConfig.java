package com.hientran.sohebox.configuration;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

@Configuration
public class SwaggerConfig {

	@Bean
	public OpenAPI customOpenAPI(@Value("${spring.application.name}") String appName,
			@Value("${application.description}") String appDesciption,
			@Value("${application.version}") String appVersion, @Value("${build.date}") String buildDate) {
		OpenAPI openAPI = new OpenAPI().info(new Info().title(appName).version(appVersion)
				.description(appDesciption.concat(" [").concat(buildDate).concat("]"))
				.termsOfService("http://swagger.io/terms/")
				.license(new License().name("Apache 2.0").url("http://springdoc.org")));

		return openAPI;
	}

	@Bean
	public GroupedOpenApi publicApi() {
		return GroupedOpenApi.builder().group("Publish").packagesToScan("com.hientran.sohebox.restcontroller")
				.pathsToMatch("/**").build();
	}

	@Bean
	public GroupedOpenApi actuatorApi() {
		return GroupedOpenApi.builder().group("Internal").packagesToScan("com.hientran.sohebox.restcontroller.internal")
				.pathsToMatch("/**").build();
	}
}
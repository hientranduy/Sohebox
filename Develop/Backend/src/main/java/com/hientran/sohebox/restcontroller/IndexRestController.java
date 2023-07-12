package com.hientran.sohebox.restcontroller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/")
@Slf4j
public class IndexRestController {

	/**
	 *
	 * For testing only
	 *
	 * @return
	 */
	@GetMapping
	public String sayHello() {
		log.info("test INFO");
		log.error("test ERROR");

		return "Hello and Welcome to the Sohebox application: Spring Boot& Tomcat, MySQL, JPA, Hibernate Restful API";
	}
}

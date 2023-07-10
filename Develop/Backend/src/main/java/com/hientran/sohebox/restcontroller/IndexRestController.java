package com.hientran.sohebox.restcontroller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class IndexRestController {

	/**
	 *
	 * For testing only
	 *
	 * @return
	 */
	@GetMapping
	public String sayHello() {
		return "Hello and Welcome to the Sohebox application: Spring Boot& Tomcat, MySQL, JPA, Hibernate Restful API";
	}
}

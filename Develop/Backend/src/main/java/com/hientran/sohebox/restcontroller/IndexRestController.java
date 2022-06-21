package com.hientran.sohebox.restcontroller;

import java.io.Serializable;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 
 * Just for testing root path when start service
 *
 * @author hientran
 */
@RestController
@RequestMapping("/")
public class IndexRestController implements Serializable {

    private static final long serialVersionUID = 1L;

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

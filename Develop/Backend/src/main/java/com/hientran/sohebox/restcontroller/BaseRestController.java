package com.hientran.sohebox.restcontroller;

import java.io.Serializable;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author hientran
 */
@RestController
@RequestMapping("/api")
public class BaseRestController implements Serializable {

    private static final long serialVersionUID = 1L;
}

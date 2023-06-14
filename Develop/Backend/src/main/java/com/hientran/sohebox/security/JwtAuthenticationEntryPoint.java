package com.hientran.sohebox.security;

import java.io.IOException;
import java.io.Serializable;
import java.util.Collections;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hientran.sohebox.constants.MessageConstants;

/**
 * Custom return message in case unauthentication
 * 
 * @author hientran
 */
@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint, Serializable {

    private static final long serialVersionUID = 1L;

    @Autowired
    protected ResourceBundleMessageSource messageSource;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException authException) throws IOException {
        // Get message expired
        String message = (String) request.getAttribute("expired");

        // If not expired
        if (message == null) {
            message = messageSource.getMessage(MessageConstants.UNAUTHORIZED_USER, null, null);
        }

        // Set response content
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        byte[] body = new ObjectMapper().writeValueAsBytes(Collections.singletonMap("message", message));
        response.getOutputStream().write(body);
    }
}
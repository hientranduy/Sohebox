package com.hientran.sohebox.authentication;

import java.io.IOException;
import java.util.Collections;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hientran.sohebox.constants.ResponseCode;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JWTAuthenticationEntryPoint implements AuthenticationEntryPoint {

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException {
		// Get message expired
		String message = (String) request.getAttribute("expired");

		// If not expired
		if (message == null) {
			message = ResponseCode.UNAUTHORIZED_USER.getDescription();
		}

		// Set response content
		response.setStatus(HttpStatus.UNAUTHORIZED.value());
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		byte[] body = new ObjectMapper().writeValueAsBytes(Collections.singletonMap("message", message));
		response.getOutputStream().write(body);
	}
}
package com.hientran.sohebox.authentication;

import java.io.IOException;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JWTAuthenticationTokenFilter extends OncePerRequestFilter {

	private JWTTokenService tokenService;

	/**
	 * Construction
	 *
	 */
	public JWTAuthenticationTokenFilter(JWTTokenService jWTTokenService) {
		super();
		tokenService = jWTTokenService;
	}

	/**
	 * Authenticate token
	 *
	 * {@inheritDoc}
	 */
	@Override
	public void doFilterInternal(HttpServletRequest servletRequest, HttpServletResponse servletResponse,
			FilterChain filterChain) throws IOException, ServletException {
		// Get Authentication
		Authentication authentication = tokenService.getAuthentication(servletRequest);

		// Set Authentication
		SecurityContextHolder.getContext().setAuthentication(authentication);

		// Do filter
		filterChain.doFilter(servletRequest, servletResponse);
	}
}
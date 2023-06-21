package com.hientran.sohebox.security;

import java.io.IOException;
import java.util.Collections;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * 
 * Authentication when access by username/password
 *
 * @author hientran
 */
public class JWTLoginFilter extends UsernamePasswordAuthenticationFilter {

	private JWTTokenService tokenAuthenticationService;

	/**
	 * 
	 * Constructor
	 *
	 * @param url
	 * @param authManager
	 * @param tokenAuthentication
	 */
	public JWTLoginFilter(String url, AuthenticationManager authManager, JWTTokenService tokenAuthentication) {

		// Set login URL and method
		this.setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher(url, "POST"));

		// Set Authentication manager
		setAuthenticationManager(authManager);

		// Set value to local
		tokenAuthenticationService = tokenAuthentication;
	}

	/**
	 * Authorize login
	 * 
	 * {@inheritDoc}
	 */
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		try {
			UserCredentials creds = new ObjectMapper().readValue(request.getInputStream(), UserCredentials.class);

			UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(creds.getUsername(),
					creds.getPassword(), Collections.emptyList());

			return getAuthenticationManager().authenticate(authToken);

		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * In case authenticate success, generate JWT and add to token
	 * 
	 * {@inheritDoc}
	 */
	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {
		tokenAuthenticationService.addAuthentication(response, authResult.getName());
	}

	/**
	 * 
	 * Temporary UserCredentials
	 *
	 */
	private static class UserCredentials {
		private String username, password;

		public String getUsername() {
			return username;
		}

		public String getPassword() {
			return password;
		}
	}
}
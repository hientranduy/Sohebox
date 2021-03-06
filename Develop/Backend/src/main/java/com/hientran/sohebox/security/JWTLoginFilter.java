package com.hientran.sohebox.security;

import java.io.IOException;
import java.util.Collections;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.fasterxml.jackson.databind.ObjectMapper;

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
            // Get credentials from request
            UserCredentials creds = new ObjectMapper().readValue(request.getInputStream(), UserCredentials.class);

            System.out.printf("JWTLoginFilter.attemptAuthentication: username/password= %s,%s ", creds.getUsername(),
                    creds.getPassword());

            // Create auth object (contains credentials) which will be used by auth manager
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
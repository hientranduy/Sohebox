package com.hientran.sohebox.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * 
 * Authentication when access by token
 *
 * @author hientran
 */
public class JWTAuthenticationFilter extends OncePerRequestFilter {

    private JWTTokenService tokenService;

    /**
     * Construction
     *
     */
    public JWTAuthenticationFilter(JWTTokenService jWTTokenService) {
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
        Authentication authentication = tokenService.getAuthentication((HttpServletRequest) servletRequest);

        // Set Authentication
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Do filter
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
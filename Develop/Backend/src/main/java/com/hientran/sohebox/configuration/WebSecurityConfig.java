package com.hientran.sohebox.configuration;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.DefaultAuthenticationEventPublisher;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.hientran.sohebox.constants.ApiPublicConstants;
import com.hientran.sohebox.constants.DBConstants;
import com.hientran.sohebox.security.JWTAuthenticationFilter;
import com.hientran.sohebox.security.JWTLoginFilter;
import com.hientran.sohebox.security.JWTTokenService;
import com.hientran.sohebox.security.JwtAuthenticationEntryPoint;
import com.hientran.sohebox.security.UserService;

/**
 * Web access security config
 *
 * @author hientran
 */
@Configuration
@EnableWebSecurity
@ComponentScan("com.hientran.sohebox")
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserService userService;

    @Autowired
    private JWTTokenService tokenAuthenticationService;

    @Autowired
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationEventPublisher(authenticationEventPublisher()).userDetailsService(userService)
                .passwordEncoder(passwordEncoder());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DefaultAuthenticationEventPublisher authenticationEventPublisher() {
        return new DefaultAuthenticationEventPublisher();
    }

    /**
     * Configure access from web
     * 
     * {@inheritDoc}
     */
    @Override
    public void configure(WebSecurity web) throws Exception {
        // TODO if need ignore
        // web.ignoring().antMatchers("/v2/api-docs").antMatchers("/swagger-resources/**").antMatchers("/swagger-ui.html");
    }

    /**
     * Configure access from http
     * 
     * {@inheritDoc}
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable();

        // Register custom error handle
        http.httpBasic().authenticationEntryPoint(jwtAuthenticationEntryPoint);

        // Don't create session
        http.httpBasic().and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        // Set secured URLs
        http.authorizeRequests()

                ////////////////////////////////////////
                // PERMISSION - ALLOW ACCESS - PUBLIC //
                ////////////////////////////////////////

                //////////////////////
                // General access //
                //////////////////////

                // Swagger
                .antMatchers("/v2/api-docs", "/v3/api-docs", "/swagger-ui/**", "/swagger-ui/", "/swagger-ui.html",
                        "/swagger-resources/**")
                .permitAll()

                // Home page
                .antMatchers("/").permitAll()

                // Register user
                .antMatchers(HttpMethod.POST, ApiPublicConstants.API_USER).permitAll()

                // Change password
                .antMatchers(HttpMethod.PUT, ApiPublicConstants.API_USER + ApiPublicConstants.CHANGE_PASSWORD)
                .permitAll()

                // Login
                .antMatchers(HttpMethod.POST, ApiPublicConstants.API_USER + ApiPublicConstants.AUTHENTICATE).permitAll()

                // Logout
                .antMatchers(HttpMethod.POST, ApiPublicConstants.API_USER + ApiPublicConstants.LOGOUT).permitAll()

                //////////////////////
                // Food //
                //////////////////////
                // Food: search
                .antMatchers(HttpMethod.POST, "/api" + ApiPublicConstants.API_FOOD + ApiPublicConstants.SEARCH)
                .permitAll()
                // Food: get item
                .antMatchers(HttpMethod.GET, "/api" + ApiPublicConstants.API_FOOD + "/*").permitAll()

                //////////////////////
                // Media //
                //////////////////////
                // YOUTUBE channel: search
                .antMatchers(HttpMethod.POST,
                        "/api" + ApiPublicConstants.API_YOUTUBE_CHANNEL + ApiPublicConstants.SEARCH)
                .permitAll()
                // YOUTUBE channel: search my channel
                .antMatchers(HttpMethod.POST,
                        "/api" + ApiPublicConstants.API_YOUTUBE_CHANNEL + ApiPublicConstants.SEARCH_MY_OWNER)
                .permitAll()
                // YOUTUBE video: search
                .antMatchers(HttpMethod.POST, "/api" + ApiPublicConstants.API_YOUTUBE_VIDEO + ApiPublicConstants.SEARCH)
                .permitAll()
                .antMatchers(HttpMethod.POST,
                        "/api" + ApiPublicConstants.API_YOUTUBE_VIDEO + ApiPublicConstants.SEARCH_BY_CHANNEL)
                .permitAll()

                //////////////////////
                // Finance //
                //////////////////////
                // QUANDL
                .antMatchers(HttpMethod.GET, "/api" + ApiPublicConstants.API_QUANDL + "/*").permitAll()
                // TRADING
                .antMatchers(HttpMethod.GET, "/api" + ApiPublicConstants.API_TRADINGECONOMICS + "/*").permitAll()
                // FINANCE
                .antMatchers(HttpMethod.GET, "/api" + ApiPublicConstants.API_FINANCE + "/*").permitAll()

                ///////////////////////////////////////////
                // RESTRICTION - ADMIN ONLY //
                ///////////////////////////////////////////
                // User: get all
                .antMatchers(HttpMethod.GET, ApiPublicConstants.API_USER).hasRole(DBConstants.USER_ROLE_CREATOR)
                // User: search
                .antMatchers(HttpMethod.POST, ApiPublicConstants.API_USER + ApiPublicConstants.SEARCH)
                .hasRole(DBConstants.USER_ROLE_CREATOR)
                // User: delete
                .antMatchers(HttpMethod.DELETE, ApiPublicConstants.API_USER + "/*")
                .hasRole(DBConstants.USER_ROLE_CREATOR)

                // Config: create
                .antMatchers(HttpMethod.POST, "/api" + ApiPublicConstants.API_CONFIG)
                .hasRole(DBConstants.USER_ROLE_CREATOR)
                .antMatchers(HttpMethod.POST, "/api" + ApiPublicConstants.API_ENGLISH)
                .hasRole(DBConstants.USER_ROLE_CREATOR)
                .antMatchers(HttpMethod.POST, "/api" + ApiPublicConstants.API_FOOD)
                .hasRole(DBConstants.USER_ROLE_CREATOR)
                .antMatchers(HttpMethod.POST, "/api" + ApiPublicConstants.API_YOUTUBE_CHANNEL)
                .hasRole(DBConstants.USER_ROLE_CREATOR)

                // Config: update
                .antMatchers(HttpMethod.PUT, "/api" + ApiPublicConstants.API_CONFIG)
                .hasRole(DBConstants.USER_ROLE_CREATOR)
                .antMatchers(HttpMethod.PUT, "/api" + ApiPublicConstants.API_ENGLISH)
                .hasRole(DBConstants.USER_ROLE_CREATOR)
                .antMatchers(HttpMethod.PUT, "/api" + ApiPublicConstants.API_FOOD)
                .hasRole(DBConstants.USER_ROLE_CREATOR)
                .antMatchers(HttpMethod.PUT, "/api" + ApiPublicConstants.API_YOUTUBE_CHANNEL)
                .hasRole(DBConstants.USER_ROLE_CREATOR)

                // Config: search
                .antMatchers(HttpMethod.POST, "/api" + ApiPublicConstants.API_CONFIG + ApiPublicConstants.SEARCH)
                .hasRole(DBConstants.USER_ROLE_CREATOR)

                // Config: get
                .antMatchers(HttpMethod.GET, "/api" + ApiPublicConstants.API_CONFIG + "/*")
                .hasRole(DBConstants.USER_ROLE_CREATOR)

                // Type: create
                .antMatchers(HttpMethod.POST, "/api" + ApiPublicConstants.API_TYPE)
                .hasRole(DBConstants.USER_ROLE_CREATOR)

                // Type: update
                .antMatchers(HttpMethod.PUT, "/api" + ApiPublicConstants.API_TYPE)
                .hasRole(DBConstants.USER_ROLE_CREATOR)
                .antMatchers(HttpMethod.PUT, "/api" + ApiPublicConstants.API_ENGLISH_TYPE)
                .hasRole(DBConstants.USER_ROLE_CREATOR)
                .antMatchers(HttpMethod.PUT, "/api" + ApiPublicConstants.API_FOOD_TYPE)
                .hasRole(DBConstants.USER_ROLE_CREATOR)
                .antMatchers(HttpMethod.PUT, "/api" + ApiPublicConstants.API_FOOD_TYPE)
                .hasRole(DBConstants.USER_ROLE_CREATOR)

                // Add new trading symbol
                .antMatchers(HttpMethod.POST, "/api" + ApiPublicConstants.API_TRADING_SYMBOL)
                .hasRole(DBConstants.USER_ROLE_CREATOR)

                // Show account password
                .antMatchers(HttpMethod.POST,
                        "/api" + ApiPublicConstants.API_ACCOUNT + ApiPublicConstants.API_ACCOUNT_SHOW_PASSWORD)
                .hasRole(DBConstants.USER_ROLE_CREATOR)

                // Crypto
                .antMatchers(HttpMethod.POST, "/api" + ApiPublicConstants.API_CRYPTO_TOKEN_CONFIG)
                .hasRole(DBConstants.USER_ROLE_CREATOR)
                .antMatchers(HttpMethod.PUT, "/api" + ApiPublicConstants.API_CRYPTO_TOKEN_CONFIG)
                .hasRole(DBConstants.USER_ROLE_CREATOR)

                ///////////////////////////////////////////
                // REQUIRED LOGI - AUTHENTICATION //
                ///////////////////////////////////////////
                .anyRequest().fullyAuthenticated()

                .and()

                // Login by user name/password
                .addFilterAfter(
                        new JWTLoginFilter(ApiPublicConstants.API_USER + ApiPublicConstants.AUTHENTICATE,
                                authenticationManager(), tokenAuthenticationService),
                        UsernamePasswordAuthenticationFilter.class)

                // Login by token bearer
                .addFilterAfter(new JWTAuthenticationFilter(tokenAuthenticationService),
                        UsernamePasswordAuthenticationFilter.class);
    }

    /**
     * Create Cors Configuration Source bean.
     * 
     * It is for accept access from Web like AngularJS.
     *
     * @return CorsConfigurationSource object
     */
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        // Build CORS settings
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowedOriginPatterns(Arrays.asList("*"));
        corsConfiguration.setAllowedMethods(Arrays.asList(HttpMethod.GET.name(), HttpMethod.POST.name(),
                HttpMethod.PUT.name(), HttpMethod.DELETE.name(), HttpMethod.OPTIONS.name(), HttpMethod.HEAD.name()));
        corsConfiguration.setAllowedHeaders(Arrays.asList("*"));
        corsConfiguration.setAllowCredentials(true);
        corsConfiguration.setMaxAge(Long.valueOf(3600));

        // Register CORS configuration
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfiguration);
        return source;
    }
}
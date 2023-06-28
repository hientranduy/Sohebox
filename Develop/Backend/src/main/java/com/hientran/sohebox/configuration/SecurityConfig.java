package com.hientran.sohebox.configuration;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.DefaultAuthenticationEventPublisher;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.hientran.sohebox.authentication.JWTAuthenticationLoginFilter;
import com.hientran.sohebox.authentication.JWTAuthenticationTokenFilter;
import com.hientran.sohebox.authentication.JWTTokenService;
import com.hientran.sohebox.authentication.JwtAuthenticationEntryPoint;
import com.hientran.sohebox.authentication.UserDetailsServiceImpl;
import com.hientran.sohebox.constants.ApiPublicConstants;
import com.hientran.sohebox.constants.DBConstants;

/**
 * Web access security config
 *
 * @author hientran
 */
@Configuration
@EnableWebSecurity
@ConditionalOnProperty(name = "authentication.isActived", havingValue = "true", matchIfMissing = true)
public class SecurityConfig {

	@Autowired
	private UserDetailsServiceImpl userService;

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

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
			throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		// Enable CORS and disable CSRF
		http = http.cors().and().csrf().disable();

		// Register custom error handle
		http.httpBasic().authenticationEntryPoint(jwtAuthenticationEntryPoint);

		// Don't create session
		http.httpBasic().and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

		// Set secured URLs
		http.authorizeHttpRequests()

				////////////////////////////////////////
				// PERMISSION - ALLOW ACCESS - PUBLIC //
				////////////////////////////////////////

				//////////////////////
				// General access //
				//////////////////////

				// Swagger
				.requestMatchers("/v2/api-docs/**", "/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html",
						"/swagger-resources/**")
				.permitAll()

				// Home page
				.requestMatchers("/").permitAll()

				// Add role
				.requestMatchers(HttpMethod.POST, "/api" + ApiPublicConstants.API_ROLE + ApiPublicConstants.ADD)
				.permitAll()

				// Register user
				.requestMatchers(HttpMethod.POST, ApiPublicConstants.API_USER).permitAll()

				// Change password
				.requestMatchers(HttpMethod.PUT, ApiPublicConstants.API_USER + ApiPublicConstants.CHANGE_PASSWORD)
				.permitAll()

				// Login
				.requestMatchers(HttpMethod.POST, ApiPublicConstants.API_USER + ApiPublicConstants.AUTHENTICATE)
				.permitAll()

				// Logout
				.requestMatchers(HttpMethod.POST, ApiPublicConstants.API_USER + ApiPublicConstants.LOGOUT).permitAll()

				//////////////////////
				// Food //
				//////////////////////
				// Food: search
				.requestMatchers(HttpMethod.POST, "/api" + ApiPublicConstants.API_FOOD + ApiPublicConstants.SEARCH)
				.permitAll()
				// Food: get item
				.requestMatchers(HttpMethod.GET, "/api" + ApiPublicConstants.API_FOOD + "/*").permitAll()

				//////////////////////
				// Media //
				//////////////////////
				// YOUTUBE channel: search
				.requestMatchers(HttpMethod.POST,
						"/api" + ApiPublicConstants.API_YOUTUBE_CHANNEL + ApiPublicConstants.SEARCH)
				.permitAll()
				// YOUTUBE channel: search my channel
				.requestMatchers(HttpMethod.POST,
						"/api" + ApiPublicConstants.API_YOUTUBE_CHANNEL + ApiPublicConstants.SEARCH_MY_OWNER)
				.permitAll()
				// YOUTUBE video: search
				.requestMatchers(HttpMethod.POST,
						"/api" + ApiPublicConstants.API_YOUTUBE_VIDEO + ApiPublicConstants.SEARCH)
				.permitAll()
				.requestMatchers(HttpMethod.POST,
						"/api" + ApiPublicConstants.API_YOUTUBE_VIDEO + ApiPublicConstants.SEARCH_BY_CHANNEL)
				.permitAll()

				//////////////////////
				// Finance //
				//////////////////////
				// QUANDL
				.requestMatchers(HttpMethod.GET, "/api" + ApiPublicConstants.API_QUANDL + "/*").permitAll()
				// TRADING
				.requestMatchers(HttpMethod.GET, "/api" + ApiPublicConstants.API_TRADINGECONOMICS + "/*").permitAll()
				// FINANCE
				.requestMatchers(HttpMethod.GET, "/api" + ApiPublicConstants.API_FINANCE + "/*").permitAll()

				///////////////////////////////////////////
				// RESTRICTION - ADMIN ONLY //
				///////////////////////////////////////////
				// User: get all
				.requestMatchers(HttpMethod.GET, ApiPublicConstants.API_USER).hasRole(DBConstants.USER_ROLE_CREATOR)
				// User: search
				.requestMatchers(HttpMethod.POST, ApiPublicConstants.API_USER + ApiPublicConstants.SEARCH)
				.hasRole(DBConstants.USER_ROLE_CREATOR)
				// User: delete
				.requestMatchers(HttpMethod.DELETE, ApiPublicConstants.API_USER + "/*")
				.hasRole(DBConstants.USER_ROLE_CREATOR)

				// Config: create-update-search
				.requestMatchers(HttpMethod.POST, "/api" + ApiPublicConstants.API_CONFIG)
				.hasRole(DBConstants.USER_ROLE_CREATOR)
				.requestMatchers(HttpMethod.PUT, "/api" + ApiPublicConstants.API_CONFIG)
				.hasRole(DBConstants.USER_ROLE_CREATOR)
				.requestMatchers(HttpMethod.POST, "/api" + ApiPublicConstants.API_CONFIG + ApiPublicConstants.SEARCH)
				.hasRole(DBConstants.USER_ROLE_CREATOR)

				// Type: create-update-search
				.requestMatchers(HttpMethod.POST, "/api" + ApiPublicConstants.API_TYPE)
				.hasRole(DBConstants.USER_ROLE_CREATOR)
				.requestMatchers(HttpMethod.PUT, "/api" + ApiPublicConstants.API_TYPE)
				.hasRole(DBConstants.USER_ROLE_CREATOR)
				.requestMatchers(HttpMethod.POST, "/api" + ApiPublicConstants.API_TYPE + ApiPublicConstants.SEARCH)
				.hasRole(DBConstants.USER_ROLE_CREATOR)

				// EnglishType: update-search
				.requestMatchers(HttpMethod.PUT, "/api" + ApiPublicConstants.API_ENGLISH_TYPE)
				.hasRole(DBConstants.USER_ROLE_CREATOR)
				.requestMatchers(HttpMethod.POST,
						"/api" + ApiPublicConstants.API_ENGLISH_TYPE + ApiPublicConstants.SEARCH)
				.hasRole(DBConstants.USER_ROLE_CREATOR)

				// FoodType: update-search
				.requestMatchers(HttpMethod.PUT, "/api" + ApiPublicConstants.API_FOOD_TYPE)
				.hasRole(DBConstants.USER_ROLE_CREATOR)
				.requestMatchers(HttpMethod.POST, "/api" + ApiPublicConstants.API_FOOD_TYPE + ApiPublicConstants.SEARCH)
				.hasRole(DBConstants.USER_ROLE_CREATOR)

				// MediaType: update-search
				.requestMatchers(HttpMethod.PUT, "/api" + ApiPublicConstants.API_MEDIA_TYPE)
				.hasRole(DBConstants.USER_ROLE_CREATOR)
				.requestMatchers(HttpMethod.POST,
						"/api" + ApiPublicConstants.API_MEDIA_TYPE + ApiPublicConstants.SEARCH)
				.hasRole(DBConstants.USER_ROLE_CREATOR)

				// English: add-update
				.requestMatchers(HttpMethod.PUT, "/api" + ApiPublicConstants.API_ENGLISH)
				.hasRole(DBConstants.USER_ROLE_CREATOR)
				.requestMatchers(HttpMethod.POST, "/api" + ApiPublicConstants.API_ENGLISH)
				.hasRole(DBConstants.USER_ROLE_CREATOR)

				// Food: add-update
				.requestMatchers(HttpMethod.POST, "/api" + ApiPublicConstants.API_FOOD)
				.hasRole(DBConstants.USER_ROLE_CREATOR)
				.requestMatchers(HttpMethod.PUT, "/api" + ApiPublicConstants.API_FOOD)
				.hasRole(DBConstants.USER_ROLE_CREATOR)

				// Youtube Channel: add-update
				.requestMatchers(HttpMethod.POST, "/api" + ApiPublicConstants.API_YOUTUBE_CHANNEL)
				.hasRole(DBConstants.USER_ROLE_CREATOR)
				.requestMatchers(HttpMethod.PUT, "/api" + ApiPublicConstants.API_YOUTUBE_CHANNEL)
				.hasRole(DBConstants.USER_ROLE_CREATOR)
				
				// Crypto Token: add-update
				.requestMatchers(HttpMethod.POST, "/api" + ApiPublicConstants.API_CRYPTO_TOKEN_CONFIG)
				.hasRole(DBConstants.USER_ROLE_CREATOR)
				.requestMatchers(HttpMethod.PUT, "/api" + ApiPublicConstants.API_CRYPTO_TOKEN_CONFIG)
				.hasRole(DBConstants.USER_ROLE_CREATOR)

				// Trading symbol: add
				.requestMatchers(HttpMethod.POST, "/api" + ApiPublicConstants.API_TRADING_SYMBOL)
				.hasRole(DBConstants.USER_ROLE_CREATOR)

				// Show account password
				.requestMatchers(HttpMethod.POST,
						"/api" + ApiPublicConstants.API_ACCOUNT + ApiPublicConstants.API_ACCOUNT_SHOW_PASSWORD)
				.hasRole(DBConstants.USER_ROLE_CREATOR)

				///////////////////////////////////////////
				// REQUIRED LOGI - AUTHENTICATION //
				///////////////////////////////////////////
				.anyRequest().fullyAuthenticated()

				.and()

				// Login by user name/password
				.addFilterAfter(
						new JWTAuthenticationLoginFilter(ApiPublicConstants.API_USER + ApiPublicConstants.AUTHENTICATE,
								authenticationManager(http.getSharedObject(AuthenticationConfiguration.class)),
								tokenAuthenticationService),
						UsernamePasswordAuthenticationFilter.class)

				// Login by token bearer
				.addFilterAfter(new JWTAuthenticationTokenFilter(tokenAuthenticationService),
						UsernamePasswordAuthenticationFilter.class);

		return http.build();
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
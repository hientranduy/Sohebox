package com.hientran.sohebox.authentication;

import java.io.IOException;
import java.util.Date;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hientran.sohebox.constants.DBConstants;
import com.hientran.sohebox.dto.UserVO;
import com.hientran.sohebox.service.UserActivityService;
import com.hientran.sohebox.service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JWTTokenService {

	@Value("${jwt.secret}")
	private String SECRET;

	@Value("${jwt.expiration}")
	private Long EXPIRATIONTIME;

	private String SCHEME = "Bearer";

	private String HEADER = "Authorization";

	private final UserService userService;
	private final UserDetailsServiceImpl userDetailsServiceImpl;
	private final UserActivityService userActivityService;
	private final ObjectMapper objectMapper;

	public void addAuthentication(HttpServletResponse res, String username) throws IOException {
		// Prepare parameter
		Date createdDate = new Date();
		Date expiredDate = calculateExpirationDate(createdDate, null);

		// Generate JWT
		String jwtToken = JWT.create().withIssuer("Sohebox").withSubject(username).withClaim("userName", username)
				.withIssuedAt(createdDate).withExpiresAt(expiredDate).withJWTId(UUID.randomUUID().toString())
				.withNotBefore(createdDate) // allow use token right after create
				.sign(Algorithm.HMAC256(SECRET));

		// Add to header
		res.addHeader(HEADER, SCHEME + " " + jwtToken);

		// Add to body
		UserVO userVO = userService.getByUserName(username);
		userVO.setToken(SCHEME + " " + jwtToken);

		res.setContentType("application/json");
		res.setCharacterEncoding("UTF-8");
		res.getWriter().write(objectMapper.writeValueAsString(userVO));

		// Record activity "LOGIN"
		userActivityService.recordUserActivity(userVO, DBConstants.USER_ACTIVITY_LOGIN);
	}

	public Authentication getAuthentication(HttpServletRequest request) {
		// Declare return
		Authentication result = null;

		// Get token from request
		String token = request.getHeader(HEADER);

		if (StringUtils.isNotBlank(token)) {
			String userName = getUserNameFromToken(token);
			if (StringUtils.isNotBlank(userName)) {
				UserDetails userDetails = userDetailsServiceImpl.loadUserByUsername(userName);
				if (userName != null) {
					result = new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
				}
			}
		}

		// Return
		return result;
	}

	public boolean validateToken(String token) {
		try {
			JWTVerifier verifier = JWT.require(Algorithm.HMAC256(SECRET)).withIssuer("Sohebox").build();
			verifier.verify(token);
			return true;
		} catch (JWTVerificationException e) {
			System.out.println(e.getMessage());
			return false;
		}
	}

	private String getUserNameFromToken(String token) {
		try {
			if (validateToken(getJWTToken(token))) {
				DecodedJWT decodedJWT = JWT.decode(getJWTToken(token));
				return decodedJWT.getSubject();
			} else {
				return null;
			}
		} catch (JWTVerificationException e) {
			System.out.println(e.getMessage());
			return null;
		}
	}

	private Date calculateExpirationDate(Date createdDate, Long expirationMiliSecond) {
		if (expirationMiliSecond == null || expirationMiliSecond <= 0) {
			return new Date(createdDate.getTime() + EXPIRATIONTIME);
		} else {
			return new Date(createdDate.getTime() + expirationMiliSecond);
		}
	}

	public static String getJWTToken(String bearerToken) {
		String jwtToken = bearerToken.replace("Bearer ", "");
		return jwtToken;
	}
}
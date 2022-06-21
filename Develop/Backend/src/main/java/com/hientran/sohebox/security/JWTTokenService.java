package com.hientran.sohebox.security;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hientran.sohebox.constants.DBConstants;
import com.hientran.sohebox.service.UserActivityService;
import com.hientran.sohebox.utils.LogUtils;
import com.hientran.sohebox.vo.UserVO;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.DefaultClock;

/**
 * 
 * Token service
 *
 * @author hientran
 */
@Component
public class JWTTokenService {

    @Value("${jwt.secret}")
    private String SECRET;

    @Value("${jwt.expiration}")
    private Long EXPIRATIONTIME;

    private String SCHEME = "Bearer";

    private String HEADER = "Authorization";

    @Autowired
    private UserService userService;

    @Autowired
    private UserActivityService userActivityService;

    @Autowired
    private ObjectMapper objectMapper;

    /**
     * 
     * Generate token and add to header
     *
     * @param res
     * @param username
     * @throws IOException
     */
    public void addAuthentication(HttpServletResponse res, String username) throws IOException {
        // Prepare parameter
        Map<String, Object> claims = new HashMap<>();
        Date createdDate = DefaultClock.INSTANCE.now();
        Date expiredDate = calculateExpirationDate(createdDate, null);

        // Generate JWT
        String JWT = Jwts.builder().setClaims(claims).setSubject(username).setIssuedAt(createdDate)
                .setExpiration(expiredDate).signWith(SignatureAlgorithm.HS512, SECRET).compact();

        // Add to header
        res.addHeader(HEADER, SCHEME + " " + JWT);

        // Add to body
        UserVO userVO = userService.getByUserName(username);
        userVO.setToken(SCHEME + " " + JWT);

        res.setContentType("application/json");
        res.setCharacterEncoding("UTF-8");
        res.getWriter().write(objectMapper.writeValueAsString(userVO));

        System.out.println("Authorization String=" + res.getHeader(HEADER));

        // Record activity "LOGIN"
        userActivityService.recordUserActivity(userVO, DBConstants.USER_ACTIVITY_LOGIN);
    }

    /**
     * 
     * Get authentication
     *
     * @param request
     * @return
     */
    public Authentication getAuthentication(HttpServletRequest request) {
        // Declare return
        Authentication result = null;

        // Get token from request
        String token = request.getHeader(HEADER);

        if (StringUtils.isNotBlank(token)) {
            String userName = getUserNameFromToken(token);
            if (StringUtils.isNotBlank(userName)) {
                UserDetails userDetails = userService.loadUserByUsername(userName);
                if (userName != null) {
                    result = new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
                }
            }
        }

        // Return
        return result;
    }

    /**
     * 
     * Validate token
     *
     * @param token
     * @return
     */
    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            LogUtils.writeLogError(e);
            return false;
        }
    }

    /**
     * 
     * Get user name from token
     *
     * @param token
     * @return
     */
    private String getUserNameFromToken(String token) {
        String result = null;

        try {
            result = Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token.replace(SCHEME, "")).getBody()
                    .getSubject();
        } catch (Exception e) {
        }

        return result;
    }

    /**
     * 
     * Calculate the expiration date
     *
     * @param createdDate
     * @param expirationCustom
     * 
     * @return expired date
     */
    private Date calculateExpirationDate(Date createdDate, Long expirationMiliSecond) {
        if (expirationMiliSecond == null || expirationMiliSecond <= 0) {
            return new Date(createdDate.getTime() + EXPIRATIONTIME);
        } else {
            return new Date(createdDate.getTime() + expirationMiliSecond);
        }
    }
}
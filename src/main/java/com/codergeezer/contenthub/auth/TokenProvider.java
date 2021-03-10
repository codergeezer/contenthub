package com.codergeezer.contenthub.auth;

import com.codergeezer.contenthub.dto.response.LoginResponse;
import com.codergeezer.contenthub.utils.Constant;
import com.codergeezer.core.base.exception.BaseException;
import com.codergeezer.core.base.exception.CommonErrorCode;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @author haidv
 * @version 1.0
 */
@Component
public class TokenProvider {

    @Value("${access.token.expired}")
    private long accessTokenExpired;

    @Value("${refresh.token.expired}")
    private long refreshTokenExpired;

    @Value("${token.secret.key}")
    private String secretKey;

    private String generateToken(String username, String tokenType, Long timeExpired, String uuId) {
        Date now = Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant());
        Date expiryDate = new Date(now.getTime() + timeExpired);
        return Jwts.builder()
                   .setSubject(tokenType)
                   .setIssuer(username)
                   .setIssuedAt(now)
                   .setExpiration(expiryDate)
                   .signWith(SignatureAlgorithm.HS256, secretKey)
                   .setAudience(uuId)
                   .setId(UUID.randomUUID().toString())
                   .compact();
    }

    public LoginResponse createAccessToken(String username, String uuId) {
        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setAccessToken(generateToken(username, Constant.ACCESS_TOKEN, accessTokenExpired, uuId));
        loginResponse.setAccessExpired(accessTokenExpired);
        return loginResponse;
    }

    public LoginResponse createToken(String username, String uuId) {
        LoginResponse loginResponse = createAccessToken(username, uuId);
        loginResponse.setRefreshToken(generateToken(username, Constant.REFRESH_TOKEN, refreshTokenExpired, uuId));
        loginResponse.setRefreshExpired(refreshTokenExpired);
        return loginResponse;
    }

    public Claims getAllClaimsFromToken(String token) throws BaseException {
        try {
            return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
        } catch (MalformedJwtException | UnsupportedJwtException | IllegalArgumentException | SignatureException | ExpiredJwtException ex) {
            throw new BaseException(ex.getMessage(), CommonErrorCode.UNAUTHORIZED);
        }
    }

    public String getUsernameFromToken(String token) {
        return this.getAllClaimsFromToken(token).getIssuer();
    }

    public LocalDateTime getAccessTokenExpired() {
        return LocalDateTime.now().plusSeconds(TimeUnit.MILLISECONDS.toSeconds(accessTokenExpired));
    }
}

package com.codergeezer.contenthub.dto.response;

/**
 * @author haidv
 * @version 1.0
 */
public class LoginResponse {

    private String accessToken;

    private String refreshToken;

    private Long accessExpired;

    private Long refreshExpired;

    private String securityCode;

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public Long getAccessExpired() {
        return accessExpired;
    }

    public void setAccessExpired(Long accessExpired) {
        this.accessExpired = accessExpired;
    }

    public Long getRefreshExpired() {
        return refreshExpired;
    }

    public void setRefreshExpired(Long refreshExpired) {
        this.refreshExpired = refreshExpired;
    }

    public String getSecurityCode() {
        return securityCode;
    }

    public void setSecurityCode(String securityCode) {
        this.securityCode = securityCode;
    }
}

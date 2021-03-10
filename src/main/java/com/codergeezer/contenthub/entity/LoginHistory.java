package com.codergeezer.contenthub.entity;

import com.codergeezer.core.base.data.BaseEntity;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * @author haidv
 * @version 1.0
 */
@Entity
@Table(name = "tbl_login_history")
public class LoginHistory extends BaseEntity {

    private Long loginHistoryId;

    private Long userId;

    private String userCode;

    private Boolean isSuccess;

    private String securityCode;

    private Boolean isRenewToken;

    private LocalDateTime startedAt;

    private LocalDateTime endAt;

    private LocalDateTime expiredAt;

    private String sessionId;

    private Boolean isActivated;

    @Id
    @Column(name = "login_history_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getLoginHistoryId() {
        return loginHistoryId;
    }

    public void setLoginHistoryId(Long loginHistoryId) {
        this.loginHistoryId = loginHistoryId;
    }

    @Basic
    @Column(name = "user_id")
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Basic
    @Column(name = "user_code")
    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    @Basic
    @Column(name = "is_success")
    public Boolean getSuccess() {
        return isSuccess;
    }

    public void setSuccess(Boolean success) {
        isSuccess = success;
    }

    @Basic
    @Column(name = "security_code")
    public String getSecurityCode() {
        return securityCode;
    }

    public void setSecurityCode(String securityCode) {
        this.securityCode = securityCode;
    }

    @Basic
    @Column(name = "is_renew_token")
    public Boolean getRenewToken() {
        return isRenewToken;
    }

    public void setRenewToken(Boolean renewToken) {
        isRenewToken = renewToken;
    }

    @Basic
    @Column(name = "started_at")
    public LocalDateTime getStartedAt() {
        return startedAt;
    }

    public void setStartedAt(LocalDateTime startedAt) {
        this.startedAt = startedAt;
    }

    @Basic
    @Column(name = "end_at")
    public LocalDateTime getEndAt() {
        return endAt;
    }

    public void setEndAt(LocalDateTime endAt) {
        this.endAt = endAt;
    }

    @Basic
    @Column(name = "expired_at")
    public LocalDateTime getExpiredAt() {
        return expiredAt;
    }

    public void setExpiredAt(LocalDateTime expiredAt) {
        this.expiredAt = expiredAt;
    }

    @Basic
    @Column(name = "session_id")
    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    @Basic
    @Column(name = "is_activated")
    public Boolean getActivated() {
        return isActivated;
    }

    public void setActivated(Boolean activated) {
        isActivated = activated;
    }
}

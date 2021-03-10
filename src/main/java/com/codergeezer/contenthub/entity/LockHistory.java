package com.codergeezer.contenthub.entity;

import com.codergeezer.core.base.data.BaseEntity;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * @author haidv
 * @version 1.0
 */
@Entity
@Table(name = "tbl_lock_history")
public class LockHistory extends BaseEntity {

    private Long lockHistoryId;

    private Long userId;

    private String userCode;

    private Boolean isLock;

    private LocalDateTime lockTime;

    private String unlockCode;

    private LocalDateTime unlockTime;

    private LocalDateTime unlockCodeExpired;

    @Id
    @Column(name = "lock_history_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getLockHistoryId() {
        return lockHistoryId;
    }

    public void setLockHistoryId(Long lockHistoryId) {
        this.lockHistoryId = lockHistoryId;
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
    @Column(name = "is_lock")
    public Boolean getLock() {
        return isLock;
    }

    public void setLock(Boolean lock) {
        isLock = lock;
    }

    @Basic
    @Column(name = "lock_time")
    public LocalDateTime getLockTime() {
        return lockTime;
    }

    public void setLockTime(LocalDateTime lockTime) {
        this.lockTime = lockTime;
    }

    @Basic
    @Column(name = "unlock_code")
    public String getUnlockCode() {
        return unlockCode;
    }

    public void setUnlockCode(String unlockCode) {
        this.unlockCode = unlockCode;
    }

    @Basic
    @Column(name = "unlock_time")
    public LocalDateTime getUnlockTime() {
        return unlockTime;
    }

    public void setUnlockTime(LocalDateTime unlockTime) {
        this.unlockTime = unlockTime;
    }

    @Basic
    @Column(name = "unlock_code_expired")
    public LocalDateTime getUnlockCodeExpired() {
        return unlockCodeExpired;
    }

    public void setUnlockCodeExpired(LocalDateTime unlockCodeExpired) {
        this.unlockCodeExpired = unlockCodeExpired;
    }
}

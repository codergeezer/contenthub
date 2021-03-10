package com.codergeezer.contenthub.entity;

import com.codergeezer.core.base.data.LocalDateTimeConverter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "tbl_upgrade")
public class Upgrade {

    private Long upgradeId;

    private Long groupId;

    private String groupCode;

    private Long tierId;

    private String tierCode;

    private Long userId;

    private String userCode;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    private String transaction;

    @Id
    @Column(name = "upgrade_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getUpgradeId() {
        return upgradeId;
    }

    public void setUpgradeId(Long upgradeId) {
        this.upgradeId = upgradeId;
    }

    @Basic
    @Column(name = "group_id")
    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    @Basic
    @Column(name = "group_code")
    public String getGroupCode() {
        return groupCode;
    }

    public void setGroupCode(String groupCode) {
        this.groupCode = groupCode;
    }

    @Basic
    @Column(name = "tier_id")
    public Long getTierId() {
        return tierId;
    }

    public void setTierId(Long tierId) {
        this.tierId = tierId;
    }

    @Basic
    @Column(name = "tier_code")
    public String getTierCode() {
        return tierCode;
    }

    public void setTierCode(String tierCode) {
        this.tierCode = tierCode;
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
    @Column(name = "start_date")
    @Convert(converter = LocalDateTimeConverter.class)
    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    @Basic
    @Column(name = "end_date")
    @Convert(converter = LocalDateTimeConverter.class)
    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    @Basic
    @Column(name = "transaction")
    public String getTransaction() {
        return transaction;
    }

    public void setTransaction(String transaction) {
        this.transaction = transaction;
    }
}

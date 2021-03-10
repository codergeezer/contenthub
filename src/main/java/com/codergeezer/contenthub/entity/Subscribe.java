package com.codergeezer.contenthub.entity;

import com.codergeezer.core.base.data.BaseEntity;

import javax.persistence.*;

/**
 * @author haidv
 * @version 1.0
 */
@Entity
@Table(name = "tbl_subscribe")
public class Subscribe extends BaseEntity {

    private Long subscribeId;

    private Long groupId;

    private String groupCode;

    private Boolean subscribe;

    private Long tierId;

    private String tierCode;

    private Long userId;

    private String userCode;

    @Id
    @Column(name = "subscribe_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getSubscribeId() {
        return subscribeId;
    }

    public void setSubscribeId(Long subscribeId) {
        this.subscribeId = subscribeId;
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
    @Column(name = "is_subscribe")
    public Boolean getSubscribe() {
        return subscribe;
    }

    public void setSubscribe(Boolean subscribe) {
        this.subscribe = subscribe;
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
}

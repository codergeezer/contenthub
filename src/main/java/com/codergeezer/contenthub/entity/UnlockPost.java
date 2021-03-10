package com.codergeezer.contenthub.entity;

import com.codergeezer.core.base.data.BaseEntity;
import com.codergeezer.core.base.data.LocalDateTimeConverter;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * @author haidv
 * @version 1.0
 */
@Entity
@Table(name = "tbl_unlock_post")
public class UnlockPost extends BaseEntity {

    private Long unlockPostId;

    private Long userId;

    private String userCode;

    private Long postId;

    private Long groupId;

    private String groupCode;

    private Long cost;

    private LocalDateTime unlockTime;

    private String transaction;

    @Id
    @Column(name = "unlock_post_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getUnlockPostId() {
        return unlockPostId;
    }

    public void setUnlockPostId(Long unlockPostId) {
        this.unlockPostId = unlockPostId;
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
    @Column(name = "post_id")
    public Long getPostId() {
        return postId;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
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
    @Column(name = "cost")
    public Long getCost() {
        return cost;
    }

    public void setCost(Long cost) {
        this.cost = cost;
    }

    @Basic
    @Column(name = "unlock_time")
    @Convert(converter = LocalDateTimeConverter.class)
    public LocalDateTime getUnlockTime() {
        return unlockTime;
    }

    public void setUnlockTime(LocalDateTime unlockTime) {
        this.unlockTime = unlockTime;
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

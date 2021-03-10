package com.codergeezer.contenthub.entity;

import com.codergeezer.core.base.data.BaseEntity;

import javax.persistence.*;

/**
 * @author haidv
 * @version 1.0
 */
@Entity
@Table(name = "tbl_donate")
public class Donate extends BaseEntity {

    private Long donateId;

    private Long albumId;

    private Long postId;

    private Long cost;

    private Long groupId;

    private String groupCode;

    private Long userId;

    private String userCode;

    private String transaction;

    @Id
    @Column(name = "donate_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getDonateId() {
        return donateId;
    }

    public void setDonateId(Long donateId) {
        this.donateId = donateId;
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
    @Column(name = "album_id")
    public Long getAlbumId() {
        return albumId;
    }

    public void setAlbumId(Long albumId) {
        this.albumId = albumId;
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
    @Column(name = "cost")
    public Long getCost() {
        return cost;
    }

    public void setCost(Long cost) {
        this.cost = cost;
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
    @Column(name = "user_code")
    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
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

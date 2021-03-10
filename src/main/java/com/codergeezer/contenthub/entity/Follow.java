package com.codergeezer.contenthub.entity;

import com.codergeezer.core.base.data.BaseEntity;

import javax.persistence.*;

/**
 * @author haidv
 * @version 1.0
 */
@Entity
@Table(name = "tbl_follow")
public class Follow extends BaseEntity {

    private Long followId;

    private Long userId;

    private Long albumId;

    private Boolean isFollow;

    @Id
    @Column(name = "follow_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getFollowId() {
        return followId;
    }

    public void setFollowId(Long followId) {
        this.followId = followId;
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
    @Column(name = "is_follow")
    public Boolean getFollow() {
        return isFollow;
    }

    public void setFollow(Boolean follow) {
        isFollow = follow;
    }
}

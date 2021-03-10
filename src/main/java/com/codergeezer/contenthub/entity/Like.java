package com.codergeezer.contenthub.entity;

import com.codergeezer.core.base.data.BaseEntity;
import com.codergeezer.core.base.data.LocalDateTimeConverter;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * @author haidv
 * @version 1.0
 */
@Entity @Table(name = "tbl_like")
public class Like extends BaseEntity {

    private Long likeId;

    private Long userId;

    private Long postId;

    private Boolean isLike;

    private LocalDateTime likeTime;

    private LocalDateTime unlikeTime;

    @Id
    @Column(name = "like_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getLikeId() {
        return likeId;
    }

    public void setLikeId(Long likeId) {
        this.likeId = likeId;
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
    @Column(name = "post_id")
    public Long getPostId() {
        return postId;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }

    @Basic
    @Column(name = "is_like")
    public Boolean getLike() {
        return isLike;
    }

    public void setLike(Boolean like) {
        isLike = like;
    }

    @Basic
    @Column(name = "like_time")
    @Convert(converter = LocalDateTimeConverter.class)
    public LocalDateTime getLikeTime() {
        return likeTime;
    }

    public void setLikeTime(LocalDateTime likeTime) {
        this.likeTime = likeTime;
    }

    @Basic
    @Column(name = "unlike_time")
    @Convert(converter = LocalDateTimeConverter.class)
    public LocalDateTime getUnlikeTime() {
        return unlikeTime;
    }

    public void setUnlikeTime(LocalDateTime unlikeTime) {
        this.unlikeTime = unlikeTime;
    }
}

package com.codergeezer.contenthub.entity;

import com.codergeezer.core.base.data.BaseEntity;

import javax.persistence.*;

/**
 * @author haidv
 * @version 1.0
 */
@Entity
@Table(name = "tbl_comment")
public class Comment extends BaseEntity {

    private Long commentId;

    private Long parentCommentId;

    private String message;

    private Long postId;

    private Long userId;

    @Id
    @Column(name = "comment_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getCommentId() {
        return commentId;
    }

    public void setCommentId(Long commentId) {
        this.commentId = commentId;
    }


    @Basic
    @Column(name = "parent_comment_id")
    public Long getParentCommentId() {
        return parentCommentId;
    }

    public void setParentCommentId(Long parentCommentId) {
        this.parentCommentId = parentCommentId;
    }

    @Basic
    @Column(name = "message")
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
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
    @Column(name = "user_id")
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}

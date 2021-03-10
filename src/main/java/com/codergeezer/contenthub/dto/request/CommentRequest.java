package com.codergeezer.contenthub.dto.request;

/**
 * @author haidv
 * @version 1.0
 */
public class CommentRequest {

    private Long parentCommentId;

    private String message;

    public Long getParentCommentId() {
        return parentCommentId;
    }

    public void setParentCommentId(Long parentCommentId) {
        this.parentCommentId = parentCommentId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

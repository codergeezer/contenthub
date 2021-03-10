package com.codergeezer.contenthub.dto.response;

import java.time.LocalDateTime;

/**
 * @author haidv
 * @version 1.0
 */
public class PostResponse {

    private Long postId;

    private String name;

    private Long albumId;

    private String content;

    private String tags;

    private Long totalRead;

    private Long totalComment;

    private Long totalDonate;

    private Long totalLike;

    private Long groupId;

    private String groupCode;

    private Boolean isComment;

    private LocalDateTime publicDate;

    public Long getPostId() {
        return postId;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getAlbumId() {
        return albumId;
    }

    public void setAlbumId(Long albumId) {
        this.albumId = albumId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public Long getTotalRead() {
        return totalRead;
    }

    public void setTotalRead(Long totalRead) {
        this.totalRead = totalRead;
    }

    public Long getTotalComment() {
        return totalComment;
    }

    public void setTotalComment(Long totalComment) {
        this.totalComment = totalComment;
    }

    public Long getTotalDonate() {
        return totalDonate;
    }

    public void setTotalDonate(Long totalDonate) {
        this.totalDonate = totalDonate;
    }

    public Long getTotalLike() {
        return totalLike;
    }

    public void setTotalLike(Long totalLike) {
        this.totalLike = totalLike;
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public String getGroupCode() {
        return groupCode;
    }

    public void setGroupCode(String groupCode) {
        this.groupCode = groupCode;
    }

    public Boolean getComment() {
        return isComment;
    }

    public void setComment(Boolean comment) {
        isComment = comment;
    }

    public LocalDateTime getPublicDate() {
        return publicDate;
    }

    public void setPublicDate(LocalDateTime publicDate) {
        this.publicDate = publicDate;
    }
}

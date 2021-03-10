package com.codergeezer.contenthub.dto.response;

import java.time.LocalDateTime;

/**
 * @author haidv
 * @version 1.0
 */
public class AlbumResponse {

    private Long albumId;

    private String name;

    private String description;

    private String tags;

    private Long totalRead;

    private Long totalComment;

    private Long totalPost;

    private Long totalFollow;

    private Long totalLike;

    private Long totalDonate;

    private LocalDateTime lastPublicPost;

    private String author;

    private String coverImage;

    private Long status;

    private Long price;

    private Long tierId;

    private String tierCode;

    private Long ownerId;

    private String ownerCode;

    private Long groupId;

    private String groupCode;

    public Long getAlbumId() {
        return albumId;
    }

    public void setAlbumId(Long albumId) {
        this.albumId = albumId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public Long getTotalPost() {
        return totalPost;
    }

    public void setTotalPost(Long totalPost) {
        this.totalPost = totalPost;
    }

    public Long getTotalFollow() {
        return totalFollow;
    }

    public void setTotalFollow(Long totalFollow) {
        this.totalFollow = totalFollow;
    }

    public Long getTotalLike() {
        return totalLike;
    }

    public void setTotalLike(Long totalLike) {
        this.totalLike = totalLike;
    }

    public Long getTotalDonate() {
        return totalDonate;
    }

    public void setTotalDonate(Long totalDonate) {
        this.totalDonate = totalDonate;
    }

    public LocalDateTime getLastPublicPost() {
        return lastPublicPost;
    }

    public void setLastPublicPost(LocalDateTime lastPublicPost) {
        this.lastPublicPost = lastPublicPost;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getCoverImage() {
        return coverImage;
    }

    public void setCoverImage(String coverImage) {
        this.coverImage = coverImage;
    }

    public Long getStatus() {
        return status;
    }

    public void setStatus(Long status) {
        this.status = status;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public Long getTierId() {
        return tierId;
    }

    public void setTierId(Long tierId) {
        this.tierId = tierId;
    }

    public String getTierCode() {
        return tierCode;
    }

    public void setTierCode(String tierCode) {
        this.tierCode = tierCode;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }

    public String getOwnerCode() {
        return ownerCode;
    }

    public void setOwnerCode(String ownerCode) {
        this.ownerCode = ownerCode;
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
}

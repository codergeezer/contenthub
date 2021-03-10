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
@Table(name = "tbl_album")
public class Album extends BaseEntity {

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

    @Id
    @Column(name = "album_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getAlbumId() {
        return albumId;
    }

    public void setAlbumId(Long albumId) {
        this.albumId = albumId;
    }

    @Basic
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "description")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Basic
    @Column(name = "tags")
    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    @Basic
    @Column(name = "total_read")
    public Long getTotalRead() {
        return totalRead;
    }

    public void setTotalRead(Long totalRead) {
        this.totalRead = totalRead;
    }

    @Basic
    @Column(name = "total_comment")
    public Long getTotalComment() {
        return totalComment;
    }

    public void setTotalComment(Long totalComment) {
        this.totalComment = totalComment;
    }

    @Basic
    @Column(name = "total_post")
    public Long getTotalPost() {
        return totalPost;
    }

    public void setTotalPost(Long totalPost) {
        this.totalPost = totalPost;
    }

    @Basic
    @Column(name = "total_follow")
    public Long getTotalFollow() {
        return totalFollow;
    }

    public void setTotalFollow(Long totalFollow) {
        this.totalFollow = totalFollow;
    }

    @Basic
    @Column(name = "total_like")
    public Long getTotalLike() {
        return totalLike;
    }

    public void setTotalLike(Long totalLike) {
        this.totalLike = totalLike;
    }

    @Basic
    @Column(name = "total_donate")
    public Long getTotalDonate() {
        return totalDonate;
    }

    public void setTotalDonate(Long totalDonate) {
        this.totalDonate = totalDonate;
    }

    @Basic
    @Column(name = "last_upload_chap")
    @Convert(converter = LocalDateTimeConverter.class)
    public LocalDateTime getLastPublicPost() {
        return lastPublicPost;
    }

    public void setLastPublicPost(LocalDateTime lastPublicPost) {
        this.lastPublicPost = lastPublicPost;
    }

    @Basic
    @Column(name = "author")
    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    @Basic
    @Column(name = "cover_image")
    public String getCoverImage() {
        return coverImage;
    }

    public void setCoverImage(String coverImage) {
        this.coverImage = coverImage;
    }

    @Basic
    @Column(name = "status")
    public Long getStatus() {
        return status;
    }

    public void setStatus(Long status) {
        this.status = status;
    }

    @Basic
    @Column(name = "price")
    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
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
    @Column(name = "owner_id")
    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }

    @Basic
    @Column(name = "owner_code")
    public String getOwnerCode() {
        return ownerCode;
    }

    public void setOwnerCode(String ownerCode) {
        this.ownerCode = ownerCode;
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
}

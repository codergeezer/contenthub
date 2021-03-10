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
@Table(name = "tbl_post")
public class Post extends BaseEntity {

    private Long postId;

    private String name;

    private Long albumId;

    private String userCode;

    private String content;

    private String tags;

    private Long totalRead = 0L;

    private Long totalComment = 0L;

    private Long totalDonate = 0L;

    private Long totalLike = 0L;

    private Long groupId;

    private String groupCode;

    private Boolean isComment;

    private LocalDateTime publicDate;

    private Boolean isLock;

    private Long unlockFee;

    private LocalDateTime lockTime;

    private Boolean imageLinking;

    private Long tierId;

    @Id
    @Column(name = "post_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getPostId() {
        return postId;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
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
    @Column(name = "album_id")
    public Long getAlbumId() {
        return albumId;
    }

    public void setAlbumId(Long albumId) {
        this.albumId = albumId;
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
    @Column(name = "content")
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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
    @Column(name = "total_donate")
    public Long getTotalDonate() {
        return totalDonate;
    }

    public void setTotalDonate(Long totalDonate) {
        this.totalDonate = totalDonate;
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
    @Column(name = "is_comment")
    public Boolean getComment() {
        return isComment;
    }

    public void setComment(Boolean comment) {
        isComment = comment;
    }

    @Basic
    @Column(name = "public_date")
    @Convert(converter = LocalDateTimeConverter.class)
    public LocalDateTime getPublicDate() {
        return publicDate;
    }

    public void setPublicDate(LocalDateTime publicDate) {
        this.publicDate = publicDate;
    }

    @Basic
    @Column(name = "is_lock")
    public Boolean getLock() {
        return isLock;
    }

    public void setLock(Boolean lock) {
        isLock = lock;
    }

    @Basic
    @Column(name = "unlock_fee")
    public Long getUnlockFee() {
        return unlockFee;
    }

    public void setUnlockFee(Long unlockFee) {
        this.unlockFee = unlockFee;
    }

    @Basic
    @Column(name = "lock_time")
    @Convert(converter = LocalDateTimeConverter.class)
    public LocalDateTime getLockTime() {
        return lockTime;
    }

    public void setLockTime(LocalDateTime lockTime) {
        this.lockTime = lockTime;
    }

    @Basic
    @Column(name = "image_linking")
    public Boolean getImageLinking() {
        return imageLinking;
    }

    public void setImageLinking(Boolean imageLinking) {
        this.imageLinking = imageLinking;
    }

    @Basic
    @Column(name = "tier_id")
    public Long getTierId() {
        return tierId;
    }

    public void setTierId(Long tierId) {
        this.tierId = tierId;
    }
}

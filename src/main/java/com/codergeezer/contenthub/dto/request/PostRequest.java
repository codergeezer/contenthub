package com.codergeezer.contenthub.dto.request;

import java.time.LocalDateTime;

/**
 * @author haidv
 * @version 1.0
 */
public class PostRequest {

    private String name;

    private Long albumId;

    private String content;

    private String tags;

    private Boolean isComment;

    private LocalDateTime publicDate;

    private boolean isLock;

    private Long unlockFee;

    private LocalDateTime lockTime;

    private boolean imageLinking;

    private Long tierId;

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

    public boolean isLock() {
        return isLock;
    }

    public void setLock(boolean lock) {
        isLock = lock;
    }

    public Long isUnlockFee() {
        return unlockFee;
    }

    public void setUnlockFee(Long unlockFee) {
        this.unlockFee = unlockFee;
    }

    public LocalDateTime getLockTime() {
        return lockTime;
    }

    public void setLockTime(LocalDateTime lockTime) {
        this.lockTime = lockTime;
    }

    public boolean isImageLinking() {
        return imageLinking;
    }

    public void setImageLinking(boolean imageLinking) {
        this.imageLinking = imageLinking;
    }

    public Long getTierId() {
        return tierId;
    }

    public void setTierId(Long tierId) {
        this.tierId = tierId;
    }
}

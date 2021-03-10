package com.codergeezer.contenthub.enums;

import com.codergeezer.core.base.exception.BaseErrorCode;
import org.springframework.http.HttpStatus;

/**
 * @author haidv
 * @version 1.0
 */
public enum ErrorCode implements BaseErrorCode {

    EMAIL_EXISTED(100, "email.existed", HttpStatus.BAD_REQUEST),

    USERNAME_EXISTED(101, "username.existed", HttpStatus.BAD_REQUEST),

    USER_NOT_FOUND(102, "user.not.found", HttpStatus.NOT_FOUND),

    PASSWORD_NOT_MATCH(103, "password.not.match", HttpStatus.BAD_REQUEST),

    GRANT_NOT_SUPPORT(104, "grant.not.support", HttpStatus.BAD_REQUEST),

    EMAIL_TEMPLATE_EXISTED(105, "email.template.existed", HttpStatus.BAD_REQUEST),

    EMAIL_TEMPLATE_NOT_EXISTED(106, "email.template.not.existed", HttpStatus.BAD_REQUEST),

    TIER_NOT_FOUND(107, "tier.not.found", HttpStatus.NOT_FOUND),

    TIER_INVALID(108, "tier.invalid", HttpStatus.BAD_REQUEST),

    ALBUM_NOT_FOUND(109, "album.not.found", HttpStatus.NOT_FOUND),

    POST_NOT_FOUND(110, "post.not.found", HttpStatus.NOT_FOUND),

    COMMENT_NOT_FOUND(111, "comment.not.found", HttpStatus.NOT_FOUND),

    PARENT_COMMENT_NOT_DELETE(112, "parent.comment.not.delete", HttpStatus.BAD_REQUEST),

    GROUP_NOT_FOUND(113, "group.not.found", HttpStatus.NOT_FOUND),

    UNSUBSCRIBED_TO_GROUP(114, "unsubscribed.to.group", HttpStatus.BAD_REQUEST),

    UPGRADE_FAILED(115, "upgrade.failed", HttpStatus.BAD_REQUEST),

    INSUFFICIENT_BALANCE(116, "insufficient.balance", HttpStatus.BAD_REQUEST),

    POST_NOT_LOCK(117, "post.not.lock", HttpStatus.NOT_FOUND),

    USER_HAS_NOT_GROUP(118, "user.has.not.group", HttpStatus.FORBIDDEN),

    USER_HAS_GROUP(119, "user.has.group", HttpStatus.BAD_REQUEST),

    USER_HAS_LOCKED(120, "user.has.locked", HttpStatus.UNAUTHORIZED),

    NOT_FOUND_USER_LOCK_INFO(121, "not.found.user.lock.info", HttpStatus.NOT_FOUND),

    NOT_FOUND_USER_PRIMARY_EMAIL(122, "not.found.user.primary.email", HttpStatus.NOT_FOUND),

    CONFIRM_CODE_NOT_MATCH(123, "confirm.code.not.match", HttpStatus.UNAUTHORIZED),

    CONFIRM_CODE_EXPIRED(124, "confirm.code.expired", HttpStatus.UNAUTHORIZED),

    SUBSCRIBED_TO_GROUP(125, "subscribed.to.group", HttpStatus.BAD_REQUEST);

    private final int code;

    private final String messageCode;

    private final HttpStatus httpStatus;

    ErrorCode(int code, String messageCode, HttpStatus httpStatus) {
        this.code = code;
        this.messageCode = messageCode;
        this.httpStatus = httpStatus;
    }

    @Override
    public String getMessageCode() {
        return messageCode;
    }

    @Override
    public int getCode() {
        return code;
    }

    @Override
    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}

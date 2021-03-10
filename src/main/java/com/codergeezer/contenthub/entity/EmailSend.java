package com.codergeezer.contenthub.entity;

import com.codergeezer.core.base.data.LocalDateTimeConverter;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * @author haidv
 * @version 1.0
 */
@Entity
@Table(name = "tbl_email_send")
public class EmailSend {

    private Long emailSendId;

    private Long emailId;

    private String emailFrom;

    private String emailTo;

    private String emailCc;

    private String emailBcc;

    private Short priority;

    private LocalDateTime createdDate;

    private LocalDateTime sendTime;

    private String attachmentPath;

    private String subject;

    private Short countError;

    private String content;

    private String uuId;

    @Id
    @Column(name = "email_send_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getEmailSendId() {
        return emailSendId;
    }

    public void setEmailSendId(Long emailSendId) {
        this.emailSendId = emailSendId;
    }

    @Basic
    @Column(name = "email_id")
    public Long getEmailId() {
        return emailId;
    }

    public void setEmailId(Long emailId) {
        this.emailId = emailId;
    }

    @Basic
    @Column(name = "email_from")
    public String getEmailFrom() {
        return emailFrom;
    }

    public void setEmailFrom(String emailFrom) {
        this.emailFrom = emailFrom;
    }

    @Basic
    @Column(name = "email_to")
    public String getEmailTo() {
        return emailTo;
    }

    public void setEmailTo(String emailTo) {
        this.emailTo = emailTo;
    }

    @Basic
    @Column(name = "email_cc")
    public String getEmailCc() {
        return emailCc;
    }

    public void setEmailCc(String emailCc) {
        this.emailCc = emailCc;
    }

    @Basic
    @Column(name = "email_bcc")
    public String getEmailBcc() {
        return emailBcc;
    }

    public void setEmailBcc(String emailBcc) {
        this.emailBcc = emailBcc;
    }

    @Basic
    @Column(name = "priority")
    public Short getPriority() {
        return priority;
    }

    public void setPriority(Short priority) {
        this.priority = priority;
    }

    @Basic
    @Column(name = "created_date")
    @Convert(converter = LocalDateTimeConverter.class)
    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    @Basic
    @Column(name = "send_time")
    @Convert(converter = LocalDateTimeConverter.class)
    public LocalDateTime getSendTime() {
        return sendTime;
    }

    public void setSendTime(LocalDateTime sendTime) {
        this.sendTime = sendTime;
    }

    @Basic
    @Column(name = "attachment_path")
    public String getAttachmentPath() {
        return attachmentPath;
    }

    public void setAttachmentPath(String attachmentPath) {
        this.attachmentPath = attachmentPath;
    }

    @Basic
    @Column(name = "subject")
    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    @Basic
    @Column(name = "count_error")
    public Short getCountError() {
        return countError;
    }

    public void setCountError(Short countError) {
        this.countError = countError;
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
    @Column(name = "uuid")
    public String getUuId() {
        return uuId;
    }

    public void setUuId(String uuId) {
        this.uuId = uuId;
    }
}

package com.codergeezer.contenthub.entity;

import com.codergeezer.core.base.data.BaseEntity;

import javax.persistence.*;

/**
 * @author haidv
 * @version 1.0
 */
@Entity
@Table(name = "tbl_credential")
public class Credential extends BaseEntity {

    private Long credentialId;

    private Long userId;

    private String userCode;

    private String userName;

    private String password;

    private String roles;

    private Short totalFailed;

    @Id
    @Column(name = "credential_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getCredentialId() {
        return credentialId;
    }

    public void setCredentialId(Long credentialId) {
        this.credentialId = credentialId;
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
    @Column(name = "user_code")
    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    @Basic
    @Column(name = "user_name")
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Basic
    @Column(name = "password")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Basic
    @Column(name = "roles")
    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }

    @Basic
    @Column(name = "total_failed")
    public Short getTotalFailed() {
        return totalFailed;
    }

    public void setTotalFailed(Short totalFailed) {
        this.totalFailed = totalFailed;
    }
}

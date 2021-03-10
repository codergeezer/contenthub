package com.codergeezer.contenthub.entity;

import com.codergeezer.core.base.data.BaseEntity;
import com.codergeezer.core.base.data.LocalDateConverter;

import javax.persistence.*;
import java.time.LocalDate;

/**
 * @author haidv
 * @version 1.0
 */
@Entity
@Table(name = "tbl_user")
public class User extends BaseEntity {

    private Long userId;

    private String code;

    private String fullName;

    private String firstName;

    private String lastName;

    private LocalDate birthday;

    private Long gender;

    private String address;

    private Double money = 0d;

    private String avatar;

    private boolean belongGroup;

    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Basic
    @Column(name = "code")
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Basic
    @Column(name = "full_name")
    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    @Basic
    @Column(name = "first_name")
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Basic
    @Column(name = "last_name")
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Basic
    @Column(name = "birthday")
    @Convert(converter = LocalDateConverter.class)
    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    @Basic
    @Column(name = "gender")
    public Long getGender() {
        return gender;
    }

    public void setGender(Long gender) {
        this.gender = gender;
    }

    @Basic
    @Column(name = "address")
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Basic
    @Column(name = "money")
    public Double getMoney() {
        return money;
    }

    public void setMoney(Double money) {
        this.money = money;
    }

    @Basic
    @Column(name = "avatar")
    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    @Basic
    @Column(name = "belong_group")
    public boolean isBelongGroup() {
        return belongGroup;
    }

    public void setBelongGroup(boolean belongGroup) {
        this.belongGroup = belongGroup;
    }
}

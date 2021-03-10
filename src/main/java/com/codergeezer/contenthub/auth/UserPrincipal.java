package com.codergeezer.contenthub.auth;

import com.codergeezer.contenthub.entity.Credential;
import com.codergeezer.contenthub.entity.Group;
import com.codergeezer.contenthub.entity.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author haidv
 * @version 1.0
 */
public class UserPrincipal implements OAuth2User, UserDetails {

    private final Long userId;

    private final String code;

    private final String fullName;

    private final String firstName;

    private final String lastName;

    private final String userName;

    private final String password;

    private final String roles;

    private final Collection<? extends GrantedAuthority> authorities;

    private Long groupId;

    private String groupCode;

    private String groupName;

    private String otherName;

    private Long adminId;

    private String adminCode;

    private Map<String, Object> attributes;

    public UserPrincipal(User user, Credential credential, Group group, Map<String, Object> attributes) {
        this.authorities = Arrays.stream(credential.getRoles().split(";"))
                                 .map(SimpleGrantedAuthority::new)
                                 .collect(Collectors.toList());
        this.attributes = attributes;
        this.userId = user.getUserId();
        this.code = user.getCode();
        this.fullName = user.getFullName();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.userName = credential.getUserName();
        this.password = credential.getPassword();
        this.roles = credential.getRoles();
        if (group != null) {
            this.groupId = group.getGroupId();
            this.groupCode = group.getCode();
            this.groupName = group.getGroupName();
            this.otherName = group.getOtherName();
            this.adminId = group.getAdminId();
            this.adminCode = group.getAdminCode();
        }
    }

    public UserPrincipal(User user, Credential credential, Group group) {
        this.authorities = Arrays.stream(credential.getRoles().split(";"))
                                 .map(SimpleGrantedAuthority::new)
                                 .collect(Collectors.toList());
        this.userId = user.getUserId();
        this.code = user.getCode();
        this.fullName = user.getFullName();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.userName = credential.getUserName();
        this.password = credential.getPassword();
        this.roles = credential.getRoles();
        if (group != null) {
            this.groupId = group.getGroupId();
            this.groupCode = group.getCode();
            this.groupName = group.getGroupName();
            this.otherName = group.getOtherName();
            this.adminId = group.getAdminId();
            this.adminCode = group.getAdminCode();
        }
    }

    @JsonIgnore
    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return userName;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @JsonIgnore
    @Override
    public String getName() {
        return fullName;
    }

    public Long getUserId() {
        return userId;
    }

    public String getCode() {
        return code;
    }

    public String getFullName() {
        return fullName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    @JsonIgnore
    public String getUserName() {
        return userName;
    }

    public String getRoles() {
        return roles;
    }

    public Long getGroupId() {
        return groupId;
    }

    public String getGroupCode() {
        return groupCode;
    }

    public String getGroupName() {
        return groupName;
    }

    public String getOtherName() {
        return otherName;
    }

    public Long getAdminId() {
        return adminId;
    }

    public String getAdminCode() {
        return adminCode;
    }
}

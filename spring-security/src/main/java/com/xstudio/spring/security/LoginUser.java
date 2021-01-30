package com.xstudio.spring.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;
import java.util.Set;

/**
 * 用户详情
 *
 * @author xiaobiao
 * @version 1.0.0
 * @date 2020/12/01
 */
public class LoginUser<T> extends User {

    /**
     * 权限
     */
    private Set<GrantedAuthority> authorities;
    /**
     * 第三方token
     */
    private String token;
    /**
     * 用户详细信息
     */
    @SuppressWarnings("all")
    private T details;
    /**
     * 用户ID
     */
    private String userId;

    /**
     * 用户昵称
     */
    private String userNickname;

    public LoginUser(String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
    }

    public LoginUser(String userId, String userNickname, String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
        this.userId = userId;
        this.userNickname = userNickname;
    }

    public LoginUser(String username, String password, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
    }

    @Override
    public Set<GrantedAuthority> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Set<GrantedAuthority> authorities) {
        this.authorities = authorities;
    }

    @Override
    public boolean equals(Object rhs) {
        return super.equals(rhs);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public T getDetails() {
        return details;
    }

    public void setDetails(T details) {
        this.details = details;
    }

    public String getUserId() {
        return userId;
    }

    /**
     * 获取 userNickname.
     *
     * @return userNickname 值
     */
    public String getUserNickname() {
        return userNickname;
    }
}

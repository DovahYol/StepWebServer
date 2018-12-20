package com.step.webServer.security;

import com.step.webServer.util.ResponseError;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class UserPrincipal implements UserDetails {
    private final String username;
    private String password;
    private final Set<GrantedAuthority> authorities;
    private final boolean accountNonExpired;
    private final boolean accountNonLocked;
    private final boolean credentialsNonExpired;
    private final boolean enabled;
    private Integer userId;
    private ResponseError responseError;

    public UserPrincipal(String username, String password,
                         Collection<? extends GrantedAuthority> authorities) {
        this(username, password, authorities,
                true, true, true,
        true, null, null);
    }

    public UserPrincipal(String username, ResponseError responseError) {
        this(username, null, new ArrayList<>(),
                true, true, true,
                true, null, responseError);
    }

    private UserPrincipal(String username, String password, Collection<? extends GrantedAuthority> authorities,
                          boolean accountNonExpired, boolean accountNonLocked, boolean credentialsNonExpired,
                          boolean enabled, Integer userId, ResponseError responseError) {
        this.username = username;
        this.password = password;
        if (authorities != null) {
            this.authorities = new HashSet<>();
            this.authorities.addAll(authorities);
        }else {
            this.authorities = null;
        }
        this.accountNonExpired = accountNonExpired;
        this.accountNonLocked = accountNonLocked;
        this.credentialsNonExpired = credentialsNonExpired;
        this.enabled = enabled;
        this.userId = userId;
        this.responseError = responseError;

    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return accountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return credentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public ResponseError getResponseError() {
        return responseError;
    }

    public void setResponseError(ResponseError responseError) {
        this.responseError = responseError;
    }

    public boolean hasError() {
        return responseError == null;
    }
}

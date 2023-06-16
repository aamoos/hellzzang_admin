package com.hellzzangAdmin.common;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Collection;

/**
* @package : com.hellzzangAdmin.common
* @name : CustomUserDetails.java
* @date : 2023-06-16 오후 11:52
* @author : 김재성
* @Description: idx principal에 넣을려고 custom으로 만듬
**/
public class CustomUserDetails implements UserDetails {

    private String username;
    private String password;
    private Collection<? extends GrantedAuthority> authorities;

    private Long idx;

    public CustomUserDetails(String username, String password, Collection<? extends GrantedAuthority> authorities, Long idx) {
        this.username = username;
        this.password = password;
        this.authorities = authorities;
        this.idx = idx;
    }

    // Implement the UserDetails interface methods

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



    public Long getIdx() {
        return idx;
    }

    // Implement the remaining methods (e.g., isEnabled, isAccountNonExpired, etc.) as per your requirements
}

package com.kintai.web.security;

import org.springframework.security.core.authority.AuthorityUtils;

import com.kintai.business.domain.User;

public class LoginUserDetails extends org.springframework.security.core.userdetails.User{

    private String userId;
    
    public LoginUserDetails(User user) {
        super(user.getUserId(), user.getPassword(), AuthorityUtils.createAuthorityList("ROLE_USER"));
        this.userId = user.getUserId();
    }

    public String getUserId() {
        return userId;
    }

}


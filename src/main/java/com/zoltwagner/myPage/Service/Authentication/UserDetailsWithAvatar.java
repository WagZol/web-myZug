package com.zoltwagner.myPage.Service.Authentication;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

public class UserDetailsWithAvatar extends User {

    private String avatar;
    private static final long serialVersionUID = 1L;


    public UserDetailsWithAvatar(String username, String password, Collection<? extends GrantedAuthority> authorities,
                                 String avatar) {
        super(username, password, authorities);
        this.avatar=avatar;
    }

    public UserDetailsWithAvatar(String username, String password, boolean enabled, boolean accountNonExpired,
                                 boolean credentialsNonExpired, boolean accountNonLocked,
                                 Collection<? extends GrantedAuthority> authorities, String avatar) {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
        this.avatar=avatar;
    }

    public String getAvatar(){
        return this.avatar;
    }

    public void setAvatar(String avatar){
        this.avatar=avatar;
    }
}

package org.spacebank.co.payload;

import java.util.Collection;


import org.springframework.security.core.GrantedAuthority;

public class UserSummary {
    private Long id;
    private String username;
    private String name;
    private Collection<? extends GrantedAuthority> authorities;

    public UserSummary(Long id, String username, String name) {
        this.id = id;
        this.username = username;
        this.name = name;
        
    }
    public UserSummary(Long id, String username, String name, Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.username = username;
        this.name = name;
        this.authorities = authorities;
        
    }
    
	public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}
	public void setAuthorities(Collection<? extends GrantedAuthority> authorities) {
		this.authorities = authorities;
	}
    
}

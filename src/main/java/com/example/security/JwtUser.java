package com.example.security;

import java.util.Collection;
import java.util.Date;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;


public class JwtUser implements UserDetails {

	private static final long serialVersionUID = 1215456216545L;
	
	private final Long id;
    private final String username;
    private final String firstname;
    private final String lastname;
    private final String password;
    private final String email;
    //private final Collection<? extends GrantedAuthority> authorities;
    //private final Collection<?> phones;
    private final boolean enabled;
    private final Date lastPasswordResetDate;

    public JwtUser(
          Long id,
          String username,
          String firstname,
          String lastname,
          String email,
          String password, //Collection<? extends GrantedAuthority> authorities,
          //Collection<?> phones,
          boolean enabled,
          Date lastPasswordResetDate
    ) {
        this.id = id;
        this.username = username;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.password = password;
        //this.authorities = authorities;
        //this.phones = phones;
        this.enabled = enabled;
        this.lastPasswordResetDate = lastPasswordResetDate;
    }

   /* public Collection<?> getPhones() {
        return phones;
    }*/

    @JsonIgnore
    public Long getId() {
        return id;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public String getEmail() {
        return email;
    }

    @JsonIgnore
    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<? extends GrantedAuthority> authorities = null;
        return authorities;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    @JsonIgnore
    public Date getLastPasswordResetDate() {
        return lastPasswordResetDate;
    }
}

package com.example.jwtdemo.jwt;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Date;

/**
 * Created by tomoya at 2018/8/10
 */
public class JwtUser implements UserDetails {

  private Integer id;
  private String username;
  private String password;
  private String email;
  private Collection<? extends GrantedAuthority> authorities;
  private Date lastPasswordResetDate;

  public JwtUser(Integer id, String username, String password, String email, Collection<? extends GrantedAuthority> authorities, Date lastPasswordResetDate) {
    this.id = id;
    this.username = username;
    this.password = password;
    this.email = email;
    this.authorities = authorities;
    this.lastPasswordResetDate = lastPasswordResetDate;
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

  public Date getLastPasswordResetDate() {
    return lastPasswordResetDate;
  }
}

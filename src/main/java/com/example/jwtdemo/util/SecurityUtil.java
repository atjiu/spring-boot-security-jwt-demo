package com.example.jwtdemo.util;

import com.example.jwtdemo.jwt.JwtUser;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

/**
 * Created by tomoya at 2018/8/12
 */
@Component
public class SecurityUtil {

  public String getPrincipal() {
    Object obj = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    if (obj instanceof JwtUser) {
      return ((JwtUser) obj).getUsername();
    } else {
      return null;
    }
  }
}

package com.example.jwtdemo.jwt;

import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Collection;

/**
 * Created by tomoya at 2018/8/12
 */
@Component
public class JwtAccessDecisionManager implements AccessDecisionManager {

  @Override
  public void decide(Authentication authentication, Object object, Collection<ConfigAttribute> configAttributes) throws AccessDeniedException, InsufficientAuthenticationException {
    if (configAttributes == null || configAttributes.size() == 0) return;
    String needRole;
    for (ConfigAttribute configAttribute : configAttributes) {
      needRole = configAttribute.getAttribute();
      for (GrantedAuthority grantedAuthority : authentication.getAuthorities()) {
        if (needRole.equals(grantedAuthority.getAuthority())) return;
      }
    }
    throw new AccessDeniedException("没有权限");
  }

  @Override
  public boolean supports(ConfigAttribute attribute) {
    return true;
  }

  @Override
  public boolean supports(Class<?> clazz) {
    return true;
  }
}

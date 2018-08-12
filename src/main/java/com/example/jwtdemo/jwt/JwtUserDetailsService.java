package com.example.jwtdemo.jwt;

import com.example.jwtdemo.model.User;
import com.example.jwtdemo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Created by tomoya at 2018/8/10
 */
@Service
public class JwtUserDetailsService implements UserDetailsService {

  @Autowired
  private UserService userService;

  @Override
  public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
    User user = userService.findByUsername(s);
    if (user == null) {
      throw new UsernameNotFoundException(String.format("用户'%s'不存在", s));
    } else {
      return JwtUserFactory.create(user);
    }
  }
}

package com.example.jwtdemo.service;

import com.example.jwtdemo.jwt.JwtAuthenticationManager;
import com.example.jwtdemo.jwt.JwtUser;
import com.example.jwtdemo.jwt.JwtUserDetailsService;
import com.example.jwtdemo.model.Role;
import com.example.jwtdemo.model.User;
import com.example.jwtdemo.repository.UserRepository;
import com.example.jwtdemo.util.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Date;
import java.util.Optional;

/**
 * Created by tomoya at 2018/8/10
 */
@Service
@Transactional
public class UserService {

  private UserRepository userRepository;
  private JwtAuthenticationManager jwtAuthenticationManager;
  private JwtUserDetailsService jwtUserDetailsService;
  private JwtTokenUtil jwtTokenUtil;
  private RoleService roleService;

  @Value("${jwt.tokenHead}")
  private String tokenHead;

  @Autowired
  public UserService(
      UserRepository userRepository,
      JwtAuthenticationManager jwtAuthenticationManager,
      JwtUserDetailsService jwtUserDetailsService,
      RoleService roleService,
      JwtTokenUtil jwtTokenUtil) {
    this.userRepository = userRepository;
    this.jwtAuthenticationManager = jwtAuthenticationManager;
    this.jwtUserDetailsService = jwtUserDetailsService;
    this.roleService = roleService;
    this.jwtTokenUtil = jwtTokenUtil;
  }

  private void save(User user) {
    userRepository.save(user);
  }

  public User findByUsername(String username) {
    User user = new User();
    user.setUsername(username);
    Optional<User> user1 = userRepository.findOne(Example.of(user));
    return user1.orElse(null);
  }

  public Page<User> findAll(Integer pageNo, Integer pageSize) {
    Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
    return userRepository.findAll(pageable);
  }

  public void register(String username, String password, String email, Integer roleId) {
    Role role = roleService.findById(roleId);
    User user = new User();
    user.setUsername(username);
    user.setPassword(new BCryptPasswordEncoder().encode(password));
    user.setEmail(email);
    user.setRoles(Collections.singletonList(role));
    user.setInTime(new Date());
    user.setLastPasswordResetDate(new Date());
    this.save(user);
  }

  public String login(String username, String password) {
    User user = findByUsername(username);
    if (new BCryptPasswordEncoder().matches(password, user.getPassword())) {
      UsernamePasswordAuthenticationToken upToken = new UsernamePasswordAuthenticationToken(username, password);
      Authentication authentication = jwtAuthenticationManager.authenticate(upToken);
      SecurityContextHolder.getContext().setAuthentication(authentication);

      UserDetails userDetails = jwtUserDetailsService.loadUserByUsername(username);
      return jwtTokenUtil.generateToken(userDetails);
    }
    return null;
  }

  public String refresh(String oldToken) {
    String token = oldToken.substring(tokenHead.length());
    String username = jwtTokenUtil.getUsernameFromToken(token);
    JwtUser user = (JwtUser) jwtUserDetailsService.loadUserByUsername(username);
    if (jwtTokenUtil.canTokenBeRefreshed(token, user.getLastPasswordResetDate())) {
      return jwtTokenUtil.refreshToken(token);
    }
    return null;
  }
}

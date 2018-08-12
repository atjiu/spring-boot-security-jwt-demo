package com.example.jwtdemo.controller;

import com.example.jwtdemo.service.UserService;
import com.example.jwtdemo.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by tomoya at 2018/8/10
 */
@RestController
public class IndexController {

  @Autowired
  private UserService userService;
  @Value("${jwt.header}")
  private String tokenHeader;

  @PostMapping("/login")
  public Result login(String username, String password) {
    if (StringUtils.isEmpty(username)) return Result.error("用户名不能为空");
    if (StringUtils.isEmpty(password)) return Result.error("密码不能为空");
    String token = userService.login(username, password);
    if (token == null) {
      return Result.error("用户名或密码不正确");
    } else {
      return Result.success(token);
    }
  }

  @PostMapping("/register")
  public Result register(String username, String password) {
    if (StringUtils.isEmpty(username)) return Result.error("用户名不能为空");
    if (StringUtils.isEmpty(password)) return Result.error("密码不能为空");
    if (userService.findByUsername(username) != null) return Result.error("用户名已经被注册了");
    userService.register(username, password, username + "@qq.com", 2);
    String token = userService.login(username, password);
    if (token == null) {
      return Result.error("用户名或密码不正确");
    } else {
      return Result.success(token);
    }
  }

  @PostMapping("/refreshToken")
  public Result refreshToken(HttpServletRequest request) {
    String token = request.getHeader(tokenHeader);
    String refreshToken = userService.refresh(token);
    if (refreshToken == null) {
      return Result.error("非法请求");
    } else {
      return Result.success(refreshToken);
    }
  }

}

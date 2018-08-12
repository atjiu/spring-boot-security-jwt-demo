package com.example.jwtdemo.config;

import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.autoconfigure.web.servlet.error.BasicErrorController;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by tomoya at 2018/8/12
 */
@Component
public class JwtBaseErrorController extends BasicErrorController {

  public JwtBaseErrorController(ServerProperties serverProperties) {
    super(new DefaultErrorAttributes(), serverProperties.getError());
  }

  @Override
  public ResponseEntity<Map<String, Object>> error(HttpServletRequest request) {
    Map<String, Object> body = getErrorAttributes(request,
        isIncludeStackTrace(request, MediaType.ALL));
    HttpStatus status = getStatus(request);

    //输出自定义的Json格式
    Map<String, Object> map = new HashMap<>();
    map.put("code", status.value());
    map.put("desc", body.get("message"));
    return new ResponseEntity<>(map, status);
  }
}

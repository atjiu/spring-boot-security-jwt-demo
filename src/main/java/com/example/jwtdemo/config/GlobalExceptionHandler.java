package com.example.jwtdemo.config;

import com.example.jwtdemo.util.Result;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by tomoya at 2018/8/10
 */
@ControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(value = Exception.class)
  @ResponseBody
  public Result jsonErrorHandler(Exception e) {
    return Result.error(e.getMessage());
  }

}

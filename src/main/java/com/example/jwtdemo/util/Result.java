package com.example.jwtdemo.util;

import lombok.Data;

/**
 * Created by tomoya at 2018/8/10
 */
@Data
public class Result {

  private Integer code;
  private String desc;
  private Object detail;

  public Result() {
  }

  public Result(Integer code, String desc, Object detail) {
    this.code = code;
    this.desc = desc;
    this.detail = detail;
  }

  public static Result success() {
    return success(null);
  }

  public static Result success(Object obj) {
    return new Result(200, "SUCCESS", obj);
  }

  public static Result error() {
    return error(null);
  }

  public static Result error(String desc) {
    return new Result(201, desc, null);
  }
}

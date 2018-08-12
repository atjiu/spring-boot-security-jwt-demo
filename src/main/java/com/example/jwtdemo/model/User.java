package com.example.jwtdemo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * Created by tomoya at 2018/8/10
 */
@Data
@Entity
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;
  @Column(unique = true)
  private String username;
  @JsonIgnore
  private String password;
  @Column(unique = true)
  private String email;
  private Date inTime;
  private Date lastPasswordResetDate;
  //角色，逗号隔开
  @JsonIgnore
  @ManyToMany(fetch = FetchType.EAGER)
  private List<Role> roles;

}

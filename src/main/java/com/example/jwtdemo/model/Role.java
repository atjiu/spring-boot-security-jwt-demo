package com.example.jwtdemo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

/**
 * Created by tomoya at 2018/8/12
 */
@Data
@Entity
public class Role {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;
  private String name;
  @JsonIgnore
  @ManyToMany(mappedBy = "roles")
  private List<User> users;
  @JsonIgnore
  @ManyToMany(fetch = FetchType.EAGER)
  private List<Permission> permissions;

}

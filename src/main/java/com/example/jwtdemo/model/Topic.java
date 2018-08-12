package com.example.jwtdemo.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by tomoya at 2018/8/10
 */
@Data
@Entity
public class Topic {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;
  private String title;
  @Column(columnDefinition = "TEXT")
  private String content;
  private Date inTime;
  @ManyToOne
  private User user;

}

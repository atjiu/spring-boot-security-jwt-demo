package com.example.jwtdemo.service;

import com.example.jwtdemo.model.Role;
import com.example.jwtdemo.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by tomoya at 2018/8/12
 */
@Service
@Transactional
public class RoleService {

  @Autowired
  private RoleRepository roleRepository;

  public Role findById(Integer id) {
    return roleRepository.findById(id).orElse(null);
  }
}

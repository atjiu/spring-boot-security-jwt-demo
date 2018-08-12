package com.example.jwtdemo.repository;

import com.example.jwtdemo.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by tomoya at 2018/8/12
 */
public interface RoleRepository extends JpaRepository<Role, Integer> {
}

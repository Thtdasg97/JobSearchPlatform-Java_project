package com.example.springasm02.service;

import com.example.springasm02.entity.Role;

import java.util.List;

public interface RoleService {
    List<Role> findAllRoles();
    Role findRoleById(int theId);
}

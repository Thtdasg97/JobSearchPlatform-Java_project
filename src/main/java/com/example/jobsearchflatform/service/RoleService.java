package com.example.jobsearchflatform.service;

import com.example.jobsearchflatform.entity.Role;

import java.util.List;

public interface RoleService {
    List<Role> findAllRoles();
    Role findRoleById(int theId);
}

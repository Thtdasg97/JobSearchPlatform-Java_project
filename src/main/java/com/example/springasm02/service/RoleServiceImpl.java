package com.example.springasm02.service;

import com.example.springasm02.dao.RoleRepository;
import com.example.springasm02.entity.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoleServiceImpl implements RoleService{
    private RoleRepository roleRepository;
    @Autowired
    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }
    @Override
    public List<Role> findAllRoles() {
        return roleRepository.findAll();
    }

    @Override
    public Role findRoleById(int theId) {
        Optional<Role> result = roleRepository.findById(theId);

        Role theRole = null;

        if(result.isPresent()) {
            theRole = result.get();
        } else {
            throw new RuntimeException("Did not find role id - " + theId);
        }

        return theRole;
    }
}

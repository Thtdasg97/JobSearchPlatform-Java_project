package com.example.springasm02.dao;

import com.example.springasm02.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
    User findUserByEmail(String theEmail);
}

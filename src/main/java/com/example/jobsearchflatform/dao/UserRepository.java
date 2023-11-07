package com.example.jobsearchflatform.dao;

import com.example.jobsearchflatform.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
    User findUserByEmail(String theEmail);
}

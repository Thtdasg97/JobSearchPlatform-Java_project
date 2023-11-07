package com.example.jobsearchflatform.service;

import com.example.jobsearchflatform.entity.User;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface UserService {
    List<User> findAllUsers();
    User findUserById(int theId);
    void saveUser(User theUser);
    User findUserByEmail(String theEmail);
    User updateUser(User theUser);
    void saveFile(MultipartFile file, String logoPath) throws IOException;
    void saveCv(MultipartFile file, String cvPath) throws IOException;

}

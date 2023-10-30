package com.example.springasm02.service;

import com.example.springasm02.dao.UserRepository;
import com.example.springasm02.entity.Role;
import com.example.springasm02.entity.User;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{
    private UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> findAllUsers() {
        List<User> users = userRepository.findAll();
        return users;
    }

    @Override
    public User findUserById(int theId) {
        Optional<User> result = userRepository.findById(theId);

        User theUser = null;

        if(result.isPresent()) {
            theUser = result.get();
        } else {
            throw new RuntimeException("Did not find user id - " + theId);
        }
        return theUser;
    }

    @Override
    public void saveUser(User theUser) {
        userRepository.save(theUser);
    }

    @Override
    public User findUserByEmail(String theEmail) {
        return userRepository.findUserByEmail(theEmail);
    }

    @Override
    public User updateUser(User updatedUser) {
        // Find the existing user from the database using the user ID
        Optional<User> optionalExistingUser = userRepository.findById(updatedUser.getId());

        if (optionalExistingUser.isPresent()) {
            User existingUser = optionalExistingUser.get();

            // Update the properties of the existing user with the updated data
            existingUser.setEmail(updatedUser.getEmail());
            existingUser.setFullName(updatedUser.getFullName());
            existingUser.setAddress(updatedUser.getAddress());
            existingUser.setPhoneNumber(updatedUser.getPhoneNumber());
            existingUser.setDescription(updatedUser.getDescription());

            // Save the modified user back to the database
            return userRepository.save(existingUser);
        } else {
            // Handle the case where the user is not found in the database, if desired.
            // For example, you could throw an exception, log an error, or create a new user if applicable.
            // Here's an example of throwing an exception:
            throw new EntityNotFoundException("User with ID " + updatedUser.getId() + " not found");
        }
    }

    @Override
    public void saveFile(MultipartFile file, String logoPath) throws IOException {
        String imagesDirectory = "src/main/resources/static/";

        Path filePath = Paths.get(imagesDirectory, logoPath);
        file.transferTo(filePath);
    }

    @Override
    public void saveCv(MultipartFile file, String cvPath) throws IOException {
        String uploadsDirectory ="src/main/resources/static/assets/uploads";

        Path filePath = Paths.get(uploadsDirectory,cvPath);
        file.transferTo(filePath);

    }


}

package com.hamza.NewsApp.service;

import com.hamza.NewsApp.model.User;
import com.hamza.NewsApp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    // Constructor-based injection is recommended over field injection
    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    public User registerUser(User newUser) {
        // Check if the user already exists to prevent duplicate entries
        if (userRepository.findByEmail(newUser.getEmail()).isPresent()) {
            // Throw an exception or handle it according to your application's requirements
            throw new IllegalStateException("Email already in use");
        }
        newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));
        return userRepository.save(newUser);
    }

    public User authenticateUser(String email, String password) {
        return userRepository.findByEmail(email)
                .filter(user -> passwordEncoder.matches(password, user.getPassword()))
                .orElse(null); // Consider handling the case when the user is not found or password does not match
    }
}

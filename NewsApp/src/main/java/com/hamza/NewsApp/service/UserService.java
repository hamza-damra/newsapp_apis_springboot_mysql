package com.hamza.NewsApp.service;

import com.hamza.NewsApp.dto.UserDto;
import com.hamza.NewsApp.exception.EmailAlreadyUsedException;
import com.hamza.NewsApp.exception.AuthenticationException;
import com.hamza.NewsApp.model.User;
import com.hamza.NewsApp.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;

    @Autowired
    public UserService(UserRepository userRepository, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
        this.modelMapper = modelMapper;
    }

    public UserDto registerUser(UserDto newUserDto) {
        if (userRepository.findByEmail(newUserDto.getEmail()).isPresent()) {
            throw new EmailAlreadyUsedException("Email already in use");
        }
        User user = modelMapper.map(newUserDto, User.class);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User savedUser = userRepository.save(user);
        return modelMapper.map(savedUser, UserDto.class);
    }

    public UserDto authenticateUser(String email, String password) {
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            if (passwordEncoder.matches(password, user.getPassword())) {
                return modelMapper.map(user, UserDto.class);
            } else {
                throw new AuthenticationException("Incorrect password");
            }
        } else {
            throw new AuthenticationException("Email not found");
        }
    }
}

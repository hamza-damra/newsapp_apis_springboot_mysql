package com.hamza.NewsApp.controller;

import com.hamza.NewsApp.dto.UserDto;
import com.hamza.NewsApp.exception.AuthenticationException;
import com.hamza.NewsApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<UserDto> registerUser(@RequestBody @Valid UserDto userDto) {
            UserDto registered = userService.registerUser(userDto);
            return ResponseEntity.ok(registered);

    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestParam String email,@RequestParam String password) {
        UserDto userDto = userService.authenticateUser(email, password);
        return ResponseEntity.ok(userDto);
    }


}

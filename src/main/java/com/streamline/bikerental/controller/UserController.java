package com.streamline.bikerental.controller;

import com.streamline.bikerental.dto.UserDTO;
import com.streamline.bikerental.model.User;
import com.streamline.bikerental.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/users")

public class UserController {

    @Autowired
    private UserService userService;

// Register user
@PostMapping("/register")
public ResponseEntity<User> registerUser(@RequestBody UserDTO userDTO) {
    User registeredUser = userService.registerUser(userDTO);
    return ResponseEntity.ok(registeredUser);
}

    // Login user
    @PostMapping("/login")
    public ResponseEntity<User> loginUser(@RequestParam String email, @RequestParam String password) {
        User loggedInUser = userService.loginUser(email, password);
        return ResponseEntity.ok(loggedInUser);
    }

//    @GetMapping("/profile")
//    public ResponseEntity<User> getUserProfile(Authentication
//                                                       authentication) {
//        Long userId = (Long) authentication.getPrincipal();
//        User user = userService.getUserProfile(userId);
//        return ResponseEntity.ok(user);
//    }

// Get user profile
@GetMapping("/{userId}")
public ResponseEntity<User> getUserProfile(@PathVariable Long userId) {
    User user = userService.getUserProfile(userId);
    return ResponseEntity.ok(user);
}

//    @PutMapping("/profile")
//    public ResponseEntity<User> updateUserProfile(Authentication
//                                                          authentication, @RequestBody UserDTO userDTO) throws IOException {
//        Long userId = (Long) authentication.getPrincipal();
//        User user = userService.updateUserProfile(userId, userDTO);
//        return ResponseEntity.ok(user);
//    }

    @PutMapping("/update/{userId}")
    public ResponseEntity<User> updateUser(
            @PathVariable Long userId,
            @RequestPart("userDTO") UserDTO userDTO,
            @RequestPart(value = "profilePhoto", required = false) MultipartFile profilePhoto) throws IOException {
        User updatedUser = userService.updateUserProfile(userId, userDTO, profilePhoto);
        return ResponseEntity.ok(updatedUser);
    }
}

package com.streamline.bikerental.controller;

import com.streamline.bikerental.dto.JwtResponse;
import com.streamline.bikerental.dto.AdminDTO;
import com.streamline.bikerental.dto.UserDTO;
import com.streamline.bikerental.dto.LoginRequest;
import com.streamline.bikerental.model.User;
import com.streamline.bikerental.security.JwtTokenUtil;
import com.streamline.bikerental.security.JwtUserDetailsService;
import com.streamline.bikerental.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private JwtUserDetailsService userDetailsService;

    @Autowired
    private UserService userService;

    @PostMapping("/admin/register")
    public ResponseEntity<?> registerAdmin(@RequestBody AdminDTO adminDTO) {
        userService.registerAdmin(adminDTO);
        return ResponseEntity.ok("Admin registered successfully.");
    }

    @PostMapping("/admin/login")
    public ResponseEntity<?> loginAdmin(@RequestBody LoginRequest loginRequest) {
        User user = userService.loginUser(loginRequest.getUsername(), loginRequest.getPassword());
        // Generate JWT token here and return it
        return ResponseEntity.ok("Admin logged in successfully.");
    }

    // Edit admin profile endpoint
    @PutMapping("/admin/profile/{id}")
    public ResponseEntity<?> editAdminProfile(@PathVariable Long id, @RequestBody AdminDTO adminDTO) {
        User updatedAdmin = userService.editAdminProfile(id, adminDTO);
        return ResponseEntity.ok(updatedAdmin);
    }

    // View all users endpoint
    @GetMapping("/admin/users")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    // View a single user endpoint
    @GetMapping("/admin/users/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Long id) {
        User user = userService.getUserById(id);
        return ResponseEntity.ok(user);
    }

    // Delete user endpoint
    @DeleteMapping("/admin/users/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.ok("User deleted successfully.");
    }

//    // Edit user profile endpoint (working) but it updates if same email
//    @PutMapping("/admin/users/{id}")
//    public ResponseEntity<?> editUserProfile(@PathVariable Long id, @RequestBody UserDTO userDTO) {
//        User updatedUser = userService.editUserProfile(id, userDTO);
//        return ResponseEntity.ok(updatedUser);
//    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody UserDTO userDTO) {
        System.out.println(userDTO);
        User user = userService.registerUser(userDTO);
        UserDetails userDetails = userDetailsService.loadUserByUsername(user.getEmail());
        final String token = jwtTokenUtil.generateToken(userDetails);
        return ResponseEntity.ok(new JwtResponse(token));
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestParam String email, @RequestParam String password) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
        final UserDetails userDetails = userDetailsService.loadUserByUsername(email);
        final String token = jwtTokenUtil.generateToken(userDetails);
        return ResponseEntity.ok(new JwtResponse(token));
    }
}




package com.streamline.bikerental.service;

import com.streamline.bikerental.dto.AdminDTO;
import com.streamline.bikerental.dto.UserDTO;
import com.streamline.bikerental.exception.ResourceNotFoundException;
import com.streamline.bikerental.exception.UserAlreadyExistsException;
import com.streamline.bikerental.model.User;
import com.streamline.bikerental.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private static final String UPLOAD_DIR = "uploads/profile-photos";

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // Register admin
    public User registerAdmin(AdminDTO adminDTO) {
        Optional<User> existingUser = userRepository.findByUsername(adminDTO.getUsername());
        if (existingUser.isPresent()) {
            throw new UserAlreadyExistsException("User with username " + adminDTO.getUsername() + " already exists.");
        }
        User user = new User();
        user.setFirstName(adminDTO.getFirstName());
        user.setLastName(adminDTO.getLastName());
        user.setUsername(adminDTO.getUsername());
        user.setEmail(adminDTO.getEmail());
        user.setPassword(passwordEncoder.encode(adminDTO.getPassword()));
        user.setRole("ADMIN");
        return userRepository.save(user);
    }

    // Login admin
    public User loginAdmin(String username, String password) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with username: " + username));
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new ResourceNotFoundException("Invalid credentials.");
        }
        return user;
    }

    // Edit admin profile
    public User editAdminProfile(Long adminId, AdminDTO adminDTO) {
        User admin = userRepository.findById(adminId)
                .orElseThrow(() -> new ResourceNotFoundException("Admin not found with id: " + adminId));
        admin.setFirstName(adminDTO.getFirstName());
        admin.setLastName(adminDTO.getLastName());
        admin.setUsername(adminDTO.getUsername());
        admin.setEmail(adminDTO.getEmail());
        if (adminDTO.getPassword() != null && !adminDTO.getPassword().isEmpty()) {
            admin.setPassword(passwordEncoder.encode(adminDTO.getPassword()));
        }
        return userRepository.save(admin);
    }

    // Register user
    public User registerUser(UserDTO userDTO) {
        Optional<User> existingUser = userRepository.findByEmail(userDTO.getEmail());
        if (existingUser.isPresent()) {
            throw new UserAlreadyExistsException("User with email " + userDTO.getEmail() + " already exists.");
        }
        User user = new User();
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setContactNumber(userDTO.getContactNumber());
        user.setAddress(userDTO.getAddress());
        user.setEmail(userDTO.getEmail());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        user.setRole("CUSTOMER");
        return userRepository.save(user);
    }

    // Login user
    public User loginUser(String email, String password) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + email));
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new ResourceNotFoundException("Invalid credentials.");
        }
        return user;
    }

    // Get user profile
    public User getUserProfile(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));
    }

    // Update user profile
    public User updateUserProfile(Long userId, UserDTO userDTO, MultipartFile profilePhoto) throws IOException {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));

        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setContactNumber(userDTO.getContactNumber());
        user.setAddress(userDTO.getAddress());

        // Handle profile photo
        if (profilePhoto != null && !profilePhoto.isEmpty()) {
            String fileName = StringUtils.cleanPath(profilePhoto.getOriginalFilename());
            String filePath = Paths.get(UPLOAD_DIR, fileName).toString();
            Files.copy(profilePhoto.getInputStream(), Paths.get(filePath));
            user.setProfilePhoto(fileName);  // Save the filename in the database
        }

        return userRepository.save(user);
    }

    // Admin view all users
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // Admin view a single user
    public User getUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));
    }

    // Admin delete user
    public void deleteUser(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new ResourceNotFoundException("User not found with id: " + userId);
        }
        userRepository.deleteById(userId);
    }

    // Admin edit user profile
    public User editUserProfile(Long userId, UserDTO userDTO, MultipartFile profilePhoto) throws IOException {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setContactNumber(userDTO.getContactNumber());
        user.setAddress(userDTO.getAddress());
        user.setEmail(userDTO.getEmail());
        if (userDTO.getPassword() != null && !userDTO.getPassword().isEmpty()) {
            user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        }

        // Handle profile photo
        if (profilePhoto != null && !profilePhoto.isEmpty()) {
            String fileName = StringUtils.cleanPath(profilePhoto.getOriginalFilename());
            String filePath = Paths.get(UPLOAD_DIR, fileName).toString();
            Files.copy(profilePhoto.getInputStream(), Paths.get(filePath));
            user.setProfilePhoto(fileName);  // Save the filename in the database
        }

        return userRepository.save(user);
    }
}



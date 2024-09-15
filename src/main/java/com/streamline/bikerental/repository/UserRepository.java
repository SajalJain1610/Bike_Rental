package com.streamline.bikerental.repository;

import com.streamline.bikerental.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    long count();
    Optional<User> findByUsername(String username);
}
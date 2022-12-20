package com.techshop.userservice.repository;

import com.techshop.userservice.entity.User;
import com.techshop.userservice.entity.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VerificationTokenRepository extends JpaRepository<VerificationToken, Long> {
    Optional<VerificationToken> findByToken(String token);
    VerificationToken findByUser(User user);
}
package com.techshop.userservice.repository;


import com.techshop.userservice.entity.PasswordResetToken;
import com.techshop.userservice.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Long> {
        Optional<PasswordResetToken> findByToken(String token);
        PasswordResetToken findByUser(User user);
}

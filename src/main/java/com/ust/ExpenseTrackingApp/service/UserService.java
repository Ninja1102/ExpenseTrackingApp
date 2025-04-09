package com.ust.ExpenseTrackingApp.service;

import com.ust.ExpenseTrackingApp.dto.RegisterRequest;
import com.ust.ExpenseTrackingApp.model.PasswordResetToken;
import com.ust.ExpenseTrackingApp.model.User;
import com.ust.ExpenseTrackingApp.repository.PasswordResetTokenRepository;
import com.ust.ExpenseTrackingApp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordResetTokenRepository tokenRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // Register a new user
    public void registerUser(RegisterRequest registerRequest) {
        User user = new User();
        user.setEmail(registerRequest.getEmail());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setRoles(Collections.singleton("ROLE_USER"));
        userRepository.save(user);
    }


    // Create a password reset token for a user
    public String createPasswordResetTokenForUser(User user) {
        tokenRepository.findByUser(user).ifPresent(tokenRepository::delete);

        String token = UUID.randomUUID().toString();

        PasswordResetToken passwordResetToken = PasswordResetToken.builder()
                .token(token)
                .user(user)
                .expiryDate(Instant.now().plus(24, ChronoUnit.HOURS))
                .build();

        tokenRepository.save(passwordResetToken);
        return token;
    }


    // Validate password reset token
    public boolean validatePasswordResetToken(String token) {
        Optional<PasswordResetToken> passwordResetToken = tokenRepository.findByToken(token);
        return passwordResetToken.isPresent() && !passwordResetToken.get().isExpired();
    }

    // Get user by password reset token
    public Optional<User> getUserByPasswordResetToken(String token) {
        return tokenRepository.findByToken(token).map(PasswordResetToken::getUser);
    }

    public void changeUserPassword(User user, String newPassword) {
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
        tokenRepository.findByUser(user).ifPresent(tokenRepository::delete);
    }


    // Find user by email
    public Optional<User> findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}

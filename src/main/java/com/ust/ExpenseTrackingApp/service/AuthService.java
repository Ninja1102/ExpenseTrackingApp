package com.ust.ExpenseTrackingApp.service;

import com.ust.ExpenseTrackingApp.dto.LoginRequest;
import com.ust.ExpenseTrackingApp.dto.RegisterRequest;
import com.ust.ExpenseTrackingApp.model.User;
import com.ust.ExpenseTrackingApp.repository.UserRepository;
import com.ust.ExpenseTrackingApp.config.JwtTokenProvider;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder,
                       AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    public String register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) { // Use getEmail()
            throw new RuntimeException("Email already in use!");
        }

        User user = new User();
        user.setEmail(request.getEmail()); // Use getEmail()
        user.setPassword(passwordEncoder.encode(request.getPassword())); // Use getPassword()
        userRepository.save(user);

        return "User registered successfully!";
    }

    public String login(LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        return jwtTokenProvider.generateToken(authentication);
    }
}

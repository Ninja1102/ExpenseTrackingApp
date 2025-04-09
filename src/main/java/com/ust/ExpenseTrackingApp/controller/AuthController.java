package com.ust.ExpenseTrackingApp.controller;

import com.ust.ExpenseTrackingApp.dto.LoginRequest;
import com.ust.ExpenseTrackingApp.dto.RegisterRequest;
import com.ust.ExpenseTrackingApp.dto.PasswordResetRequest;
import com.ust.ExpenseTrackingApp.service.AuthService;
import com.ust.ExpenseTrackingApp.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;
    private final UserService userService;

    public AuthController(AuthService authService, UserService userService) {
        this.authService = authService;
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@Valid @RequestBody RegisterRequest request) {
        return ResponseEntity.ok(authService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@Valid @RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<String> forgotPassword(@RequestParam String email) {
        userService.findUserByEmail(email).ifPresent(user -> {
            String token = userService.createPasswordResetTokenForUser(user);
            // Send email with token
        });
        return ResponseEntity.ok("Password reset link sent if email exists");
    }

    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(
            @RequestParam String token,
            @Valid @RequestBody PasswordResetRequest request
    ) {
        if (userService.validatePasswordResetToken(token)) {
            userService.getUserByPasswordResetToken(token).ifPresent(user -> {
                userService.changeUserPassword(user, request.newPassword()); // Changed here
            });
            return ResponseEntity.ok("Password reset successfully");
        }
        return ResponseEntity.badRequest().body("Invalid or expired token");
    }

}

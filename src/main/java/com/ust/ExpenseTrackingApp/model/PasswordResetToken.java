package com.ust.ExpenseTrackingApp.model;

import jakarta.persistence.*;

import java.time.Instant;

@Entity
public class PasswordResetToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String token;
    private Instant expiryDate;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public PasswordResetToken() {
    }

    public PasswordResetToken(String token, Instant expiryDate, User user) {
        this.token = token;
        this.expiryDate = expiryDate;
        this.user = user;
    }

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Instant getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Instant expiryDate) {
        this.expiryDate = expiryDate;
    }

    public User getUser() { // <- this is the missing method
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public boolean isExpired() {
        return this.expiryDate.isBefore(Instant.now());
    }

    // Manual builder implementation
    public static class Builder {
        private String token;
        private Instant expiryDate;
        private User user;

        public Builder token(String token) {
            this.token = token;
            return this;
        }

        public Builder expiryDate(Instant expiryDate) {
            this.expiryDate = expiryDate;
            return this;
        }

        public Builder user(User user) {
            this.user = user;
            return this;
        }

        public PasswordResetToken build() {
            return new PasswordResetToken(token, expiryDate, user);
        }
    }

    public static Builder builder() {
        return new Builder();
    }
}

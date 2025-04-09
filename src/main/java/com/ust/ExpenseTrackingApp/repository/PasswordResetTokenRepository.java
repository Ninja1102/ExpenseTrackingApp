package com.ust.ExpenseTrackingApp.repository;

import com.ust.ExpenseTrackingApp.model.PasswordResetToken;
import com.ust.ExpenseTrackingApp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Long> {

    Optional<PasswordResetToken> findByToken(String token);

    Optional<PasswordResetToken> findByUser(User user);
}

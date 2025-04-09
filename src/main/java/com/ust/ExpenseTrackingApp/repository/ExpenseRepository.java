package com.ust.ExpenseTrackingApp.repository;

import com.ust.ExpenseTrackingApp.model.Expense;
import com.ust.ExpenseTrackingApp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long> {

    List<Expense> findByUser(User user);

    @Query("SELECT SUM(e.amount) FROM Expense e WHERE e.user = :user AND e.date BETWEEN :start AND :end")
    BigDecimal calculateTotalExpenses(User user, LocalDateTime start, LocalDateTime end);

    @Query("SELECT AVG(e.amount) FROM Expense e WHERE e.user = :user AND e.date BETWEEN :start AND :end")
    BigDecimal calculateAverageExpense(User user, LocalDateTime start, LocalDateTime end);
}

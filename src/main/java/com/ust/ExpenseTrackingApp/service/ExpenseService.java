package com.ust.ExpenseTrackingApp.service;

import com.ust.ExpenseTrackingApp.dto.ExpenseSummaryResponse;
import com.ust.ExpenseTrackingApp.exception.ExpenseNotFoundException;
import com.ust.ExpenseTrackingApp.model.Expense;
import com.ust.ExpenseTrackingApp.model.User;
import com.ust.ExpenseTrackingApp.repository.ExpenseRepository;
import com.ust.ExpenseTrackingApp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.List;
import java.util.Optional;

@Service
public class ExpenseService {

    @Autowired
    private ExpenseRepository expenseRepository;

    @Autowired
    private UserRepository userRepository;

    public void addExpense(Expense expense, String email) {
        Optional<User> userOptional = userRepository.findByEmail(email);
        userOptional.ifPresent(expense::setUser);
        expense.setDate(LocalDateTime.now().toLocalDate());
        expenseRepository.save(expense);
    }

    public List<Expense> getAllExpenses(String email) {
        Optional<User> userOptional = userRepository.findByEmail(email);
        return userOptional.map(expenseRepository::findByUser).orElse(null);
    }

    public ExpenseSummaryResponse getExpenseSummary(User user) {
        // No need to fetch again, we already have user
        LocalDate today = LocalDate.now();
        LocalDateTime startOfDay = today.atStartOfDay();
        LocalDateTime endOfDay = today.atTime(23, 59, 59);

        LocalDate startOfMonth = today.with(TemporalAdjusters.firstDayOfMonth());
        LocalDate startOfQuarter = today.minusMonths(3).with(TemporalAdjusters.firstDayOfMonth());
        LocalDate startOfYear = today.with(TemporalAdjusters.firstDayOfYear());

        BigDecimal dailyExpense = expenseRepository.calculateTotalExpenses(user, startOfDay, endOfDay);
        BigDecimal monthlyExpense = expenseRepository.calculateTotalExpenses(user, startOfMonth.atStartOfDay(), endOfDay);
        BigDecimal quarterlyExpense = expenseRepository.calculateTotalExpenses(user, startOfQuarter.atStartOfDay(), endOfDay);
        BigDecimal yearlyExpense = expenseRepository.calculateTotalExpenses(user, startOfYear.atStartOfDay(), endOfDay);
        BigDecimal avgMonthlyExpense = expenseRepository.calculateAverageExpense(user, startOfMonth.atStartOfDay(), endOfDay);

        return new ExpenseSummaryResponse(
                dailyExpense != null ? dailyExpense : BigDecimal.ZERO,
                monthlyExpense != null ? monthlyExpense : BigDecimal.ZERO,
                quarterlyExpense != null ? quarterlyExpense : BigDecimal.ZERO,
                yearlyExpense != null ? yearlyExpense : BigDecimal.ZERO,
                avgMonthlyExpense != null ? avgMonthlyExpense : BigDecimal.ZERO
        );
    }



    public void deleteExpense(Long id, String username) {
        if(expenseRepository.existsById(id)){
            expenseRepository.deleteById(id);
        }
    }
}

package com.ust.ExpenseTrackingApp.controller;

import com.ust.ExpenseTrackingApp.config.CustomUserDetails;
import com.ust.ExpenseTrackingApp.dto.ExpenseRequest;
import com.ust.ExpenseTrackingApp.dto.ExpenseSummaryResponse;
import com.ust.ExpenseTrackingApp.model.Expense;
import com.ust.ExpenseTrackingApp.model.User;
import com.ust.ExpenseTrackingApp.service.ExpenseService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/expenses")
public class ExpenseController {

    private final ExpenseService expenseService;

    public ExpenseController(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }

    @PostMapping
    public ResponseEntity<Expense> addExpense(@Valid @RequestBody ExpenseRequest request,
                                              @AuthenticationPrincipal UserDetails userDetails) {
        Expense expense = new Expense();
        expense.setCategory(request.getCategory());
        expense.setAmount(BigDecimal.valueOf(request.getAmount()));
        expense.setDate(request.getDate());
        expenseService.addExpense(expense, userDetails.getUsername());
        return ResponseEntity.ok(expense);
    }

    @GetMapping
    public ResponseEntity<List<Expense>> getAllExpenses(@AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(expenseService.getAllExpenses(userDetails.getUsername()));
    }

    @GetMapping("/summary")
    public ResponseEntity<ExpenseSummaryResponse> getSummary(@AuthenticationPrincipal CustomUserDetails userDetails) {
        try {
            User user = userDetails.getUser();
            ExpenseSummaryResponse summary = expenseService.getExpenseSummary(user);
            return ResponseEntity.ok(summary);
        } catch (Exception e) {
            e.printStackTrace(); // Logs the full error in the console
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ExpenseSummaryResponse(BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO));
        }
    }




    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteExpense(@PathVariable Long id,
                                              @AuthenticationPrincipal UserDetails userDetails) {
        expenseService.deleteExpense(id, userDetails.getUsername());
        return ResponseEntity.noContent().build();
    }
}

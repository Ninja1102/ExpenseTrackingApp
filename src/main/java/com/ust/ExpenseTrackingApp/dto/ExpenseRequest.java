package com.ust.ExpenseTrackingApp.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class ExpenseRequest {
    @NotNull(message = "Category cannot be null")
    private String category;

    @NotNull(message = "Amount cannot be null")
    private Double amount;

    @NotNull(message = "Date cannot be null")
    private LocalDate date; // Use LocalDate if preferred

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}



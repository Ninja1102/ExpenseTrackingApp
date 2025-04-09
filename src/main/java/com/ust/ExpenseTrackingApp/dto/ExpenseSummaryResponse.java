package com.ust.ExpenseTrackingApp.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter

public class ExpenseSummaryResponse {
    private BigDecimal dailyExpense;
    private BigDecimal monthlyExpense;
    private BigDecimal quarterlyExpense;
    private BigDecimal yearlyExpense;
    private BigDecimal avgMonthlyExpense;

    public ExpenseSummaryResponse(BigDecimal dailyExpense, BigDecimal monthlyExpense, BigDecimal quarterlyExpense, BigDecimal yearlyExpense, BigDecimal avgMonthlyExpense) {
        this.dailyExpense = dailyExpense;
        this.monthlyExpense = monthlyExpense;
        this.quarterlyExpense = quarterlyExpense;
        this.yearlyExpense = yearlyExpense;
        this.avgMonthlyExpense = avgMonthlyExpense;
    }

    public BigDecimal getDailyExpense() {
        return dailyExpense;
    }

    public void setDailyExpense(BigDecimal dailyExpense) {
        this.dailyExpense = dailyExpense;
    }

    public BigDecimal getMonthlyExpense() {
        return monthlyExpense;
    }

    public void setMonthlyExpense(BigDecimal monthlyExpense) {
        this.monthlyExpense = monthlyExpense;
    }

    public BigDecimal getQuarterlyExpense() {
        return quarterlyExpense;
    }

    public void setQuarterlyExpense(BigDecimal quarterlyExpense) {
        this.quarterlyExpense = quarterlyExpense;
    }

    public BigDecimal getYearlyExpense() {
        return yearlyExpense;
    }

    public void setYearlyExpense(BigDecimal yearlyExpense) {
        this.yearlyExpense = yearlyExpense;
    }

    public BigDecimal getAvgMonthlyExpense() {
        return avgMonthlyExpense;
    }

    public void setAvgMonthlyExpense(BigDecimal avgMonthlyExpense) {
        this.avgMonthlyExpense = avgMonthlyExpense;
    }
}

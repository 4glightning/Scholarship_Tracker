package com.example.scholarshiptracker;

public class WeeklyIncomeChart {
    String days;
    int income;

    public WeeklyIncomeChart(String days, int income) {
        this.days = days;
        this.income = income;
    }

    public String getDays() {
        return days;
    }

    public void setDays(String days) {
        this.days = days;
    }

    public int getIncome() {
        return income;
    }

    public void setIncome(int income) {
        this.income = income;
    }
}

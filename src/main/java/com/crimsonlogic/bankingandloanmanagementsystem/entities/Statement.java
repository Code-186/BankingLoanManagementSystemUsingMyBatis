package com.crimsonlogic.bankingandloanmanagementsystem.entities;

import java.time.LocalDate;

public class Statement {

    private long accountNumber;
    private LocalDate fromDate;
    private LocalDate toDate;

    public Statement() {
    }

    public Statement(long accountNumber,
                     LocalDate fromDate,
                     LocalDate toDate) {

        this.accountNumber = accountNumber;
        this.fromDate = fromDate;
        this.toDate = toDate;
    }

    public long getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(long accountNumber) {
        this.accountNumber = accountNumber;
    }

    public LocalDate getFromDate() {
        return fromDate;
    }

    public void setFromDate(LocalDate fromDate) {
        this.fromDate = fromDate;
    }

    public LocalDate getToDate() {
        return toDate;
    }

    public void setToDate(LocalDate toDate) {
        this.toDate = toDate;
    }

    @Override
    public String toString() {
        return "Statement{" +
                "accountNumber=" + accountNumber +
                ", fromDate=" + fromDate +
                ", toDate=" + toDate +
                '}';
    }
}
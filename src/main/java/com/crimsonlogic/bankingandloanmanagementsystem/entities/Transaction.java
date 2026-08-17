package com.crimsonlogic.bankingandloanmanagementsystem.entities;

import java.time.LocalDateTime;
import java.util.Objects;

public class Transaction {

    private String transactionId;
    private long accountNumber;
    private String transactionType;
    private double amount;
    private String status;
    private LocalDateTime transactionDate;

    public Transaction() {
    }

    public Transaction(String transactionId,
                       long accountNumber,
                       String transactionType,
                       double amount,
                       String status,
                       LocalDateTime transactionDate) {

        this.transactionId = transactionId;
        this.accountNumber = accountNumber;
        this.transactionType = transactionType;
        this.amount = amount;
        this.status = status;
        this.transactionDate = transactionDate;
    }

    // getters setters

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public long getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(long accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(LocalDateTime transactionDate) {
        this.transactionDate = transactionDate;
    }

    @Override
    public String toString() {

        return "Transaction [transactionId=" + transactionId
                + ", accountNumber=" + accountNumber
                + ", transactionType=" + transactionType
                + ", amount=" + amount
                + ", status=" + status
                + ", transactionDate=" + transactionDate + "]";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transaction that = (Transaction) o;
        return transactionId == that.transactionId && accountNumber == that.accountNumber && Double.compare(amount, that.amount) == 0 && Objects.equals(transactionType, that.transactionType) && Objects.equals(status, that.status) && Objects.equals(transactionDate, that.transactionDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(transactionId, accountNumber, transactionType, amount, status, transactionDate);
    }
}
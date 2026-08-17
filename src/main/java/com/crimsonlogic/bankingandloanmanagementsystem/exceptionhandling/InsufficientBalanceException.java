package com.crimsonlogic.bankingandloanmanagementsystem.exceptionhandling;

public class InsufficientBalanceException extends RuntimeException {

    public InsufficientBalanceException(String message) {
        super(message);
    }
}
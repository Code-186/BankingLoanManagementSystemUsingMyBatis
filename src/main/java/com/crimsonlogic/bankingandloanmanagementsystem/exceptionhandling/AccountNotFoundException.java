package com.crimsonlogic.bankingandloanmanagementsystem.exceptionhandling;

public class AccountNotFoundException
        extends RuntimeException {

    public AccountNotFoundException(String message) {
        super(message);
    }
}
package com.crimsonlogic.bankingandloanmanagementsystem.exceptionhandling;

public class AccountInactiveException extends RuntimeException {

    public AccountInactiveException(String message) {
        super(message);
    }
}
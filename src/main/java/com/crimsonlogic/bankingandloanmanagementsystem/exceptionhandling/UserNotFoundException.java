package com.crimsonlogic.bankingandloanmanagementsystem.exceptionhandling;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String message) {
        super(message);
    }
}
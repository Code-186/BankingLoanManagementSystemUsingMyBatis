package com.crimsonlogic.bankingandloanmanagementsystem.exceptionhandling;

public class AdminNotFoundException extends UserNotFoundException {
    public AdminNotFoundException(String message) {
        super(message);
    }
}
package com.crimsonlogic.bankingandloanmanagementsystem.exceptionhandling;

public class CustomerNotFoundException extends UserNotFoundException {
    public CustomerNotFoundException(String message) {
        super(message);
    }
}
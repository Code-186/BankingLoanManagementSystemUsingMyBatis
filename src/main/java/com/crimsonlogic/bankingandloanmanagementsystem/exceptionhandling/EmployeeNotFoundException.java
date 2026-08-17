package com.crimsonlogic.bankingandloanmanagementsystem.exceptionhandling;

public class EmployeeNotFoundException extends UserNotFoundException {
    public EmployeeNotFoundException(String message) {
        super(message);
    }
}
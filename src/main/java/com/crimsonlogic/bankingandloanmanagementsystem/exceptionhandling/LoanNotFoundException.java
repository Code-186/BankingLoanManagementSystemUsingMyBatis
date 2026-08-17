package com.crimsonlogic.bankingandloanmanagementsystem.exceptionhandling;

public class LoanNotFoundException extends RuntimeException {

    public LoanNotFoundException(String message) {
        super(message);
    }
}

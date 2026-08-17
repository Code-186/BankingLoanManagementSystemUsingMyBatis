package com.crimsonlogic.bankingandloanmanagementsystem.exceptionhandling;

public class InvalidMPINException extends RuntimeException {
    public InvalidMPINException(String message) {
        super(message);
    }
}
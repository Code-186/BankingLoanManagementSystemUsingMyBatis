package com.crimsonlogic.bankingandloanmanagementsystem.utility;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class ValidationUtil {

    public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private ValidationUtil() {}

    public static boolean validateAmount(double amount) {
        return amount > 0;
    }

    public static boolean validateEmail(String email) {
        return email != null
                && email.matches("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")
                && !email.matches(".*(.)\\1\\1\\1.*");
    }

    public static boolean validatePhone(String phone) {
        return phone != null && phone.matches("^[6-9]\\d{9}$");
    }

    public static boolean validateName(String name) {
        if (name == null) {
            return false;
        }
        name = name.trim();
        return name.matches("^[A-Za-z]{3,}(\\s[A-Za-z]{2,})*$") && !name.matches("^(.)\\1+$");
    }

    public static boolean validateSalary(String salary) {
        return salary != null && salary.matches("^\\d+(\\.\\d{1,2})?$");
    }

    public static boolean validatePassword(String password) {
        return password != null
                && password.matches("^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@#$%^&+=!]).{8,20}$");
    }

    public static boolean validateAccountNumber(String accountNumber) {
        return accountNumber != null && accountNumber.matches("^\\d{12}$");
    }

    public static boolean validateMPin(String mPin) {
        return mPin != null && mPin.matches("^\\d{4}$");
    }

    public static LocalDate parseAndValidateDob(String dobStr) {
        if (dobStr == null || dobStr.trim().isEmpty()) {
            return null;
        }
        try {
            LocalDate dob = LocalDate.parse(dobStr.trim(), DATE_FORMATTER);
            int age = Period.between(dob, LocalDate.now()).getYears();
            if (age >= 18 && age <= 100) {
                return dob;
            }
        } catch (DateTimeParseException ignored) {}
        return null;
    }

    public static boolean validateDesignation(String designation) {
        return designation != null && designation.trim().matches("^[A-Za-z\\s_]{2,50}$");
    }

    public static boolean validateRole(String role) {
        return designationOrRole(role);
    }

    private static boolean designationOrRole(String val) {
        return val != null && val.trim().matches("^[A-Za-z\\s_]{2,50}$");
    }
}
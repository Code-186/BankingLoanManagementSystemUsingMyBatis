package com.crimsonlogic.bankingandloanmanagementsystem.utility;

import java.util.Random;

public class IdGeneratorUtil {

    private static final Random RANDOM = new Random();

    private IdGeneratorUtil() {}

    /**
     * Generates a 4-digit number between 1000 and 9999.
     */
    private static int generate4DigitNumber() {
        return 1000 + RANDOM.nextInt(9000); // Guarantees range [1000 - 9999]
    }

    public static String generateEmployeeId() {
        return "EMP" + generate4DigitNumber();
    }

    public static String generateCustomerId() {
        return "CUST" + generate4DigitNumber();
    }

    public static String generateLoanId() {
        return "LOAN" + generate4DigitNumber();
    }

    public static String generateAdminId() {
        return "ADM" + generate4DigitNumber();
    }

    /**
     * Generates a 12-digit Account Number starting with a non-zero digit.
     */
    public static long generateAccountNumber() {
        long min = 100000000000L;
        long max = 999999999999L;
        return min + (long) (RANDOM.nextDouble() * (max - min));
    }
}
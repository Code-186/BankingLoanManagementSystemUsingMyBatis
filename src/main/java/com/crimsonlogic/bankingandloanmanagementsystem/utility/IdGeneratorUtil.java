package com.crimsonlogic.bankingandloanmanagementsystem.utility;

import java.util.Random;

public class IdGeneratorUtil {

    private static final Random RANDOM =
            new Random();

    private IdGeneratorUtil() {
    }

    private static String generateId(
            String prefix) {

        return prefix +
                (100 + RANDOM.nextInt(900));
    }

    public static String generateAdminId() {

        return generateId("ADM");
    }

    public static String generateEmployeeId() {

        return generateId("EMP");
    }

    public static String generateCustomerId() {

        return generateId("CUST");
    }

    public static String generateBeneficiaryId() {

        return generateId("BEN");
    }

    public static String generateLoanId() {

        return generateId("LOAN");
    }

    public static String generateEmiId() {

        return generateId("EMI");
    }

    public static long generateAccountNumber() {

        return 1000000000L
                + RANDOM.nextInt(900000000);
    }

    public static String generateTransactionId() {

        return generateId("TXN");
    }
}
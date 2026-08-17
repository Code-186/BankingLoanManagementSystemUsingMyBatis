package com.crimsonlogic.bankingandloanmanagementsystem.utility;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;

public class BranchSelectionUtil {

    private static final Map<String, BankBranch> BANK_BRANCH_MAP = new LinkedHashMap<>();

    public static class BankBranch {
        private final String bankName;
        private final String branchId;

        public BankBranch(String bankName, String branchId) {
            this.bankName = bankName;
            this.branchId = branchId;
        }

        public String getBankName() {
            return bankName;
        }

        public String getBranchId() {
            return branchId;
        }
    }

    static {
        BANK_BRANCH_MAP.put("1", new BankBranch("State Bank of India", "SBI0101"));
        BANK_BRANCH_MAP.put("2", new BankBranch("Canara Bank", "CNRB0202"));
        BANK_BRANCH_MAP.put("3", new BankBranch("Union Bank of India", "UBI0303"));
        BANK_BRANCH_MAP.put("4", new BankBranch("Karnataka Bank", "KTK0404"));
    }

    private BranchSelectionUtil() {}

    public static BankBranch selectBankBranch(Scanner sc) {
        while (true) {
            System.out.println("\n--- Select Bank ---");
            System.out.println("1. State Bank of India (Branch: SBI0101)");
            System.out.println("2. Canara Bank (Branch: CNRB0202)");
            System.out.println("3. Union Bank of India (Branch: UBI0303)");
            System.out.println("4. Karnataka Bank (Branch: KTK0404)");
            System.out.print("Enter bank choice (1-4): ");
            String choice = sc.nextLine().trim();

            if (BANK_BRANCH_MAP.containsKey(choice)) {
                return BANK_BRANCH_MAP.get(choice);
            }
            System.out.println("--> Invalid choice! Please select a valid bank (1-4).");
        }
    }

    public static String getBankNameByBranchId(String branchId) {
        if (branchId == null) return "Unknown Bank";
        for (BankBranch branch : BANK_BRANCH_MAP.values()) {
            if (branch.getBranchId().equalsIgnoreCase(branchId.trim())) {
                return branch.getBankName();
            }
        }
        return "National Banking Corp";
    }
}
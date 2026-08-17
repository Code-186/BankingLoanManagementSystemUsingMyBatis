package com.crimsonlogic.bankingandloanmanagementsystem.utility;

public class MenuUtil {

    private MenuUtil() {}

    public static void displayMainMenu() {
        System.out.println("\n==========================================");
        System.out.println("     CORE BANKING & LOAN MANAGEMENT       ");
        System.out.println("==========================================");
        System.out.println("1. Customer Login");
        System.out.println("2. Customer Registration (Self)");
        System.out.println("3. Employee Login");
        System.out.println("4. Admin Login");
        System.out.println("5. Exit System");
        System.out.print("Please enter your choice (1-5): ");
    }

    public static void displayAdminMenu(String bankName, String adminName) {
        System.out.println("\n==========================================");
        System.out.println(" ADMIN PORTAL - " + bankName.toUpperCase());
        System.out.println(" Logged In As: " + adminName);
        System.out.println("==========================================");
        System.out.println("1. Employee Management");
        System.out.println("2. Deactivate Customer");
        System.out.println("3. View Management");
        System.out.println("4. Loan Management");
        System.out.println("5. Reports");
        System.out.println("6. Logout");
        System.out.print("Enter choice (1-6): ");
    }

    public static void displayEmployeeManagementMenu() {
        System.out.println("\n--- EMPLOYEE MANAGEMENT ---");
        System.out.println("1. Register Employee");
        System.out.println("2. Deactivate Employee");
        System.out.println("3. Back to Admin Menu");
        System.out.print("Enter choice (1-3): ");
    }

    public static void displayAdminViewMenu() {
        System.out.println("\n--- VIEW MANAGEMENT ---");
        System.out.println("1. View All Admins");
        System.out.println("2. View Employee by ID");
        System.out.println("3. View All Employees");
        System.out.println("4. View Customer by ID");
        System.out.println("5. View All Customers");
        System.out.println("6. Back to Admin Menu");
        System.out.print("Enter choice (1-6): ");
    }

    public static void displayAdminLoanMenu() {
        System.out.println("\n--- ADMIN LOAN MANAGEMENT ---");
        System.out.println("1. Approve Loan Application");
        System.out.println("2. Reject Loan Application");
        System.out.println("3. Find Loan by Loan ID");
        System.out.println("4. View All Loans");
        System.out.println("5. View Pending Loans");
        System.out.println("6. View Approved Loans");
        System.out.println("7. Back to Admin Menu");
        System.out.print("Enter choice (1-7): ");
    }

    public static void displayEmployeeMenu(String bankName, String branchId, String empName) {
        System.out.println("\n==========================================");
        System.out.println(" EMPLOYEE PORTAL - " + bankName.toUpperCase());
        System.out.println(" Branch: " + branchId + " | Logged in: " + empName);
        System.out.println("==========================================");
        System.out.println("1. Register Customer");
        System.out.println("2. View Customer by ID");
        System.out.println("3. View All Branch Customers");
        System.out.println("4. Open Bank Account");
        System.out.println("5. Deposit Funds");
        System.out.println("6. Withdraw Funds");
        System.out.println("7. Logout");
        System.out.print("Enter choice (1-7): ");
    }

    public static void displayCustomerMenu(String bankName, String custName) {
        System.out.println("\n==========================================");
        System.out.println(" CUSTOMER PORTAL - " + bankName.toUpperCase());
        System.out.println(" Welcome, " + custName);
        System.out.println("==========================================");
        System.out.println("1. View My Bank Accounts");
        System.out.println("2. Loan Management");
        System.out.println("3. Update Profile");
        System.out.println("4. Logout");
        System.out.print("Enter choice (1-4): ");
    }

    public static void displayCustomerLoanMenu() {
        System.out.println("\n--- LOAN SERVICES ---");
        System.out.println("1. Apply for a Loan");
        System.out.println("2. View My Loan Applications");
        System.out.println("3. View EMI & Repayment Schedule");
        System.out.println("4. Back to Customer Menu");
        System.out.print("Enter choice (1-4): ");
    }

    public static void displayCustomerProfileMenu() {
        System.out.println("\n--- UPDATE PROFILE ---");
        System.out.println("1. Update Phone Number");
        System.out.println("2. Update Address");
        System.out.println("3. Update Nominee Details");
        System.out.println("4. Change Password");
        System.out.println("5. Change Account MPIN");
        System.out.println("6. Back to Customer Menu");
        System.out.print("Enter choice (1-6): ");
    }

    public static void displayReportsMenu() {
        System.out.println("\n==========================================");
        System.out.println("             SYSTEM REPORTS               ");
        System.out.println("==========================================");
        System.out.println("1. Customer Reports");
        System.out.println("2. Account Reports");
        System.out.println("3. Loan Reports");
        System.out.println("4. Transaction Reports");
        System.out.println("5. Analytics Reports");
        System.out.println("6. Back to Menu");
        System.out.print("Enter choice (1-6): ");
    }

    public static void displayLoanReportsSubMenu() {
        System.out.println("\n--- LOAN ANALYTICS & REPORTS ---");
        System.out.println("1. Comprehensive Loan Portfolio Report");
        System.out.println("2. Pending Loan Approvals Report");
        System.out.println("3. Approved Active Loans Report");
        System.out.println("4. Rejected Loans Report");
        System.out.println("5. Back to Reports Menu");
        System.out.print("Enter choice (1-5): ");
    }
}
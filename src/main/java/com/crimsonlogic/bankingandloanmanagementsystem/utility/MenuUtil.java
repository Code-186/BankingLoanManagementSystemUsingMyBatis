package com.crimsonlogic.bankingandloanmanagementsystem.utility;

public class MenuUtil {

    public MenuUtil() {
    }

    public void showMainMenu() {
        System.out.println("\n=================================");
        System.out.println("BANKING & LOAN MANAGEMENT SYSTEM");
        System.out.println("=================================");
        System.out.println("1. Admin Login");
        System.out.println("2. Employee Login");
        System.out.println("3. Customer Portal");
        System.out.println("4. Exit");
    }

    public void showAdminMenu() {
        System.out.println("\n============= ADMIN MENU =============");
        System.out.println("1. Employee Management");
        System.out.println("2. Delete Customer");
        System.out.println("3. View Management");
        System.out.println("4. Loan Management");
        System.out.println("5. Reports");
        System.out.println("6. Logout");
    }

    public void showEmployeeManagementMenu() {
        System.out.println("\n===== EMPLOYEE MANAGEMENT =====");
        System.out.println("1. Register Employee");
        System.out.println("2. Delete Employee");
        System.out.println("3. Back");
    }

    public void showViewManagementMenu() {
        System.out.println("\n===== VIEW MANAGEMENT =====");
        System.out.println("1. View All Admins");
        System.out.println("2. View Employee");
        System.out.println("3. View All Employees");
        System.out.println("4. View Customer");
        System.out.println("5. View All Customers");
        System.out.println("6. Back");
    }

    public void showEmployeeMenu() {
        System.out.println("\n========= EMPLOYEE MENU =========");
        System.out.println("1. Register Customer");
        System.out.println("2. View Customer");
        System.out.println("3. View All Customers");
        System.out.println("4. Open Account");
        System.out.println("5. Deposit");
        System.out.println("6. Withdraw");
        System.out.println("7. Beneficiary Management");
        System.out.println("8. Logout");
    }

    public void showCustomerMenu() {
        System.out.println("\n========= CUSTOMER MENU =========");
        System.out.println("1. View Accounts");
        System.out.println("2. Beneficiary Management");
        System.out.println("3. Transfer Funds");
        System.out.println("4. View Transactions");
        System.out.println("5. Download Statement");
        System.out.println("6. Loan Management");
        System.out.println("7. Update Profile");
        System.out.println("8. Logout");
    }

    public void showBeneficiaryMenu() {
        System.out.println("\n===== BENEFICIARY MANAGEMENT MENU =====");
        System.out.println("1. Add Beneficiary");
        System.out.println("2. Remove Beneficiary");
        System.out.println("3. Search Beneficiary");
        System.out.println("4. View All Beneficiaries");
        System.out.println("5. Back");
    }

    public void showAdminLoanManagementMenu() {
        System.out.println("\n===== LOAN MANAGEMENT MENU =====");
        System.out.println("1. Approve Loan");
        System.out.println("2. Reject Loan");
        System.out.println("3. Find Loans");
        System.out.println("4. View All Loans");
        System.out.println("5. View Pending Loans");
        System.out.println("6. View Approved Loans");
        System.out.println("7. Back");
    }

    public void customerLoanMenu() {
        System.out.println("\n===== LOAN MANAGEMENT MENU =====");
        System.out.println("1. Apply Loan");
        System.out.println("2. View My Loans");
        System.out.println("3. View EMIs");
        System.out.println("4. Pay EMI");
        System.out.println("5. Back");
    }

    public void showReportsMenu() {
        System.out.println("\n===== REPORTS MENU =====");
        System.out.println("1. Customer Reports");
        System.out.println("2. Account Reports");
        System.out.println("3. Loan Reports");
        System.out.println("4. Transaction Reports");
        System.out.println("5. Analytics Reports");
        System.out.println("6. Back");
    }

    public void showCustomerReportsMenu() {
        System.out.println("\n===== CUSTOMER REPORTS =====");
        System.out.println("1. Active Customers");
        System.out.println("2. Sort Customers By Name");
        System.out.println("3. Group Customers By Branch");
        System.out.println("4. Back");
    }

    public void showAccountReportsMenu() {
        System.out.println("\n===== ACCOUNT REPORTS =====");
        System.out.println("1. Active Accounts");
        System.out.println("2. Savings Accounts");
        System.out.println("3. Sort Accounts By Balance");
        System.out.println("4. Top 5 Highest Balances");
        System.out.println("5. Account With Highest Balance");
        System.out.println("6. Earliest Account Opened");
        System.out.println("7. Optional Account Lookup");
        System.out.println("8. Back");
    }

    public void showLoanReportsMenu() {
        System.out.println("\n===== LOAN REPORTS =====");
        System.out.println("1. Highest Loan Amount");
        System.out.println("2. Lowest Loan Amount");
        System.out.println("3. Total Interest Revenue");
        System.out.println("4. Group Loans By Status");
        System.out.println("5. Count Loans Per Customer");
        System.out.println("6. Customer With Highest Loan");
        System.out.println("7. Distinct Loan Types");
        System.out.println("8. Any Pending Loan");
        System.out.println("9. All Loans Approved");
        System.out.println("10. Back");
    }

    public void showTransactionReportsMenu() {
        System.out.println("\n===== TRANSACTION REPORTS =====");
        System.out.println("1. Total Deposits");
        System.out.println("2. Total Withdrawals");
        System.out.println("3. Count Transactions Per Account");
        System.out.println("4. Latest Transaction");
        System.out.println("5. Back");
    }

    public void showAnalyticsReportsMenu() {
        System.out.println("\n===== ANALYTICS REPORTS =====");
        System.out.println("1. Group Accounts By Type");
        System.out.println("2. Partition Paid/Unpaid EMIs");
        System.out.println("3. Balance Statistics");
        System.out.println("4. Back");
    }
}
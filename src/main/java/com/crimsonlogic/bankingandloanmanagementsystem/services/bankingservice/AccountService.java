package com.crimsonlogic.bankingandloanmanagementsystem.services.bankingservice;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import com.crimsonlogic.bankingandloanmanagementsystem.dao.AccountDao;
import com.crimsonlogic.bankingandloanmanagementsystem.dao.CustomerDao;
import com.crimsonlogic.bankingandloanmanagementsystem.exceptionhandling.AccountInactiveException;
import com.crimsonlogic.bankingandloanmanagementsystem.exceptionhandling.AccountNotFoundException;
import com.crimsonlogic.bankingandloanmanagementsystem.exceptionhandling.CustomerNotFoundException;
import com.crimsonlogic.bankingandloanmanagementsystem.exceptionhandling.InsufficientBalanceException;
import com.crimsonlogic.bankingandloanmanagementsystem.exceptionhandling.InvalidMPINException;
import com.crimsonlogic.bankingandloanmanagementsystem.model.implementations.userimplementations.Customer;
import com.crimsonlogic.bankingandloanmanagementsystem.model.implementations.userimplementations.Employee;
import com.crimsonlogic.bankingandloanmanagementsystem.utility.IdGeneratorUtil;
import com.crimsonlogic.bankingandloanmanagementsystem.utility.PasswordUtil;
import com.crimsonlogic.bankingandloanmanagementsystem.utility.TableUtil;
import com.crimsonlogic.bankingandloanmanagementsystem.utility.ValidationUtil;

public class AccountService {

    private final AccountDao accountDao = new AccountDao();
    private final CustomerDao customerDao = new CustomerDao();

    public static final double SAVINGS_MIN_BALANCE = 500.00;
    public static final double CURRENT_MIN_BALANCE = 1000.00;

    public void openAccount(Scanner scanner, Employee employee) {
        System.out.println("\n=== OPEN BANK ACCOUNT ===");
        System.out.print("Enter Customer ID: ");
        String customerId = scanner.nextLine().trim();

        Customer customer = customerDao.getCustomerById(customerId);
        if (customer == null) {
            throw new CustomerNotFoundException("Customer ID '" + customerId + "' not found.");
        }

        if (!customer.getBranchId().equalsIgnoreCase(employee.getBranchId())) {
            throw new CustomerNotFoundException("Customer belongs to branch " + customer.getBranchId()
                    + ", but you are operating branch " + employee.getBranchId());
        }

        System.out.println("Select Account Type:");
        System.out.println("1. Savings Account (Min Balance: INR 500 | Default Interest: 4.0%)");
        System.out.println("2. Current Account (Min Balance: INR 1000 | Default Overdraft: INR 10,000)");

        String accountType;
        double minBalance;
        double extraParam;

        while (true) {
            System.out.print("Enter Choice (1-2): ");
            String choice = scanner.nextLine().trim();
            if ("1".equals(choice)) {
                accountType = "SAVINGS";
                minBalance = SAVINGS_MIN_BALANCE;
                extraParam = 4.0;
                break;
            } else if ("2".equals(choice)) {
                accountType = "CURRENT";
                minBalance = CURRENT_MIN_BALANCE;
                extraParam = 10000.0;
                break;
            }
            System.out.println("--> Invalid choice! Select 1 or 2.");
        }

        double initialDeposit;
        while (true) {
            System.out.printf("Enter Initial Deposit (Minimum INR %.2f): INR ", minBalance);
            try {
                initialDeposit = Double.parseDouble(scanner.nextLine().trim());
                if (initialDeposit >= minBalance) {
                    break;
                }
                System.out.printf("--> Amount is below minimum balance requirement of INR %.2f. Try again.%n", minBalance);
            } catch (NumberFormatException e) {
                System.out.println("--> Invalid amount format. Try again.");
            }
        }

        String mpin;
        while (true) {
            System.out.print("Set 4-Digit Account MPIN: ");
            mpin = scanner.nextLine().trim();
            if (ValidationUtil.validateMPin(mpin)) {
                break;
            }
            System.out.println("--> Invalid MPIN! Must be exactly 4 digits.");
        }

        String accountNumber = String.valueOf(IdGeneratorUtil.generateAccountNumber());[cite: 2]

        Map<String, Object> params = new HashMap<>();
        params.put("accountNumber", accountNumber);
        params.put("balance", initialDeposit);
        params.put("openingDate", LocalDate.now());
        params.put("accountStatus", "ACTIVE");
        params.put("mpin", PasswordUtil.hash(mpin));
        params.put("customerId", customerId);
        params.put("accountType", accountType);

        if ("SAVINGS".equalsIgnoreCase(accountType)) {
            params.put("interestRate", extraParam);
            params.put("overdraftLimit", null);
        } else {
            params.put("interestRate", null);
            params.put("overdraftLimit", extraParam);
        }

        if (accountDao.insertAccount(params)) {
            customerDao.updateCustomerStatus(customerId, "ACTIVE");
            System.out.println("\n>>> ACCOUNT OPENED AND ACTIVATED SUCCESSFULLY! <<<");
            List<String> headers = List.of("ACCOUNT NUMBER", "CUSTOMER ID", "TYPE", "BALANCE (INR)", "STATUS", "BRANCH");
            List<List<String>> rows = List.of(List.of(accountNumber, customerId, accountType, String.format("%.2f", initialDeposit), "ACTIVE", employee.getBranchId()));
            TableUtil.printTable("OPENED ACCOUNT SUMMARY", headers, rows);[cite: 4]
        } else {
            System.out.println("--> Failed to persist account to database.");
        }
    }

    public void deposit(Scanner scanner) {
        System.out.println("\n=== DEPOSIT FUNDS ===");
        System.out.print("Enter 12-Digit Account Number: ");
        String accountNumber = scanner.nextLine().trim();

        Map<String, Object> account = accountDao.getAccountByNumber(accountNumber);
        if (account == null) {
            throw new AccountNotFoundException("Account number '" + accountNumber + "' not found.");
        }

        double amount;
        while (true) {
            System.out.print("Enter Deposit Amount (Max INR 10,00,000): INR ");
            try {
                amount = Double.parseDouble(scanner.nextLine().trim());
                if (amount > 0 && amount <= 1000000.0) {
                    break;
                }
                System.out.println("--> Amount must be between INR 1 and INR 10,00,000. Try again.");
            } catch (NumberFormatException e) {
                System.out.println("--> Invalid numerical input.");
            }
        }

        double currentBal = ((Number) account.get("balance")).doubleValue();
        double newBal = currentBal + amount;
        String type = (String) account.get("account_type");
        double minBal = "SAVINGS".equalsIgnoreCase(type) ? SAVINGS_MIN_BALANCE : CURRENT_MIN_BALANCE;
        String status = (newBal >= minBal) ? "ACTIVE" : "INACTIVE";

        accountDao.updateBalanceAndStatus(accountNumber, newBal, status);
        System.out.printf("\n>>> Deposit successful! Updated Balance: INR %.2f (Status: %s) <<<%n", newBal, status);
    }

    public void withdraw(Scanner scanner) {
        System.out.println("\n=== WITHDRAW FUNDS ===");
        System.out.print("Enter 12-Digit Account Number: ");
        String accountNumber = scanner.nextLine().trim();

        Map<String, Object> account = accountDao.getAccountByNumber(accountNumber);
        if (account == null) {
            throw new AccountNotFoundException("Account number '" + accountNumber + "' not found.");
        }

        if ("INACTIVE".equalsIgnoreCase((String) account.get("account_status"))) {
            throw new AccountInactiveException("Account is INACTIVE. Deposit funds to reactivate.");
        }

        double currentBal = ((Number) account.get("balance")).doubleValue();

        double amount;
        while (true) {
            System.out.printf("Enter Withdrawal Amount (Current Balance: INR %.2f): INR ", currentBal);
            try {
                amount = Double.parseDouble(scanner.nextLine().trim());
                if (amount <= 0) {
                    System.out.println("--> Amount must be greater than 0. Try again.");
                    continue;
                }
                if (amount > currentBal) {
                    throw new InsufficientBalanceException("Withdrawal amount of INR " + amount + " exceeds current balance of INR " + currentBal);
                }
                break;
            } catch (NumberFormatException e) {
                System.out.println("--> Invalid numerical format.");
            }
        }

        System.out.print("Enter 4-Digit MPIN: ");
        String enteredMpin = scanner.nextLine().trim();

        String storedMpin = (String) account.get("mpin");
        if (!PasswordUtil.verify(enteredMpin, storedMpin)) {
            throw new InvalidMPINException("Incorrect MPIN! Withdrawal transaction denied.");
        }

        double newBal = currentBal - amount;
        String type = (String) account.get("account_type");
        double minBal = "SAVINGS".equalsIgnoreCase(type) ? SAVINGS_MIN_BALANCE : CURRENT_MIN_BALANCE;
        String status = (newBal >= minBal) ? "ACTIVE" : "INACTIVE";

        accountDao.updateBalanceAndStatus(accountNumber, newBal, status);
        System.out.printf("\n>>> Withdrawal successful! Remaining Balance: INR %.2f (Status: %s) <<<%n", newBal, status);
        if ("INACTIVE".equalsIgnoreCase(status)) {
            System.out.println("--> [WARNING]: Balance fell below minimum required (INR " + minBal + "). Account is now INACTIVE.");
        }
    }

    public void viewCustomerAccounts(String customerId) {
        List<Map<String, Object>> list = accountDao.getAccountsByCustomerId(customerId);
        if (list == null || list.isEmpty()) {
            throw new AccountNotFoundException("No bank accounts linked to your profile. Please visit your branch to open an account.");
        }

        List<String> headers = List.of("ACCOUNT NUMBER", "ACCOUNT TYPE", "BALANCE (INR)", "OPENING DATE", "STATUS");
        List<List<String>> rows = new ArrayList<>();
        for (Map<String, Object> a : list) {
            rows.add(List.of(
                    String.valueOf(a.get("account_number")),
                    String.valueOf(a.get("account_type")),
                    String.format("%.2f", ((Number) a.get("balance")).doubleValue()),
                    String.valueOf(a.get("opening_date")),
                    String.valueOf(a.get("account_status"))
            ));
        }
        TableUtil.printTable("MY LINKED BANK ACCOUNTS", headers, rows);[cite: 4]
    }
}
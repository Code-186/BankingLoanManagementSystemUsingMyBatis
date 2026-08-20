package com.crimsonlogic.bankingandloanmanagementsystem.services.loanservice;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.crimsonlogic.bankingandloanmanagementsystem.dao.EmiDao;
import com.crimsonlogic.bankingandloanmanagementsystem.dao.LoanDao;
import com.crimsonlogic.bankingandloanmanagementsystem.entities.EMI;
import com.crimsonlogic.bankingandloanmanagementsystem.entities.Loan;
import com.crimsonlogic.bankingandloanmanagementsystem.exceptionhandling.LoanNotFoundException;
import com.crimsonlogic.bankingandloanmanagementsystem.utility.IdGeneratorUtil;
import com.crimsonlogic.bankingandloanmanagementsystem.utility.TableUtil;
import com.crimsonlogic.bankingandloanmanagementsystem.utility.ValidationUtil;

public class LoanService {

    private final LoanDao loanDao = new LoanDao();
    private final EmiDao emiDao = new EmiDao(); // Injected DAO

    public void approveLoan(Scanner scanner) {
        String loanId;
        while (true) {
            System.out.print("Enter Loan ID to Approve (e.g. LOAN0001): ");
            loanId = scanner.nextLine().trim();
            if (ValidationUtil.validateLoanIdFormat(loanId)) break;
            System.out.println("--> [Format Error]: Loan ID must be in format 'LOAN' followed by 4 digits (e.g. LOAN0001).");
        }

        Loan loan = loanDao.getLoanById(loanId);
        if (loan == null) {
            throw new LoanNotFoundException("Loan with ID '" + loanId + "' does not exist in the database.");
        }

        if ("APPROVED".equalsIgnoreCase(loan.getStatus())) {
            System.out.println("--> Information: Loan " + loanId + " is already APPROVED.");
            return;
        }

        loanDao.updateLoanStatus(loanId, "APPROVED");

        // --- Auto-generate EMI Schedule in DB ---
        double monthlyEmi = loan.calculateMonthlyEmi();
        LocalDate nextDueDate = LocalDate.now().plusMonths(1);

        for (int i = 1; i <= loan.getTenureMonths(); i++) {
            String emiId = "EMI" + String.format("%04d", (int) (Math.random() * 9000 + 1000));
            EMI emi = new EMI(emiId, loanId, Math.round(monthlyEmi * 100.0) / 100.0, nextDueDate, false, "PENDING");
            emiDao.insertEmi(emi);
            nextDueDate = nextDueDate.plusMonths(1);
        }

        System.out.println("\n>>> Loan " + loanId + " has been successfully APPROVED! <<<");
        System.out.printf("--> %d Monthly EMIs of INR %.2f generated & scheduled.%n", loan.getTenureMonths(), monthlyEmi);
    }

    public void rejectLoan(Scanner scanner) {
        String loanId;
        while (true) {
            System.out.print("Enter Loan ID to Reject (e.g. LOAN0001): ");
            loanId = scanner.nextLine().trim();
            if (ValidationUtil.validateLoanIdFormat(loanId)) break;
            System.out.println("--> [Format Error]: Loan ID must be in format 'LOAN' followed by 4 digits (e.g. LOAN0001).");
        }

        Loan loan = loanDao.getLoanById(loanId);
        if (loan == null) {
            throw new LoanNotFoundException("Loan with ID '" + loanId + "' does not exist in the database.");
        }

        if ("REJECTED".equalsIgnoreCase(loan.getStatus())) {
            System.out.println("--> Information: Loan " + loanId + " is already REJECTED.");
            return;
        }

        loanDao.updateLoanStatus(loanId, "REJECTED");
        System.out.println("\n>>> Loan " + loanId + " has been REJECTED. <<<");
    }

    public void findLoan(Scanner scanner) {
        String loanId;
        while (true) {
            System.out.print("Enter Loan ID to search (e.g. LOAN0001): ");
            loanId = scanner.nextLine().trim();
            if (ValidationUtil.validateLoanIdFormat(loanId)) break;
            System.out.println("--> [Format Error]: Loan ID must be in format 'LOAN' followed by 4 digits (e.g. LOAN0001).");
        }

        Loan loan = loanDao.getLoanById(loanId);
        if (loan == null) {
            throw new LoanNotFoundException("No loan found matching ID: " + loanId);
        }

        renderLoanTable("LOAN SEARCH RESULT - " + loanId, List.of(loan));
    }

    public void viewAllLoans() {
        List<Loan> loans = loanDao.getAllLoans();
        if (loans == null || loans.isEmpty()) {
            throw new LoanNotFoundException("No loan applications found in the database.");
        }
        renderLoanTable("ALL SYSTEM LOANS", loans);
    }

    public void viewPendingLoans() {
        List<Loan> loans = loanDao.getLoansByStatus("PENDING");
        if (loans == null || loans.isEmpty()) {
            throw new LoanNotFoundException("No PENDING loan applications found.");
        }
        renderLoanTable("PENDING LOAN APPLICATIONS", loans);
    }

    public void viewApprovedLoans() {
        List<Loan> loans = loanDao.getLoansByStatus("APPROVED");
        if (loans == null || loans.isEmpty()) {
            throw new LoanNotFoundException("No APPROVED loans found.");
        }
        renderLoanTable("APPROVED ACTIVE LOANS", loans);
    }

    public void applyLoan(Scanner scanner, String customerId) {
        System.out.println("\n=== APPLY FOR A NEW LOAN ===");
        System.out.println("1. Personal Loan (Rate: 11.5%)");
        System.out.println("2. Home Loan (Rate: 8.5%)");
        System.out.println("3. Vehicle Loan (Rate: 9.25%)");
        System.out.println("4. Education Loan (Rate: 7.8%)");

        String loanType = "Personal Loan";
        double interestRate = 11.5;

        while (true) {
            System.out.print("Select Loan Product (1-4): ");
            String choice = scanner.nextLine().trim();
            if ("1".equals(choice)) { loanType = "Personal Loan"; interestRate = 11.5; break; }
            if ("2".equals(choice)) { loanType = "Home Loan"; interestRate = 8.5; break; }
            if ("3".equals(choice)) { loanType = "Vehicle Loan"; interestRate = 9.25; break; }
            if ("4".equals(choice)) { loanType = "Education Loan"; interestRate = 7.8; break; }
            System.out.println("--> Invalid choice! Select 1, 2, 3, or 4.");
        }

        double loanAmount;
        while (true) {
            System.out.print("Enter Loan Amount (INR 10,000 to 50,00,000): INR ");
            try {
                loanAmount = Double.parseDouble(scanner.nextLine().trim());
                if (loanAmount >= 10000.0 && loanAmount <= 5000000.0) break;
                System.out.println("--> Amount must be between INR 10,000 and INR 50,00,000. Try again.");
            } catch (NumberFormatException e) {
                System.out.println("--> Invalid number format. Try again.");
            }
        }

        int tenureMonths;
        while (true) {
            System.out.print("Enter Loan Tenure in Months (6 to 240): ");
            try {
                tenureMonths = Integer.parseInt(scanner.nextLine().trim());
                if (tenureMonths >= 6 && tenureMonths <= 240) break;
                System.out.println("--> Tenure must be between 6 and 240 months. Try again.");
            } catch (NumberFormatException e) {
                System.out.println("--> Invalid number format. Try again.");
            }
        }

        String loanId = IdGeneratorUtil.generateLoanId();
        Loan loan = new Loan(loanId, customerId, loanType, loanAmount, interestRate, tenureMonths, "PENDING");

        if (loanDao.insertLoan(loan)) {
            System.out.println("\n>>> LOAN APPLICATION SUBMITTED SUCCESSFULLY! <<<");
            System.out.printf("Generated Loan ID: %s | Estimated Monthly EMI: INR %.2f%n", loanId, loan.calculateMonthlyEmi());
            System.out.println("Status: PENDING review by the Loan Manager.");
        } else {
            System.out.println("--> Application submission failed.");
        }
    }

    public void viewLoansByCustomer(String customerId) {
        List<Loan> loans = loanDao.getLoansByCustomerId(customerId);
        if (loans == null || loans.isEmpty()) {
            throw new LoanNotFoundException("You have not applied for any loans yet.");
        }
        renderLoanTable("MY LOAN APPLICATIONS", loans);
    }

    // Displays real EMI records joined with Customer details
    public void viewEMIs(String customerId) {
        List<EMI> emis = emiDao.getEmisByCustomerId(customerId);
        if (emis == null || emis.isEmpty()) {
            throw new LoanNotFoundException("No EMI schedules found for your approved loans.");
        }

        List<String> headers = List.of("EMI ID", "CUSTOMER NAME", "EMAIL", "LOAN ID", "AMOUNT (INR)", "DUE DATE", "STATUS");
        List<List<String>> rows = new ArrayList<>();
        for (EMI e : emis) {
            rows.add(List.of(
                    e.getEmiId(),
                    e.getCustomerName() != null ? e.getCustomerName() : "N/A",
                    e.getCustomerEmail() != null ? e.getCustomerEmail() : "N/A",
                    e.getLoanId(),
                    String.format("%.2f", e.getEmiAmount()),
                    e.getDueDate().toString(),
                    e.getStatus()
            ));
        }
        TableUtil.printTable("CUSTOMER EMI REPAYMENT SCHEDULE", headers, rows);
    }

    public void payEMI(Scanner scanner, String customerId) {
        List<EMI> emis = emiDao.getEmisByCustomerId(customerId);
        List<EMI> pendingEmis = (emis != null) 
                ? emis.stream().filter(e -> !e.isPaid()).toList() 
                : List.of();

        if (pendingEmis.isEmpty()) {
            throw new LoanNotFoundException("No pending EMI installments available for repayment.");
        }

        List<String> headers = List.of("EMI ID", "LOAN ID", "DUE DATE", "AMOUNT (INR)", "STATUS");
        List<List<String>> rows = new ArrayList<>();
        for (EMI e : pendingEmis) {
            rows.add(List.of(e.getEmiId(), e.getLoanId(), e.getDueDate().toString(), String.format("%.2f", e.getEmiAmount()), e.getStatus()));
        }
        TableUtil.printTable("PENDING EMIS FOR REPAYMENT", headers, rows);

        System.out.print("\nEnter EMI ID to pay (e.g. EMI1234): ");
        String emiId = scanner.nextLine().trim();

        EMI targetEmi = pendingEmis.stream().filter(e -> e.getEmiId().equalsIgnoreCase(emiId)).findFirst().orElse(null);
        if (targetEmi == null) {
            System.out.println("--> Invalid EMI ID selected.");
            return;
        }

        emiDao.updateEmiPaidStatus(targetEmi.getEmiId());
        System.out.printf("\n>>> EMI %s of INR %.2f PAID SUCCESSFULLY! <<<%n", targetEmi.getEmiId(), targetEmi.getEmiAmount());
    }

    public void renderLoanTable(String title, List<Loan> loans) {
        List<String> headers = List.of("LOAN ID", "CUSTOMER ID", "TYPE", "AMOUNT (INR)", "RATE (%)", "TENURE", "MONTHLY EMI", "STATUS");
        List<List<String>> rows = new ArrayList<>();
        if (loans != null) {
            for (Loan l : loans) {
                rows.add(List.of(
                        l.getLoanId(),
                        l.getCustomerId(),
                        l.getLoanType(),
                        String.format("%.2f", l.getLoanAmount()),
                        String.format("%.2f", l.getInterestRate()),
                        l.getTenureMonths() + " Months",
                        String.format("%.2f", l.calculateMonthlyEmi()),
                        l.getStatus()
                ));
            }
        }
        TableUtil.printTable(title, headers, rows);
    }
}
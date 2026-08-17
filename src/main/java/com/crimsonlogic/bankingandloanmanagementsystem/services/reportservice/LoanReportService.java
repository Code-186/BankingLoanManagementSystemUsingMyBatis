package com.crimsonlogic.bankingandloanmanagementsystem.services.reportservice;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.crimsonlogic.bankingandloanmanagementsystem.dao.LoanDao;
import com.crimsonlogic.bankingandloanmanagementsystem.entities.Loan;
import com.crimsonlogic.bankingandloanmanagementsystem.exceptionhandling.LoanNotFoundException;
import com.crimsonlogic.bankingandloanmanagementsystem.services.loanservice.LoanService;

public class LoanReportService {

    private final LoanDao loanDao = new LoanDao();
    private final LoanService loanService = new LoanService();

    public void generateLoanSummaryReport() {
        List<Loan> loans = loanDao.getAllLoans();
        if (loans == null || loans.isEmpty()) {
            throw new LoanNotFoundException("No loan records available for summary report.");
        }

        long total = loans.size();
        long approved = loans.stream().filter(l -> "APPROVED".equalsIgnoreCase(l.getStatus())).count();
        long pending = loans.stream().filter(l -> "PENDING".equalsIgnoreCase(l.getStatus())).count();
        long rejected = loans.stream().filter(l -> "REJECTED".equalsIgnoreCase(l.getStatus())).count();
        double totalDisbursed = loans.stream().filter(l -> "APPROVED".equalsIgnoreCase(l.getStatus()))
                .mapToDouble(Loan::getLoanAmount).sum();

        System.out.println("\n==========================================");
        System.out.println("           LOAN SUMMARY REPORT            ");
        System.out.println("==========================================");
        System.out.println("Total Applications : " + total);
        System.out.println("Approved Loans     : " + approved);
        System.out.println("Pending Loans      : " + pending);
        System.out.println("Rejected Loans     : " + rejected);
        System.out.printf("Total Disbursed    : INR %.2f%n", totalDisbursed);
        System.out.println("==========================================");
    }

    public void displayHighestLoan() {
        List<Loan> loans = loanDao.getAllLoans();
        if (loans == null || loans.isEmpty()) throw new LoanNotFoundException("No loans found.");

        loans.stream()
                .max(Comparator.comparingDouble(Loan::getLoanAmount))
                .ifPresent(l -> loanService.renderLoanTable("HIGHEST LOAN DISBURSED/REQUESTED", List.of(l)));
    }

    public void displayLowestLoan() {
        List<Loan> loans = loanDao.getAllLoans();
        if (loans == null || loans.isEmpty()) throw new LoanNotFoundException("No loans found.");

        loans.stream()
                .min(Comparator.comparingDouble(Loan::getLoanAmount))
                .ifPresent(l -> loanService.renderLoanTable("LOWEST LOAN REQUESTED", List.of(l)));
    }

    public void displayDistinctLoanTypes() {
        List<Loan> loans = loanDao.getAllLoans();
        if (loans == null || loans.isEmpty()) throw new LoanNotFoundException("No loans found.");

        List<String> types = loans.stream().map(Loan::getLoanType).distinct().toList();
        System.out.println("\n--- DISTINCT LOAN PRODUCTS IN SYSTEM ---");
        types.forEach(t -> System.out.println("• " + t));
    }

    public void displayLoanCountsPerCustomer() {
        List<Loan> loans = loanDao.getAllLoans();
        if (loans == null || loans.isEmpty()) throw new LoanNotFoundException("No loans found.");

        Map<String, Long> counts = loans.stream()
                .collect(Collectors.groupingBy(Loan::getCustomerId, Collectors.counting()));

        System.out.println("\n--- LOAN APPLICATION COUNT PER CUSTOMER ---");
        counts.forEach((custId, count) -> System.out.printf("Customer ID: %-10s -> %d Loan(s)%n", custId, count));
    }

    public void displayLoansGroupedByStatus() {
        List<Loan> loans = loanDao.getAllLoans();
        if (loans == null || loans.isEmpty()) throw new LoanNotFoundException("No loans found.");

        Map<String, List<Loan>> grouped = loans.stream().collect(Collectors.groupingBy(Loan::getStatus));
        grouped.forEach((status, list) -> loanService.renderLoanTable("LOANS IN STATUS: " + status, list));
    }

    public String anyPendingLoan() {
        List<Loan> loans = loanDao.getAllLoans();
        boolean exists = loans != null && loans.stream().anyMatch(l -> "PENDING".equalsIgnoreCase(l.getStatus()));
        return exists ? "--> [STATUS]: Pending loan applications are currently awaiting review."
                      : "--> [STATUS]: No pending loan applications in the pipeline.";
    }

    public String allLoansApproved() {
        List<Loan> loans = loanDao.getAllLoans();
        if (loans == null || loans.isEmpty()) return "--> [STATUS]: No loan applications present.";
        boolean all = loans.stream().allMatch(l -> "APPROVED".equalsIgnoreCase(l.getStatus()));
        return all ? "--> [STATUS]: All submitted loan applications are currently APPROVED."
                   : "--> [STATUS]: Portfolio contains non-approved (PENDING/REJECTED) loans.";
    }
}
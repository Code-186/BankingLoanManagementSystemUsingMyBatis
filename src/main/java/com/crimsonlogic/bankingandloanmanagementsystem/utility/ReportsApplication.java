package com.crimsonlogic.bankingandloanmanagementsystem.utility;

import java.util.Scanner;

import com.crimsonlogic.bankingandloanmanagementsystem.exceptionhandling.LoanNotFoundException;
import com.crimsonlogic.bankingandloanmanagementsystem.services.reportservice.LoanReportService;

public class ReportsApplication {

    private final MenuUtil menuUtil;
    private final Scanner scanner;
    private final LoanReportService loanReportService;

    public ReportsApplication(MenuUtil menuUtil, Scanner scanner, LoanReportService loanReportService) {
        this.menuUtil = menuUtil;
        this.scanner = scanner;
        this.loanReportService = loanReportService;
    }

    public void start() {
        int choice = 0;
        do {
            menuUtil.showReportsMenu();[cite: 11]
            System.out.print("Enter Choice: ");
            String input = scanner.nextLine().trim();
            try {
                choice = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Invalid Choice.");
                continue;
            }

            switch (choice) {
                case 1:
                    System.out.println("\n--> [Notice]: Customer Reports module is reserved for upcoming audit releases.");
                    break;
                case 2:
                    System.out.println("\n--> [Notice]: Account Reports module is reserved for upcoming audit releases.");
                    break;
                case 3:
                    loanReports();
                    break;
                case 4:
                    System.out.println("\n--> [Notice]: Transaction Reports module is inactive for this release.");
                    break;
                case 5:
                    System.out.println("\n--> [Notice]: Analytics Reports module is inactive for this release.");
                    break;
                case 6:
                    break;
                default:
                    System.out.println("Invalid Choice (1-6).");
            }
        } while (choice != 6);
    }

    private void loanReports() {
        int choice = 0;
        do {
            menuUtil.showLoanReportsMenu();[cite: 11]
            System.out.print("Enter Choice: ");
            String input = scanner.nextLine().trim();
            try {
                choice = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Invalid Choice.");
                continue;
            }

            try {
                switch (choice) {
                    case 1:
                        loanReportService.generateLoanSummaryReport();
                        break;
                    case 2:
                        loanReportService.displayHighestLoan();
                        break;
                    case 3:
                        loanReportService.displayLowestLoan();
                        break;
                    case 4:
                        loanReportService.displayDistinctLoanTypes();
                        break;
                    case 5:
                        loanReportService.displayLoanCountsPerCustomer();
                        break;
                    case 6:
                        loanReportService.displayLoansGroupedByStatus();
                        break;
                    case 7:
                        System.out.println(loanReportService.anyPendingLoan());
                        break;
                    case 8:
                        System.out.println(loanReportService.allLoansApproved());
                        break;
                    case 9:
                    case 10:
                        break;
                    default:
                        System.out.println("Invalid Choice (1-10).");
                }
            } catch (LoanNotFoundException e) {
                System.out.println("--> [Error]: " + e.getMessage());
            }
        } while (choice != 10 && choice != 9);
    }
}
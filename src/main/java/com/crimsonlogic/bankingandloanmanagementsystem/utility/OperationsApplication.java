package com.crimsonlogic.bankingandloanmanagementsystem.utility;

import java.util.Scanner;

import com.crimsonlogic.bankingandloanmanagementsystem.exceptionhandling.LoanNotFoundException;
import com.crimsonlogic.bankingandloanmanagementsystem.services.loanservice.LoanService;

public class OperationsApplication {

    private final MenuUtil menuUtil;
    private final Scanner scanner;
    private final LoanService loanService;

    public OperationsApplication(MenuUtil menuUtil, Scanner scanner, LoanService loanService) {
        this.menuUtil = menuUtil;
        this.scanner = scanner;
        this.loanService = loanService;
    }

    public void loanManagementMenu() {
        int loanChoice = 0;
        do {
            menuUtil.showAdminLoanManagementMenu();[cite: 11]
            System.out.print("Enter Choice: ");
            String input = scanner.nextLine().trim();
            try {
                loanChoice = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Invalid Choice. Enter a number.");
                continue;
            }

            try {
                switch (loanChoice) {
                    case 1:
                        loanService.approveLoan(scanner);
                        break;
                    case 2:
                        loanService.rejectLoan(scanner);
                        break;
                    case 3:
                        loanService.findLoan(scanner);
                        break;
                    case 4:
                        loanService.viewAllLoans();
                        break;
                    case 5:
                        loanService.viewPendingLoans();
                        break;
                    case 6:
                        loanService.viewApprovedLoans();
                        break;
                    case 7:
                        break;
                    default:
                        System.out.println("Invalid Choice (1-7).");
                }
            } catch (LoanNotFoundException e) {
                System.out.println("--> [Error]: " + e.getMessage());
            }
        } while (loanChoice != 7);
    }

    public void customerLoanMenu(String customerId) {
        int choice = 0;
        do {
            menuUtil.customerLoanMenu();[cite: 11]
            System.out.print("Enter Choice: ");
            String input = scanner.nextLine().trim();
            try {
                choice = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Invalid Choice. Enter a number.");
                continue;
            }

            try {
                switch (choice) {
                    case 1:
                        loanService.applyLoan(scanner, customerId);
                        break;
                    case 2:
                        loanService.viewLoansByCustomer(customerId);
                        break;
                    case 3:
                        loanService.viewEMIs(customerId);
                        break;
                    case 4:
                        loanService.payEMI(scanner, customerId);
                        break;
                    case 5:
                        break;
                    default:
                        System.out.println("Invalid Choice (1-5).");
                }
            } catch (LoanNotFoundException e) {
                System.out.println("--> [Error]: " + e.getMessage());
            }
        } while (choice != 5);
    }
}
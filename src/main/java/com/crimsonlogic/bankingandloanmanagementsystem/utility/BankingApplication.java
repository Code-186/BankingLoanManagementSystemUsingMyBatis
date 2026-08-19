package com.crimsonlogic.bankingandloanmanagementsystem.utility;

import java.util.Scanner;

import com.crimsonlogic.bankingandloanmanagementsystem.exceptionhandling.AccountInactiveException;
import com.crimsonlogic.bankingandloanmanagementsystem.exceptionhandling.AccountNotFoundException;
import com.crimsonlogic.bankingandloanmanagementsystem.exceptionhandling.AdminNotFoundException;
import com.crimsonlogic.bankingandloanmanagementsystem.exceptionhandling.CustomerNotFoundException;
import com.crimsonlogic.bankingandloanmanagementsystem.exceptionhandling.EmployeeNotFoundException;
import com.crimsonlogic.bankingandloanmanagementsystem.exceptionhandling.InsufficientBalanceException;
import com.crimsonlogic.bankingandloanmanagementsystem.exceptionhandling.InvalidMPINException;
import com.crimsonlogic.bankingandloanmanagementsystem.model.implementations.userimplementations.Admin;
import com.crimsonlogic.bankingandloanmanagementsystem.model.implementations.userimplementations.Customer;
import com.crimsonlogic.bankingandloanmanagementsystem.model.implementations.userimplementations.Employee;
import com.crimsonlogic.bankingandloanmanagementsystem.services.authenticationservice.AuthenticationService;
import com.crimsonlogic.bankingandloanmanagementsystem.services.bankingservice.AccountService;
import com.crimsonlogic.bankingandloanmanagementsystem.services.bankingservice.CustomerService;
import com.crimsonlogic.bankingandloanmanagementsystem.services.bankingservice.UserService;
import com.crimsonlogic.bankingandloanmanagementsystem.services.loanservice.LoanService;
import com.crimsonlogic.bankingandloanmanagementsystem.services.reportservice.LoanReportService;

public class BankingApplication {

    private final MenuUtil menuUtil = new MenuUtil();
    private final Scanner scanner = new Scanner(System.in);
    private final AuthenticationService authenticationService = new AuthenticationService();
    private final CustomerService customerService = new CustomerService();
    private final AccountService accountService = new AccountService();
    private final LoanService loanService = new LoanService();
    private final UserService userService = new UserService();
    private final LoanReportService loanReportService = new LoanReportService();

    private final OperationsApplication operationsApplication = new OperationsApplication(menuUtil, scanner, loanService);
    private final ReportsApplication reportsApplication = new ReportsApplication(menuUtil, scanner, loanReportService);

    public void start() {
        int choice = 0;
        do {
            menuUtil.showMainMenu();
            System.out.print("Enter Choice : ");
            String input = scanner.nextLine().trim();
            try {
                choice = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Invalid Choice.");
                continue;
            }

            switch (choice) {
                case 1:
                    handleAdminLogin();
                    break;
                case 2:
                    handleEmployeeLogin();
                    break;
                case 3:
                    handleCustomerFlow();
                    break;
                case 4:
                    System.out.println("\nThank you for using the Banking & Loan Management System.");
                    break;
                default:
                    System.out.println("Invalid Choice.");
            }
        } while (choice != 4);
    }
    private void handleAdminLogin() {
        System.out.println("\n=== ADMIN LOGIN ===");
        String email;
        while (true) {
            System.out.print("Enter Admin Email (e.g. admin@sbi.co.in): ");
            email = scanner.nextLine().trim();
            if (ValidationUtil.validateEmail(email)) break;
            System.out.println("--> [Format Error]: Invalid email format! Please enter in format 'name@bank.co.in'.");
        }

        int attemptsLeft = 3;
        while (attemptsLeft > 0) {
            System.out.print("Enter Password : ");
            String password = scanner.nextLine().trim();

            try {
                Admin admin = authenticationService.loginAdmin(email, password);
                if (admin != null) {
                    System.out.println("\n>>> Admin Login Successful! Welcome " + admin.getName() + " (" + admin.getBankName() + ") <<<");
                    adminMenu(admin);
                    return;
                }
                attemptsLeft--;
                if (attemptsLeft > 0) {
                    System.out.printf("--> Invalid Password! %d attempt(s) remaining. Try again.%n", attemptsLeft);
                } else {
                    System.out.println("--> [Access Denied]: Maximum 3 attempts exceeded. Returning to main menu.");
                }
            } catch (AdminNotFoundException e) {
                System.out.println("--> [Error]: " + e.getMessage());
                return;
            }
        }
    }

    private void handleEmployeeLogin() {
        System.out.println("\n=== EMPLOYEE LOGIN ===");
        String email;
        while (true) {
            System.out.print("Enter Official Email (e.g. emp@sbi.co.in): ");
            email = scanner.nextLine().trim();
            if (ValidationUtil.validateEmail(email)) break;
            System.out.println("--> [Format Error]: Invalid email format! Please enter in format 'name@bank.co.in'.");
        }

        int attemptsLeft = 3;
        while (attemptsLeft > 0) {
            System.out.print("Enter Password : ");
            String password = scanner.nextLine().trim();

            try {
                Employee emp = authenticationService.loginEmployee(email, password);
                if (emp != null) {
                    System.out.println("\n>>> Employee Login Successful! Welcome " + emp.getName() + " (" + emp.getBankName() + ") <<<");
                    employeeMenu(emp);
                    return;
                }
                attemptsLeft--;
                if (attemptsLeft > 0) {
                    System.out.printf("--> Invalid Password! %d attempt(s) remaining. Try again.%n", attemptsLeft);
                } else {
                    System.out.println("--> [Access Denied]: Maximum 3 attempts exceeded. Returning to main menu.");
                }
            } catch (EmployeeNotFoundException e) {
                System.out.println("--> [Error]: " + e.getMessage());
                return;
            }
        }
    }

    private void handleCustomerFlow() {
        System.out.println("\n1. Customer Login");
        System.out.println("2. Customer Self-Registration");
        System.out.print("Enter Choice (1-2): ");
        String ch = scanner.nextLine().trim();

        if ("2".equals(ch)) {
            customerService.registerCustomer(scanner, null);
            return;
        }

        System.out.println("\n=== CUSTOMER LOGIN ===");
        String email;
        while (true) {
            System.out.print("Enter Registered Email (e.g. user@gmail.com): ");
            email = scanner.nextLine().trim();
            if (ValidationUtil.validateEmail(email)) break;
            System.out.println("--> [Format Error]: Invalid email format! Please enter in format 'user@example.com'.");
        }

        int attemptsLeft = 3;
        while (attemptsLeft > 0) {
            System.out.print("Enter Password : ");
            String password = scanner.nextLine().trim();

            try {
                Customer cust = authenticationService.loginCustomer(email, password);
                if (cust != null) {
                    System.out.println("\n>>> Customer Login Successful! Welcome " + cust.getName() + " (" + cust.getBankName() + ") <<<");
                    customerMenu(cust);
                    return;
                }
                attemptsLeft--;
                if (attemptsLeft > 0) {
                    System.out.printf("--> Invalid Password! %d attempt(s) remaining. Try again.%n", attemptsLeft);
                } else {
                    System.out.println("--> [Access Denied]: Maximum 3 attempts exceeded. Returning to main menu.");
                }
            } catch (CustomerNotFoundException e) {
                System.out.println("--> [Error]: " + e.getMessage());
                return;
            }
        }
    }
    

    private void adminMenu(Admin admin) {
        int adminChoice = 0;
        do {
            menuUtil.showAdminMenu();
            System.out.print("Enter Admin Choice : ");
            String input = scanner.nextLine().trim();
            try {
                adminChoice = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                continue;
            }

            try {
                switch (adminChoice) {
                    case 1:
                        employeeManagementMenu(admin);
                        break;
                    case 2:
                        customerService.deleteCustomer(scanner);
                        break;
                    case 3:
                        viewManagementMenu();
                        break;
                    case 4:
                        operationsApplication.loanManagementMenu();
                        break;
                    case 5:
                        reportsApplication.start();
                        break;
                    case 6:
                        break;
                    default:
                        System.out.println("Invalid Choice.");
                }
            } catch (Exception e) {
                System.out.println("--> [Error]: " + e.getMessage());
            }
        } while (adminChoice != 6);
    }

    private void employeeManagementMenu(Admin admin) {
        int choice = 0;
        do {
            menuUtil.showEmployeeManagementMenu();
            System.out.print("Enter Choice : ");
            String input = scanner.nextLine().trim();
            try {
                choice = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                continue;
            }

            try {
                switch (choice) {
                    case 1:
                        userService.registerEmployee(scanner, admin);
                        break;
                    case 2:
                        userService.deleteEmployee(scanner);
                        break;
                    case 3:
                        break;
                    default:
                        System.out.println("Invalid Choice.");
                }
            } catch (EmployeeNotFoundException e) {
                System.out.println("--> [Error]: " + e.getMessage());
            }
        } while (choice != 3);
    }

    private void viewManagementMenu() {
        int choice = 0;
        do {
            menuUtil.showViewManagementMenu();
            System.out.print("Enter Choice : ");
            String input = scanner.nextLine().trim();
            try {
                choice = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                continue;
            }

            try {
                switch (choice) {
                    case 1:
                        userService.viewAllAdmins();
                        break;
                    case 2:
                        userService.viewEmployee(scanner);
                        break;
                    case 3:
                        userService.viewAllEmployees();
                        break;
                    case 4:
                        customerService.viewCustomer(scanner, null);
                        break;
                    case 5:
                        customerService.viewAllCustomers();
                        break;
                    case 6:
                        break;
                    default:
                        System.out.println("Invalid Choice.");
                }
            } catch (Exception e) {
                System.out.println("--> [Error]: " + e.getMessage());
            }
        } while (choice != 6);
    }

    private void employeeMenu(Employee emp) {
        int employeeChoice = 0;
        do {
            menuUtil.showEmployeeMenu();
            System.out.print("Enter Employee Choice : ");
            String input = scanner.nextLine().trim();
            try {
                employeeChoice = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                continue;
            }

            try {
                switch (employeeChoice) {
                    case 1:
                        customerService.registerCustomer(scanner, emp);
                        break;
                    case 2:
                        customerService.viewCustomer(scanner, emp.getBranchId());
                        break;
                    case 3:
                        customerService.viewBranchCustomers(emp.getBranchId());
                        break;
                    case 4:
                        accountService.openAccount(scanner, emp);
                        break;
                    case 5:
                        accountService.deposit(scanner);
                        break;
                    case 6:
                        accountService.withdraw(scanner);
                        break;
                    case 7:
                        System.out.println("--> [Notice]: Beneficiary Management is reserved for subsequent releases.");
                        break;
                    case 8:
                        break;
                    default:
                        System.out.println("Invalid Choice.");
                }
            } catch (CustomerNotFoundException | AccountNotFoundException | AccountInactiveException 
                     | InsufficientBalanceException | InvalidMPINException e) {
                System.out.println("--> [Error]: " + e.getMessage());
            }
        } while (employeeChoice != 8);
    }

    private void customerMenu(Customer cust) {
        int customerChoice = 0;
        do {
            menuUtil.showCustomerMenu();
            System.out.print("Enter Customer Choice : ");
            String input = scanner.nextLine().trim();
            try {
                customerChoice = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                continue;
            }

            try {
                switch (customerChoice) {
                    case 1:
                        accountService.viewCustomerAccounts(cust.getCustomerId());
                        break;
                    case 2:
                        System.out.println("--> [Notice]: Beneficiary Management is reserved.");
                        break;
                    case 3:
                        System.out.println("--> [Notice]: Transfer Funds module is reserved.");
                        break;
                    case 4:
                        System.out.println("--> [Notice]: View Transactions is reserved.");
                        break;
                    case 5:
                        System.out.println("--> [Notice]: Download Statement is reserved.");
                        break;
                    case 6:
                        operationsApplication.customerLoanMenu(cust.getCustomerId());
                        break;
                    case 7:
                        updateCustomerMenu(cust);
                        break;
                    case 8:
                        break;
                    default:
                        System.out.println("Invalid Choice.");
                }
            } catch (Exception e) {
                System.out.println("--> [Error]: " + e.getMessage());
            }
        } while (customerChoice != 8);
    }

    private void updateCustomerMenu(Customer cust) {
        int choice = 0;
        do {
            System.out.println("\n===== UPDATE PROFILE =====");
            System.out.println("1. Update Phone Number");
            System.out.println("2. Update Address");
            System.out.println("3. Update Nominee Details");
            System.out.println("4. Change Password");
            System.out.println("5. Back");
            System.out.print("Enter Choice : ");
            String input = scanner.nextLine().trim();
            try {
                choice = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                continue;
            }

            switch (choice) {
                case 1:
                    customerService.updateCustomerPhone(scanner, cust);
                    break;
                case 2:
                    customerService.updateCustomerAddress(scanner, cust);
                    break;
                case 3:
                    customerService.updateNomineeDetails(scanner, cust);
                    break;
                case 4:
                    customerService.changePassword(scanner, cust);
                    break;
                case 5:
                    break;
                default:
                    System.out.println("Invalid Choice.");
            }
        } while (choice != 5);
    }
}
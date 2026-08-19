package com.crimsonlogic.bankingandloanmanagementsystem.services.bankingservice;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.crimsonlogic.bankingandloanmanagementsystem.dao.CustomerDao;
import com.crimsonlogic.bankingandloanmanagementsystem.exceptionhandling.CustomerNotFoundException;
import com.crimsonlogic.bankingandloanmanagementsystem.model.implementations.userimplementations.Customer;
import com.crimsonlogic.bankingandloanmanagementsystem.model.implementations.userimplementations.Employee;
import com.crimsonlogic.bankingandloanmanagementsystem.utility.BranchSelectionUtil;
import com.crimsonlogic.bankingandloanmanagementsystem.utility.IdGeneratorUtil;
import com.crimsonlogic.bankingandloanmanagementsystem.utility.PasswordUtil;
import com.crimsonlogic.bankingandloanmanagementsystem.utility.TableUtil;
import com.crimsonlogic.bankingandloanmanagementsystem.utility.ValidationUtil;

public class CustomerService {

    private final CustomerDao customerDao = new CustomerDao();

    public void registerCustomer(Scanner scanner, Employee employee) {
        String bankName;
        String branchId;

        if (employee != null) {
            bankName = employee.getBankName();
            branchId = employee.getBranchId();
            System.out.println("\n=== REGISTER CUSTOMER FOR " + bankName.toUpperCase() + " (" + branchId + ") ===");
        } else {
            System.out.println("\n=== CUSTOMER SELF-REGISTRATION ===");
            BranchSelectionUtil.BankBranch bb = BranchSelectionUtil.selectBankBranch(scanner);
            bankName = bb.getBankName();
            branchId = bb.getBranchId();
        }

        String name;
        while (true) {
            System.out.print("Enter Full Name (e.g. John Doe): ");
            name = scanner.nextLine().trim();
            if (ValidationUtil.validateName(name)) break;
            System.out.println("--> [Format Error]: Name must contain only alphabets (min 3 chars, no repeating sequences). Please enter in format 'FirstName LastName'.");
        }

        String email;
        while (true) {
            System.out.print("Enter Email ID (e.g. user@domain.com): ");
            email = scanner.nextLine().trim();
            if (ValidationUtil.validateEmail(email)) break;
            System.out.println("--> [Format Error]: Invalid Email! Please enter in standard format 'name@example.com'.");
        }

        String phone;
        while (true) {
            System.out.print("Enter 10-Digit Phone Number (e.g. 9876543210): ");
            phone = scanner.nextLine().trim();
            if (ValidationUtil.validatePhone(phone)) break;
            System.out.println("--> [Format Error]: Phone number must start with 6, 7, 8, or 9 and have exactly 10 digits.");
        }

        String password;
        while (true) {
            System.out.println("\n--> Password Policy: Length 8-20 characters, containing at least 1 uppercase letter, 1 lowercase letter, 1 digit, and 1 special character (@#$%^&+=!).");
            System.out.print("Enter Password (e.g. Cust@1234): ");
            password = scanner.nextLine().trim();
            if (ValidationUtil.validatePassword(password)) {
                break;
            }
            System.out.println("--> [Format Error]: Password does not meet criteria. Minimum 8 characters, with uppercase, lowercase, digit, and special symbol required.");
        }

        LocalDate dob;
        while (true) {
            System.out.print("Enter Date of Birth (dd/MM/yyyy, e.g. 15/08/1998): ");
            dob = ValidationUtil.parseAndValidateDob(scanner.nextLine().trim());
            if (dob != null) break;
            System.out.println("--> [Format Error]: Invalid DOB! Format must be 'dd/MM/yyyy' and age must be at least 18 years.");
        }

        System.out.print("Enter Address (Optional, press Enter to skip): ");
        String address = scanner.nextLine().trim();

        System.out.print("Enter Nominee Name (Optional, press Enter to skip): ");
        String nomineeName = scanner.nextLine().trim();
        String nomineeRel = "";
        String nomineePhone = "";

        if (!nomineeName.isEmpty()) {
            System.out.print("Enter Nominee Relationship: ");
            nomineeRel = scanner.nextLine().trim();
            while (true) {
                System.out.print("Enter Nominee 10-Digit Phone: ");
                nomineePhone = scanner.nextLine().trim();
                if (ValidationUtil.validatePhone(nomineePhone)) break;
                System.out.println("--> [Format Error]: Nominee phone must start with 6-9 and have 10 digits.");
            }
        }

        String custId = IdGeneratorUtil.generateCustomerId();
        Customer customer = new Customer(custId, name, phone, email, address, PasswordUtil.hash(password),
                dob, bankName, "REGISTERED", branchId, nomineeName, nomineeRel, nomineePhone);

        if (customerDao.insertCustomer(customer)) {
            System.out.println("\n>>> CUSTOMER REGISTERED SUCCESSFULLY! <<<");
            renderCustomerTable("REGISTERED CUSTOMER DETAILS", List.of(customer));
            System.out.println("--> Status is 'REGISTERED'. Visit branch " + branchId + " to open your account.");
        } else {
            System.out.println("--> Registration failed. Email ID might already be registered in the system.");
        }
    }

    public void viewCustomer(Scanner scanner, String allowedBranchId) {
        String customerId;
        while (true) {
            System.out.print("Enter Customer ID (e.g. CUST0001): ");
            customerId = scanner.nextLine().trim();
            if (ValidationUtil.validateCustomerIdFormat(customerId)) break;
            System.out.println("--> [Format Error]: Customer ID must be in format 'CUST' followed by 4 digits (e.g. CUST0001).");
        }

        Customer customer = customerDao.getCustomerById(customerId);
        if (customer == null) {
            throw new CustomerNotFoundException("Customer with ID '" + customerId + "' was not found in the database.");
        }

        if (allowedBranchId != null && !customer.getBranchId().equalsIgnoreCase(allowedBranchId)) {
            throw new CustomerNotFoundException("Customer belongs to branch '" + customer.getBranchId() 
                    + "', not your assigned branch ('" + allowedBranchId + "').");
        }

        renderCustomerTable("CUSTOMER PROFILE", List.of(customer));
    }

    public void viewAllCustomers() {
        List<Customer> customers = customerDao.getAllCustomers();
        if (customers == null || customers.isEmpty()) {
            throw new CustomerNotFoundException("No customer records found in the database.");
        }
        renderCustomerTable("ALL REGISTERED CUSTOMERS", customers);
    }

    public void viewBranchCustomers(String branchId) {
        List<Customer> customers = customerDao.getCustomersByBranchId(branchId);
        if (customers == null || customers.isEmpty()) {
            throw new CustomerNotFoundException("No customers registered under branch " + branchId);
        }
        renderCustomerTable("BRANCH CUSTOMER DIRECTORY (" + branchId + ")", customers);
    }

    public void deleteCustomer(Scanner scanner) {
        String customerId;
        while (true) {
            System.out.print("Enter Customer ID to Deactivate (e.g. CUST0001): ");
            customerId = scanner.nextLine().trim();
            if (ValidationUtil.validateCustomerIdFormat(customerId)) break;
            System.out.println("--> [Format Error]: Customer ID must be in format 'CUST' followed by 4 digits (e.g. CUST0001).");
        }

        Customer customer = customerDao.getCustomerById(customerId);
        if (customer == null) {
            throw new CustomerNotFoundException("Customer with ID '" + customerId + "' was not found in the database.");
        }

        customerDao.updateCustomerStatus(customerId, "INACTIVE");
        System.out.println("--> Customer " + customerId + " status has been set to INACTIVE.");
    }

    public void updateCustomerPhone(Scanner scanner, Customer customer) {
        String phone;
        while (true) {
            System.out.print("Enter New 10-Digit Phone Number (e.g. 9876543210): ");
            phone = scanner.nextLine().trim();
            if (ValidationUtil.validatePhone(phone)) break;
            System.out.println("--> [Format Error]: Phone must start with 6-9 and have 10 digits.");
        }
        customerDao.updateCustomerPhone(customer.getCustomerId(), phone);
        customer.setPhoneNumber(phone);
        System.out.println(">>> Phone number updated successfully! <<<");
    }

    public void updateCustomerAddress(Scanner scanner, Customer customer) {
        System.out.print("Enter New Address: ");
        String address = scanner.nextLine().trim();
        customerDao.updateCustomerAddress(customer.getCustomerId(), address);
        customer.setAddress(address);
        System.out.println(">>> Address updated successfully! <<<");
    }

    public void updateNomineeDetails(Scanner scanner, Customer customer) {
        String name;
        while (true) {
            System.out.print("Enter Nominee Full Name: ");
            name = scanner.nextLine().trim();
            if (ValidationUtil.validateName(name)) break;
            System.out.println("--> [Format Error]: Invalid Name! Must be letters only (min 3 chars).");
        }

        System.out.print("Enter Nominee Relationship: ");
        String rel = scanner.nextLine().trim();

        String phone;
        while (true) {
            System.out.print("Enter Nominee 10-Digit Phone: ");
            phone = scanner.nextLine().trim();
            if (ValidationUtil.validatePhone(phone)) break;
            System.out.println("--> [Format Error]: Nominee phone must start with 6-9 and have 10 digits.");
        }

        customerDao.updateCustomerNominee(customer.getCustomerId(), name, rel, phone);
        customer.setNomineeName(name);
        customer.setNomineeRelationship(rel);
        customer.setNomineePhoneNumber(phone);
        System.out.println(">>> Nominee details updated successfully! <<<");
    }

    public void changePassword(Scanner scanner, Customer customer) {
        String newPass;
        while (true) {
            System.out.println("\n--> Password Policy: Length 8-20 characters, with uppercase, lowercase, digit, and special symbol.");
            System.out.print("Enter New Password: ");
            newPass = scanner.nextLine().trim();
            if (ValidationUtil.validatePassword(newPass)) {
                break;
            }
            System.out.println("--> [Format Error]: Password must be 8-20 characters long with uppercase, lowercase, digit, and special symbol (@#$%^&+=!).");
        }

        customerDao.updateCustomerPassword(customer.getCustomerId(), PasswordUtil.hash(newPass));
        System.out.println(">>> Password changed successfully! <<<");
    }

    public void renderCustomerTable(String title, List<Customer> customers) {
        List<String> headers = List.of("CUSTOMER ID", "NAME", "EMAIL", "PHONE", "BANK", "BRANCH", "STATUS", "NOMINEE");
        List<List<String>> rows = new ArrayList<>();
        if (customers != null) {
            for (Customer c : customers) {
                String nominee = (c.getNomineeName() != null && !c.getNomineeName().isEmpty())
                        ? c.getNomineeName() + " (" + c.getNomineeRelationship() + ")" : "N/A";
                rows.add(List.of(c.getCustomerId(), c.getName(), c.getEmail(), c.getPhoneNumber(), c.getBankName(), c.getBranchId(), c.getStatus(), nominee));
            }
        }
        TableUtil.printTable(title, headers, rows);
    }
}
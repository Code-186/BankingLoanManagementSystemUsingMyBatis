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
            System.out.print("Enter Full Name: ");
            name = scanner.nextLine().trim();
            if (ValidationUtil.validateName(name)) break;
            System.out.println("--> Invalid Name! Letters only (minimum 3 characters, no repetitive sequences).");
        }

        String email;
        while (true) {
            System.out.print("Enter Email ID: ");
            email = scanner.nextLine().trim();
            if (ValidationUtil.validateEmail(email)) break;
            System.out.println("--> Invalid Email format!");
        }

        String phone;
        while (true) {
            System.out.print("Enter 10-Digit Phone Number: ");
            phone = scanner.nextLine().trim();
            if (ValidationUtil.validatePhone(phone)) break;
            System.out.println("--> Phone must start with 6-9 and contain 10 digits.");
        }

        String password;
        while (true) {
            System.out.print("Enter Password: ");
            password = scanner.nextLine().trim();
            if (ValidationUtil.validatePassword(password)) break;
            System.out.println("--> Password must be 8-20 chars with uppercase, lowercase, digit, and special char.");
        }

        LocalDate dob;
        while (true) {
            System.out.print("Enter Date of Birth (dd/MM/yyyy): ");
            dob = ValidationUtil.parseAndValidateDob(scanner.nextLine().trim());
            if (dob != null) break;
            System.out.println("--> Invalid DOB! Must be in format dd/MM/yyyy and age >= 18.");
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
                System.out.print("Enter Nominee Phone: ");
                nomineePhone = scanner.nextLine().trim();
                if (ValidationUtil.validatePhone(nomineePhone)) break;
                System.out.println("--> Invalid nominee phone number.");
            }
        }

        String custId = IdGeneratorUtil.generateCustomerId();[cite: 2]
        Customer customer = new Customer(custId, name, phone, email, address, PasswordUtil.hash(password),
                dob, bankName, "REGISTERED", branchId, nomineeName, nomineeRel, nomineePhone);

        if (customerDao.insertCustomer(customer)) {
            System.out.println("\n>>> CUSTOMER REGISTERED SUCCESSFULLY! <<<");
            renderCustomerTable("REGISTERED CUSTOMER DETAILS", List.of(customer));
            System.out.println("--> Status is 'REGISTERED'. Visit your branch employee to open and activate your bank account.");
        } else {
            System.out.println("--> Registration failed. Email ID might already be registered in the system.");
        }
    }

    public void viewCustomer(Scanner scanner, String allowedBranchId) {
        System.out.print("Enter Customer ID: ");
        String customerId = scanner.nextLine().trim();

        Customer customer = customerDao.getCustomerById(customerId);
        if (customer == null) {
            throw new CustomerNotFoundException("Customer with ID '" + customerId + "' does not exist.");
        }

        if (allowedBranchId != null && !customer.getBranchId().equalsIgnoreCase(allowedBranchId)) {
            throw new CustomerNotFoundException("Customer does not belong to your assigned branch (" + allowedBranchId + ").");
        }

        renderCustomerTable("CUSTOMER PROFILE", List.of(customer));
    }

    public void viewAllCustomers() {
        List<Customer> customers = customerDao.getAllCustomers();
        if (customers == null || customers.isEmpty()) {
            throw new CustomerNotFoundException("No customers registered across the system.");
        }
        renderCustomerTable("ALL REGISTERED CUSTOMERS", customers);
    }

    public void viewBranchCustomers(String branchId) {
        List<Customer> customers = customerDao.getCustomersByBranchId(branchId);
        if (customers == null || customers.isEmpty()) {
            throw new CustomerNotFoundException("No customers registered under branch: " + branchId);
        }
        renderCustomerTable("BRANCH CUSTOMER DIRECTORY (" + branchId + ")", customers);
    }

    public void deleteCustomer(Scanner scanner) {
        System.out.print("Enter Customer ID to Deactivate: ");
        String customerId = scanner.nextLine().trim();

        Customer customer = customerDao.getCustomerById(customerId);
        if (customer == null) {
            throw new CustomerNotFoundException("Customer ID '" + customerId + "' not found.");
        }

        customerDao.updateCustomerStatus(customerId, "INACTIVE");
        System.out.println("--> Customer " + customerId + " status has been set to INACTIVE.");
    }

    // Profile updates
    public void updateCustomerPhone(Scanner scanner, Customer customer) {
        String phone;
        while (true) {
            System.out.print("Enter New 10-Digit Phone Number: ");
            phone = scanner.nextLine().trim();
            if (ValidationUtil.validatePhone(phone)) break;
            System.out.println("--> Invalid Phone! Must start with 6-9.");
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
            System.out.print("Enter Nominee Name: ");
            name = scanner.nextLine().trim();
            if (ValidationUtil.validateName(name)) break;
            System.out.println("--> Invalid Nominee Name!");
        }

        System.out.print("Enter Nominee Relationship: ");
        String rel = scanner.nextLine().trim();

        String phone;
        while (true) {
            System.out.print("Enter Nominee Phone Number: ");
            phone = scanner.nextLine().trim();
            if (ValidationUtil.validatePhone(phone)) break;
            System.out.println("--> Invalid Nominee Phone!");
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
            System.out.print("Enter New Password: ");
            newPass = scanner.nextLine().trim();
            if (ValidationUtil.validatePassword(newPass)) break;
            System.out.println("--> Password must be 8-20 chars with uppercase, lowercase, digit, and special char.");
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
        TableUtil.printTable(title, headers, rows);[cite: 4]
    }
}
package com.crimsonlogic.bankingandloanmanagementsystem.services.bankingservice;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import com.crimsonlogic.bankingandloanmanagementsystem.dao.AdminDao;
import com.crimsonlogic.bankingandloanmanagementsystem.dao.EmployeeDao;
import com.crimsonlogic.bankingandloanmanagementsystem.model.implementations.userimplementations.Admin;
import com.crimsonlogic.bankingandloanmanagementsystem.model.implementations.userimplementations.Employee;
import com.crimsonlogic.bankingandloanmanagementsystem.utility.IdGeneratorUtil;
import com.crimsonlogic.bankingandloanmanagementsystem.utility.PasswordUtil;
import com.crimsonlogic.bankingandloanmanagementsystem.utility.TableUtil;
import com.crimsonlogic.bankingandloanmanagementsystem.utility.ValidationUtil;

public class UserService {

    private final EmployeeDao employeeDao = new EmployeeDao();
    private final AdminDao adminDao = new AdminDao();

    public void registerEmployee(Scanner scanner, Admin currentAdmin) {
        System.out.println("\n=== REGISTER EMPLOYEE ===");

        String name;
        while (true) {
            System.out.print("Enter Name: ");
            name = scanner.nextLine().trim();
            if (ValidationUtil.validateName(name)) break;
            System.out.println("--> Invalid Name! Must be letters only (min 3 chars, no repeats).");
        }

        String phone;
        while (true) {
            System.out.print("Enter 10-Digit Phone: ");
            phone = scanner.nextLine().trim();
            if (ValidationUtil.validatePhone(phone)) break;
            System.out.println("--> Invalid Phone! Must start with 6-9 and have 10 digits.");
        }

        String email;
        while (true) {
            System.out.print("Enter Email: ");
            email = scanner.nextLine().trim();
            if (ValidationUtil.validateEmail(email)) break;
            System.out.println("--> Invalid Email format!");
        }

        System.out.print("Enter Address (Optional, Press Enter to skip): ");
        String address = scanner.nextLine().trim();

        String password;
        while (true) {
            System.out.print("Enter Password: ");
            password = scanner.nextLine().trim();
            if (ValidationUtil.validatePassword(password)) break;
            System.out.println("--> Password must be 8-20 chars, with 1 uppercase, 1 lowercase, 1 number, and 1 special symbol.");
        }

        LocalDate dob;
        while (true) {
            System.out.print("Enter Date of Birth (dd/MM/yyyy): ");
            String dobInput = scanner.nextLine().trim();
            dob = ValidationUtil.parseAndValidateDob(dobInput);
            if (dob != null) break;
            System.out.println("--> Invalid DOB! Must be in format dd/MM/yyyy and age >= 18.");
        }

        String designation;
        while (true) {
            System.out.print("Enter Designation: ");
            designation = scanner.nextLine().trim();
            if (ValidationUtil.validateDesignation(designation)) break;
            System.out.println("--> Invalid Designation!");
        }

        double salary;
        while (true) {
            System.out.print("Enter Salary (INR): ");
            String salaryStr = scanner.nextLine().trim();
            if (ValidationUtil.validateSalary(salaryStr)) {
                salary = Double.parseDouble(salaryStr);
                break;
            }
            System.out.println("--> Invalid Salary amount!");
        }

        String empId = IdGeneratorUtil.generateEmployeeId();[cite: 2]
        Employee employee = new Employee(empId, name, phone, email, address, PasswordUtil.hash(password),
                dob, currentAdmin.getBankName(), "ACTIVE", designation, salary, currentAdmin.getBranchId());

        if (employeeDao.insertEmployee(employee)) {
            System.out.println("\n>>> EMPLOYEE REGISTERED SUCCESSFULLY! <<<");
            renderEmployeeTable("NEW EMPLOYEE DETAILS", List.of(employee));
        } else {
            System.out.println("--> Registration failed. Email may already exist.");
        }
    }

    public void deleteEmployee(Scanner scanner) {
        System.out.print("Enter Employee ID to deactivate: ");
        String empId = scanner.nextLine().trim();
        Employee emp = employeeDao.getEmployeeById(empId);
        if (emp == null) {
            System.out.println("--> Employee Not Found.");
            return;
        }
        employeeDao.updateEmployeeStatus(empId, "INACTIVE");
        System.out.println("--> Employee " + empId + " deactivated successfully.");
    }

    public void viewEmployee(Scanner scanner) {
        System.out.print("Enter Employee ID: ");
        String empId = scanner.nextLine().trim();
        Employee emp = employeeDao.getEmployeeById(empId);
        if (emp != null) {
            renderEmployeeTable("EMPLOYEE PROFILE", List.of(emp));
        } else {
            System.out.println("--> Employee Not Found.");
        }
    }

    public void viewAllEmployees() {
        List<Employee> list = employeeDao.getAllEmployees();
        renderEmployeeTable("ALL REGISTERED EMPLOYEES", list);
    }

    public void viewAllAdmins() {
        List<Admin> list = adminDao.getAllAdmins();
        List<String> headers = List.of("ADMIN ID", "NAME", "EMAIL", "ROLE", "BANK", "BRANCH", "STATUS");
        List<List<String>> rows = new ArrayList<>();
        for (Admin a : list) {
            rows.add(List.of(a.getAdminId(), a.getName(), a.getEmail(), a.getRole(), a.getBankName(), a.getBranchId(), a.getStatus()));
        }
        TableUtil.printTable("SYSTEM ADMINISTRATORS", headers, rows);[cite: 4]
    }

    private void renderEmployeeTable(String title, List<Employee> list) {
        List<String> headers = List.of("EMPLOYEE ID", "NAME", "EMAIL", "PHONE", "DESIGNATION", "BANK", "BRANCH", "STATUS");
        List<List<String>> rows = new ArrayList<>();
        if (list != null) {
            for (Employee e : list) {
                rows.add(List.of(e.getEmployeeId(), e.getName(), e.getEmail(), e.getPhoneNumber(), e.getDesignation(), e.getBankName(), e.getBranchId(), e.getStatus()));
            }
        }
        TableUtil.printTable(title, headers, rows);[cite: 4]
    }
}
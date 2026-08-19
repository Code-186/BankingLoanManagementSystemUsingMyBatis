package com.crimsonlogic.bankingandloanmanagementsystem.services.bankingservice;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.crimsonlogic.bankingandloanmanagementsystem.dao.AdminDao;
import com.crimsonlogic.bankingandloanmanagementsystem.dao.EmployeeDao;
import com.crimsonlogic.bankingandloanmanagementsystem.exceptionhandling.AdminNotFoundException;
import com.crimsonlogic.bankingandloanmanagementsystem.exceptionhandling.EmployeeNotFoundException;
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
            System.out.print("Enter Full Name (e.g. Alice Smith): ");
            name = scanner.nextLine().trim();
            if (ValidationUtil.validateName(name)) break;
            System.out.println("--> [Format Error]: Name must be letters only (min 3 chars, no repeats).");
        }

        String phone;
        while (true) {
            System.out.print("Enter 10-Digit Phone (e.g. 9876543210): ");
            phone = scanner.nextLine().trim();
            if (ValidationUtil.validatePhone(phone)) break;
            System.out.println("--> [Format Error]: Phone must start with 6-9 and have 10 digits.");
        }

        String email;
        while (true) {
            System.out.print("Enter Official Email (e.g. name@bank.co.in): ");
            email = scanner.nextLine().trim();
            if (ValidationUtil.validateEmail(email)) break;
            System.out.println("--> [Format Error]: Invalid email format!");
        }

        System.out.print("Enter Address (Optional, Press Enter to skip): ");
        String address = scanner.nextLine().trim();

        String password;
        while (true) {
            System.out.print("Enter Password (e.g. Emp@1234): ");
            password = scanner.nextLine().trim();
            if (ValidationUtil.validatePassword(password)) break;
            System.out.println("--> [Format Error]: Password must be 8-20 chars with uppercase, lowercase, number, and special character.");
        }

        LocalDate dob;
        while (true) {
            System.out.print("Enter Date of Birth (dd/MM/yyyy, e.g. 12/04/1990): ");
            dob = ValidationUtil.parseAndValidateDob(scanner.nextLine().trim());
            if (dob != null) break;
            System.out.println("--> [Format Error]: Invalid DOB! Must be in format dd/MM/yyyy and age >= 18.");
        }

        String designation;
        while (true) {
            System.out.print("Enter Designation (e.g. Loan Officer): ");
            designation = scanner.nextLine().trim();
            if (ValidationUtil.validateDesignation(designation)) break;
            System.out.println("--> [Format Error]: Designation must contain letters and spaces (2-50 chars).");
        }

        double salary;
        while (true) {
            System.out.print("Enter Salary (INR, e.g. 45000.00): ");
            String salaryStr = scanner.nextLine().trim();
            if (ValidationUtil.validateSalary(salaryStr)) {
                salary = Double.parseDouble(salaryStr);
                break;
            }
            System.out.println("--> [Format Error]: Invalid Salary format! Must be a positive number.");
        }

        String empId = IdGeneratorUtil.generateEmployeeId();
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
        String empId;
        while (true) {
            System.out.print("Enter Employee ID to deactivate (e.g. EMP0001): ");
            empId = scanner.nextLine().trim();
            if (ValidationUtil.validateEmployeeIdFormat(empId)) break;
            System.out.println("--> [Format Error]: Employee ID must be in format 'EMP' followed by 4 digits (e.g. EMP0001).");
        }

        Employee emp = employeeDao.getEmployeeById(empId);
        if (emp == null) {
            throw new EmployeeNotFoundException("Employee with ID '" + empId + "' was not found in the database.");
        }

        employeeDao.updateEmployeeStatus(empId, "INACTIVE");
        System.out.println("--> Employee " + empId + " status updated to INACTIVE.");
    }

    public void viewEmployee(Scanner scanner) {
        String empId;
        while (true) {
            System.out.print("Enter Employee ID (e.g. EMP0001): ");
            empId = scanner.nextLine().trim();
            if (ValidationUtil.validateEmployeeIdFormat(empId)) break;
            System.out.println("--> [Format Error]: Employee ID must be in format 'EMP' followed by 4 digits (e.g. EMP0001).");
        }

        Employee emp = employeeDao.getEmployeeById(empId);
        if (emp == null) {
            throw new EmployeeNotFoundException("Employee with ID '" + empId + "' was not found in the database.");
        }

        renderEmployeeTable("EMPLOYEE PROFILE", List.of(emp));
    }

    public void viewAllEmployees() {
        List<Employee> list = employeeDao.getAllEmployees();
        if (list == null || list.isEmpty()) {
            throw new EmployeeNotFoundException("No employees registered in the system.");
        }
        renderEmployeeTable("ALL REGISTERED EMPLOYEES", list);
    }

    public void viewAllAdmins() {
        List<Admin> list = adminDao.getAllAdmins();
        if (list == null || list.isEmpty()) {
            throw new AdminNotFoundException("No admins found in the system.");
        }
        List<String> headers = List.of("ADMIN ID", "NAME", "EMAIL", "ROLE", "BANK", "BRANCH", "STATUS");
        List<List<String>> rows = new ArrayList<>();
        for (Admin a : list) {
            rows.add(List.of(a.getAdminId(), a.getName(), a.getEmail(), a.getRole(), a.getBankName(), a.getBranchId(), a.getStatus()));
        }
        TableUtil.printTable("SYSTEM ADMINISTRATORS", headers, rows);
    }

    private void renderEmployeeTable(String title, List<Employee> list) {
        List<String> headers = List.of("EMPLOYEE ID", "NAME", "EMAIL", "PHONE", "DESIGNATION", "BANK", "BRANCH", "STATUS");
        List<List<String>> rows = new ArrayList<>();
        if (list != null) {
            for (Employee e : list) {
                rows.add(List.of(e.getEmployeeId(), e.getName(), e.getEmail(), e.getPhoneNumber(), e.getDesignation(), e.getBankName(), e.getBranchId(), e.getStatus()));
            }
        }
        TableUtil.printTable(title, headers, rows);
    }
}
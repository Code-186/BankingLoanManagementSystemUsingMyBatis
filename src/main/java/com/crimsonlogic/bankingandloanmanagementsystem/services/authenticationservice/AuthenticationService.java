package com.crimsonlogic.bankingandloanmanagementsystem.services.authenticationservice;

import com.crimsonlogic.bankingandloanmanagementsystem.dao.AdminDao;
import com.crimsonlogic.bankingandloanmanagementsystem.dao.CustomerDao;
import com.crimsonlogic.bankingandloanmanagementsystem.dao.EmployeeDao;
import com.crimsonlogic.bankingandloanmanagementsystem.exceptionhandling.AdminNotFoundException;
import com.crimsonlogic.bankingandloanmanagementsystem.exceptionhandling.CustomerNotFoundException;
import com.crimsonlogic.bankingandloanmanagementsystem.exceptionhandling.EmployeeNotFoundException;
import com.crimsonlogic.bankingandloanmanagementsystem.model.implementations.userimplementations.Admin;
import com.crimsonlogic.bankingandloanmanagementsystem.model.implementations.userimplementations.Customer;
import com.crimsonlogic.bankingandloanmanagementsystem.model.implementations.userimplementations.Employee;
import com.crimsonlogic.bankingandloanmanagementsystem.utility.PasswordUtil;

public class AuthenticationService {

    private final AdminDao adminDao = new AdminDao();
    private final EmployeeDao employeeDao = new EmployeeDao();
    private final CustomerDao customerDao = new CustomerDao();

    public Admin loginAdmin(String email, String password) {
        Admin admin = adminDao.getAdminByEmail(email);
        if (admin == null) {
            throw new AdminNotFoundException("No Admin registered with email: " + email);
        }
        if (!"ACTIVE".equalsIgnoreCase(admin.getStatus())) {
            throw new AdminNotFoundException("Admin account is INACTIVE. Access denied.");
        }
        if (!PasswordUtil.verify(password, admin.getPassword())) {
            return null;
        }
        return admin;
    }

    public Employee loginEmployee(String email, String password) {
        Employee emp = employeeDao.getEmployeeByEmail(email);
        if (emp == null) {
            throw new EmployeeNotFoundException("Employee not registered with email: " + email + ". Please register through an Admin.");
        }
        if (!"ACTIVE".equalsIgnoreCase(emp.getStatus())) {
            throw new EmployeeNotFoundException("Employee account is marked as INACTIVE.");
        }
        if (!PasswordUtil.verify(password, emp.getPassword())) {
            return null;
        }
        return emp;
    }

    public Customer loginCustomer(String email, String password) {
        Customer cust = customerDao.getCustomerByEmail(email);
        if (cust == null) {
            throw new CustomerNotFoundException("Customer not found with email: " + email + ". Please register via Employee or Self-Registration.");
        }
        if ("INACTIVE".equalsIgnoreCase(cust.getStatus())) {
            throw new CustomerNotFoundException("Customer profile is INACTIVE.");
        }
        if (!PasswordUtil.verify(password, cust.getPassword())) {
            return null;
        }
        return cust;
    }
}
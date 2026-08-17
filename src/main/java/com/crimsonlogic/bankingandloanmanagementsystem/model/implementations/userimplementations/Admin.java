package com.crimsonlogic.bankingandloanmanagementsystem.model.implementations.userimplementations;

import java.time.LocalDate;
import com.crimsonlogic.bankingandloanmanagementsystem.model.abstractclasses.User;

public class Admin extends User {
    private String adminId;
    private String role;
    private Double salary;

    public Admin() {
        super();
    }

    public Admin(String adminId, String name, String phoneNumber, String email, 
                 String address, String password, LocalDate dateOfBirth, 
                 String bankName, String status, String role, Double salary, String branchId) {
        super(name, phoneNumber, email, address, password, dateOfBirth, bankName, status, branchId);
        this.adminId = adminId;
        this.role = role;
        this.salary = salary;
    }

    public String getAdminId() { return adminId; }
    public void setAdminId(String adminId) { this.adminId = adminId; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public Double getSalary() { return salary; }
    public void setSalary(Double salary) { this.salary = salary; }
}
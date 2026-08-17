package com.crimsonlogic.bankingandloanmanagementsystem.model.implementations.userimplementations;

import java.time.LocalDate;
import com.crimsonlogic.bankingandloanmanagementsystem.model.abstractclasses.User;

public class Employee extends User {
    private String employeeId;
    private String designation;
    private Double salary;

    public Employee() {
        super();
    }

    public Employee(String employeeId, String name, String phoneNumber, String email, 
                    String address, String password, LocalDate dateOfBirth, 
                    String bankName, String status, String designation, Double salary, String branchId) {
        super(name, phoneNumber, email, address, password, dateOfBirth, bankName, status, branchId);
        this.employeeId = employeeId;
        this.designation = designation;
        this.salary = salary;
    }

    public String getEmployeeId() { return employeeId; }
    public void setEmployeeId(String employeeId) { this.employeeId = employeeId; }

    public String getDesignation() { return designation; }
    public void setDesignation(String designation) { this.designation = designation; }

    public Double getSalary() { return salary; }
    public void setSalary(Double salary) { this.salary = salary; }
}
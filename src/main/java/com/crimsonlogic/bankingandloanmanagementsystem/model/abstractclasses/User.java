package com.crimsonlogic.bankingandloanmanagementsystem.model.abstractclasses;

import java.time.LocalDate;

public abstract class User {
    private String name;
    private String phoneNumber;
    private String email;
    private String address;
    private String password;
    private LocalDate dateOfBirth;
    private String bankName;
    private String status;
    private String branchId;

    public User() {}

    public User(String name, String phoneNumber, String email, String address, 
                String password, LocalDate dateOfBirth, String bankName, 
                String status, String branchId) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.address = address;
        this.password = password;
        this.dateOfBirth = dateOfBirth;
        this.bankName = bankName;
        this.status = status;
        this.branchId = branchId;
    }

    // Getters and Setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public LocalDate getDateOfBirth() { return dateOfBirth; }
    public void setDateOfBirth(LocalDate dateOfBirth) { this.dateOfBirth = dateOfBirth; }

    public String getBankName() { return bankName; }
    public void setBankName(String bankName) { this.bankName = bankName; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getBranchId() { return branchId; }
    public void setBranchId(String branchId) { this.branchId = branchId; }
}
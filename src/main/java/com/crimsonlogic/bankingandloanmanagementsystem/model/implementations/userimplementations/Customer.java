package com.crimsonlogic.bankingandloanmanagementsystem.model.implementations.userimplementations;

import java.time.LocalDate;
import com.crimsonlogic.bankingandloanmanagementsystem.model.abstractclasses.User;

public class Customer extends User {
    private String customerId;
    private String nomineeName;
    private String nomineeRelationship;
    private String nomineePhoneNumber;

    public Customer() {
        super();
    }

    public Customer(String customerId, String name, String phoneNumber, String email, 
                    String address, String password, LocalDate dateOfBirth, 
                    String bankName, String status, String branchId, 
                    String nomineeName, String nomineeRelationship, String nomineePhoneNumber) {
        super(name, phoneNumber, email, address, password, dateOfBirth, bankName, status, branchId);
        this.customerId = customerId;
        this.nomineeName = nomineeName;
        this.nomineeRelationship = nomineeRelationship;
        this.nomineePhoneNumber = nomineePhoneNumber;
    }

    public String getCustomerId() { return customerId; }
    public void setCustomerId(String customerId) { this.customerId = customerId; }

    public String getNomineeName() { return nomineeName; }
    public void setNomineeName(String nomineeName) { this.nomineeName = nomineeName; }

    public String getNomineeRelationship() { return nomineeRelationship; }
    public void setNomineeRelationship(String nomineeRelationship) { this.nomineeRelationship = nomineeRelationship; }

    public String getNomineePhoneNumber() { return nomineePhoneNumber; }
    public void setNomineePhoneNumber(String nomineePhoneNumber) { this.nomineePhoneNumber = nomineePhoneNumber; }
}
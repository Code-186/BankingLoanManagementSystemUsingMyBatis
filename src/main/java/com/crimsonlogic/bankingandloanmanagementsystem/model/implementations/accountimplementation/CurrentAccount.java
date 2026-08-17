package com.crimsonlogic.bankingandloanmanagementsystem.model.implementations.accountimplementation;

import java.time.LocalDate;
import com.crimsonlogic.bankingandloanmanagementsystem.model.abstractclasses.Account;

public class CurrentAccount extends Account {
    private Double overdraftLimit;

    public CurrentAccount() {
        super();
        setAccountType("CURRENT");
    }

    public CurrentAccount(String accountNumber, Double balance, LocalDate openingDate, 
                          String accountStatus, String mpin, String customerId, Double overdraftLimit) {
        super(accountNumber, balance, openingDate, accountStatus, mpin, customerId, "CURRENT");
        this.overdraftLimit = overdraftLimit;
    }

    public Double getOverdraftLimit() { return overdraftLimit; }
    public void setOverdraftLimit(Double overdraftLimit) { this.overdraftLimit = overdraftLimit; }
}
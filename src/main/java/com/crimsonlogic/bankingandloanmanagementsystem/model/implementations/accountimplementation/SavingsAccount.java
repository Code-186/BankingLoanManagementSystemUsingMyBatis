package com.crimsonlogic.bankingandloanmanagementsystem.model.implementations.accountimplementation;

import java.time.LocalDate;
import com.crimsonlogic.bankingandloanmanagementsystem.model.abstractclasses.Account;

public class SavingsAccount extends Account {
    private Double interestRate;

    public SavingsAccount() {
        super();
        setAccountType("SAVINGS");
    }

    public SavingsAccount(String accountNumber, Double balance, LocalDate openingDate, 
                          String accountStatus, String mpin, String customerId, Double interestRate) {
        super(accountNumber, balance, openingDate, accountStatus, mpin, customerId, "SAVINGS");
        this.interestRate = interestRate;
    }

    public Double getInterestRate() { return interestRate; }
    public void setInterestRate(Double interestRate) { this.interestRate = interestRate; }
}
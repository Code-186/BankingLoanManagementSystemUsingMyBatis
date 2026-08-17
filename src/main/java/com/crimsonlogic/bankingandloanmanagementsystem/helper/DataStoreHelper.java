package com.crimsonlogic.bankingandloanmanagementsystem.helper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeMap;

import com.crimsonlogic.bankingandloanmanagementsystem.entities.Beneficiary;
import com.crimsonlogic.bankingandloanmanagementsystem.entities.Branch;
import com.crimsonlogic.bankingandloanmanagementsystem.entities.EMI;
import com.crimsonlogic.bankingandloanmanagementsystem.entities.Loan;
import com.crimsonlogic.bankingandloanmanagementsystem.entities.Transaction;
import com.crimsonlogic.bankingandloanmanagementsystem.model.abstractclasses.Account;
import com.crimsonlogic.bankingandloanmanagementsystem.model.implementations.userimplementations.Admin;
import com.crimsonlogic.bankingandloanmanagementsystem.model.implementations.userimplementations.Customer;
import com.crimsonlogic.bankingandloanmanagementsystem.model.implementations.userimplementations.Employee;

public class DataStoreHelper {
    private HashMap<String, Admin> admins =
            new HashMap<>();

    private HashMap<String, Customer> customers =
            new HashMap<>();

    private HashMap<String, Employee> employees =
            new HashMap<>();

    private HashMap<Long, Account> accounts =
            new HashMap<>();

    private ArrayList<Loan> loans =
            new ArrayList<>();

    private ArrayList<Transaction> transactions =
            new ArrayList<>();

    private ArrayList<Beneficiary> beneficiaries =
            new ArrayList<>();

    private ArrayList<EMI> emis =
            new ArrayList<>();

    private TreeMap<Integer, Branch> branches =
            new TreeMap<>();

    public HashMap<String, Admin> getAdmins() {
        return admins;
    }

    public HashMap<String, Customer> getCustomers() {
        return customers;
    }

    public HashMap<String, Employee> getEmployees() {
        return employees;
    }

    public HashMap<Long, Account> getAccounts() {
        return accounts;
    }

    public ArrayList<Loan> getLoans() {
        return loans;
    }

    public ArrayList<Transaction> getTransactions() {
        return transactions;
    }

    public ArrayList<Beneficiary> getBeneficiaries() {
        return beneficiaries;
    }

    public ArrayList<EMI> getEmis() {
        return emis;
    }

    public TreeMap<Integer, Branch> getBranches() {
        return branches;
    }
}
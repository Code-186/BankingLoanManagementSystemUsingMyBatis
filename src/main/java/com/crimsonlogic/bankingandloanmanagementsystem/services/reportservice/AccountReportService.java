package com.crimsonlogic.bankingandloanmanagementsystem.services.reportservice;

import java.util.ArrayList;
import java.util.List;
import com.crimsonlogic.bankingandloanmanagementsystem.dao.AccountDao;
import com.crimsonlogic.bankingandloanmanagementsystem.model.abstractclasses.Account;

public class AccountReportService {

    private final AccountDao accountDao = new AccountDao();

    public AccountReportService() {
        // Default constructor using AccountDao
    }

    public void generateAccountSummaryReport() {
        System.out.println("--> Account Summary Report is reserved for audit updates.");
    }
}
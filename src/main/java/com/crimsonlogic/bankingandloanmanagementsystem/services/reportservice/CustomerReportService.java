package com.crimsonlogic.bankingandloanmanagementsystem.services.reportservice;

import com.crimsonlogic.bankingandloanmanagementsystem.dao.CustomerDao;

public class CustomerReportService {

    private final CustomerDao customerDao = new CustomerDao();

    public CustomerReportService() {
        // Default constructor
    }

    public void generateCustomerSummaryReport() {
        System.out.println("--> Customer Summary Report is reserved for audit updates.");
    }
}
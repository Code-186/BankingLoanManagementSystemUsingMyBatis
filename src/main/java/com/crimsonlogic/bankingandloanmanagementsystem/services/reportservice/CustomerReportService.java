package com.crimsonlogic.bankingandloanmanagementsystem.services.reportservice;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.crimsonlogic.bankingandloanmanagementsystem.helper.DataStoreHelper;
import com.crimsonlogic.bankingandloanmanagementsystem.model.implementations.userimplementations.Customer;

public class CustomerReportService {

    private DataStoreHelper dataStoreHelper;

    public CustomerReportService(DataStoreHelper dataStoreHelper) {
        this.dataStoreHelper = dataStoreHelper;
    }

    public void generateCustomerSummaryReport() {

        Map<String, Customer> customers =
                dataStoreHelper.getCustomers();

        long totalCustomers =
                customers.size();

        long activeCustomers =
                customers.values()
                        .stream()
                        .filter(customer ->
                                "ACTIVE".equalsIgnoreCase(
                                        customer.getStatus()))
                        .count();

        long pendingCustomers =
                customers.values()
                        .stream()
                        .filter(customer ->
                                "PENDING".equalsIgnoreCase(
                                        customer.getStatus()))
                        .count();

        long inactiveCustomers =
                customers.values()
                        .stream()
                        .filter(customer ->
                                "INACTIVE".equalsIgnoreCase(
                                        customer.getStatus()))
                        .count();

        System.out.println();
        System.out.println("========================================");
        System.out.println("      CUSTOMER SUMMARY REPORT");
        System.out.println("========================================");
        System.out.println("Total Customers    : " + totalCustomers);
        System.out.println("Active Customers   : " + activeCustomers);
        System.out.println("Pending Customers  : " + pendingCustomers);
        System.out.println("Inactive Customers : " + inactiveCustomers);
        System.out.println("========================================");
    }

    public List<Customer> getActiveCustomers() {

        return dataStoreHelper.getCustomers()
                .values()
                .stream()
                .filter(customer ->
                        "ACTIVE".equalsIgnoreCase(
                                customer.getStatus()))
                .collect(Collectors.toList());
    }

    public List<Customer> getPendingCustomers() {

        return dataStoreHelper.getCustomers()
                .values()
                .stream()
                .filter(customer ->
                        "PENDING".equalsIgnoreCase(
                                customer.getStatus()))
                .collect(Collectors.toList());
    }

    public List<Customer> getInactiveCustomers() {

        return dataStoreHelper.getCustomers()
                .values()
                .stream()
                .filter(customer ->
                        "INACTIVE".equalsIgnoreCase(
                                customer.getStatus()))
                .collect(Collectors.toList());
    }

    public List<Customer> sortCustomersByName() {

        return dataStoreHelper.getCustomers()
                .values()
                .stream()
                .sorted((c1, c2) ->
                        c1.getName()
                                .compareToIgnoreCase(
                                        c2.getName()))
                .collect(Collectors.toList());
    }

    public Map<String, List<Customer>> groupCustomersByBranch() {

        return dataStoreHelper.getCustomers()
                .values()
                .stream()
                .collect(Collectors.groupingBy(
                        Customer::getBranchId));
    }

    public void displayActiveCustomers() {

        List<Customer> customers =
                getActiveCustomers();

        if (customers.isEmpty()) {
            System.out.println("No Active Customers Found");
            return;
        }

        customers.forEach(System.out::println);
    }

    public void displayPendingCustomers() {

        List<Customer> customers =
                getPendingCustomers();

        if (customers.isEmpty()) {
            System.out.println("No Pending Customers Found");
            return;
        }

        customers.forEach(System.out::println);
    }

    public void displayInactiveCustomers() {

        List<Customer> customers =
                getInactiveCustomers();

        if (customers.isEmpty()) {
            System.out.println("No Inactive Customers Found");
            return;
        }

        customers.forEach(System.out::println);
    }

    public void displayCustomersSortedByName() {

        List<Customer> customers =
                sortCustomersByName();

        if (customers.isEmpty()) {
            System.out.println("No Customers Found");
            return;
        }

        customers.forEach(System.out::println);
    }

    public void displayCustomersGroupedByBranch() {

        Map<String, List<Customer>> groupedCustomers =
                groupCustomersByBranch();

        if (groupedCustomers.isEmpty()) {
            System.out.println("No Customers Found");
            return;
        }

        groupedCustomers.forEach((branchId, customers) -> {

            System.out.println("\nBranch ID : " + branchId);

            customers.forEach(System.out::println);
        });
    }
}
package com.crimsonlogic.bankingandloanmanagementsystem.services.reportservice;

import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import com.crimsonlogic.bankingandloanmanagementsystem.entities.EMI;
import com.crimsonlogic.bankingandloanmanagementsystem.entities.Loan;
import com.crimsonlogic.bankingandloanmanagementsystem.entities.Transaction;
import com.crimsonlogic.bankingandloanmanagementsystem.helper.DataStoreHelper;
import com.crimsonlogic.bankingandloanmanagementsystem.model.abstractclasses.Account;

public class AnalyticsReportService {

    private DataStoreHelper dataStoreHelper;

    public AnalyticsReportService(DataStoreHelper dataStoreHelper) {
        this.dataStoreHelper = dataStoreHelper;
    }

    public double totalInterestRevenue() {

        return dataStoreHelper.getLoans()
                .stream()
                .mapToDouble(loan ->
                        loan.getLoanAmount()
                                * loan.getInterestRate()
                                / 100)
                .sum();
    }

    public Optional<Account> accountWithHighestBalance() {

        return dataStoreHelper.getAccounts()
                .values()
                .stream()
                .max((a1, a2) ->
                        Double.compare(
                                a1.getBalance(),
                                a2.getBalance()));
    }

    public Optional<Loan> customerWithHighestLoan() {

        return dataStoreHelper.getLoans()
                .stream()
                .max((l1, l2) ->
                        Double.compare(
                                l1.getLoanAmount(),
                                l2.getLoanAmount()));
    }

    public DoubleSummaryStatistics balanceStatistics() {

        return dataStoreHelper.getAccounts()
                .values()
                .stream()
                .collect(Collectors.summarizingDouble(
                        Account::getBalance));
    }

    public Map<String, Long> countLoansPerCustomer() {

        return dataStoreHelper.getLoans()
                .stream()
                .collect(Collectors.groupingBy(
                        Loan::getCustomerId,
                        Collectors.counting()));
    }

    public Map<Long, Long> countTransactionsPerAccount() {

        return dataStoreHelper.getTransactions()
                .stream()
                .collect(Collectors.groupingBy(
                        Transaction::getAccountNumber,
                        Collectors.counting()));
    }

    public Map<Boolean, List<EMI>> partitionEMIs() {

        return dataStoreHelper.getEmis()
                .stream()
                .collect(Collectors.partitioningBy(
                        EMI::isPaid));
    }

    public void displayBalanceStatistics() {

        DoubleSummaryStatistics statistics =
                balanceStatistics();

        System.out.println();
        System.out.println("========================================");
        System.out.println("      BALANCE STATISTICS REPORT");
        System.out.println("========================================");
        System.out.println("Count   : " + statistics.getCount());
        System.out.println("Sum     : " + statistics.getSum());
        System.out.println("Average : " + statistics.getAverage());
        System.out.println("Minimum : " + statistics.getMin());
        System.out.println("Maximum : " + statistics.getMax());
        System.out.println("========================================");
    }

    public void displayHighestBalanceAccount() {

        Optional<Account> account =
                accountWithHighestBalance();

        if (account.isPresent()) {
            System.out.println(account.get());
        } else {
            System.out.println("No Accounts Found");
        }
    }

    public void displayHighestLoan() {

        Optional<Loan> loan =
                customerWithHighestLoan();

        if (loan.isPresent()) {
            System.out.println(loan.get());
        } else {
            System.out.println("No Loans Found");
        }
    }

    public void displayInterestRevenue() {

        System.out.println(
                "Total Interest Revenue : "
                        + totalInterestRevenue());
    }

    public void displayLoansPerCustomer() {

        Map<String, Long> loanCounts =
                countLoansPerCustomer();

        if (loanCounts.isEmpty()) {
            System.out.println("No Loan Records Found");
            return;
        }

        loanCounts.forEach((customerId, count) ->
                System.out.println(
                        "Customer ID : "
                                + customerId
                                + " -> "
                                + count
                                + " Loan(s)"));
    }

    public void displayTransactionsPerAccount() {

        Map<Long, Long> transactionCounts =
                countTransactionsPerAccount();

        if (transactionCounts.isEmpty()) {
            System.out.println("No Transaction Records Found");
            return;
        }

        transactionCounts.forEach((accountNumber, count) ->
                System.out.println(
                        "Account Number : "
                                + accountNumber
                                + " -> "
                                + count
                                + " Transaction(s)"));
    }

    public void displayEmiPartitionReport() {

        Map<Boolean, List<EMI>> emiReport =
                partitionEMIs();

        System.out.println();
        System.out.println("Paid EMI Count : "
                + emiReport.getOrDefault(true, List.of()).size());

        System.out.println("Pending EMI Count : "
                + emiReport.getOrDefault(false, List.of()).size());
    }
}
package com.crimsonlogic.bankingandloanmanagementsystem.services.reportservice;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import com.crimsonlogic.bankingandloanmanagementsystem.entities.Transaction;
import com.crimsonlogic.bankingandloanmanagementsystem.helper.DataStoreHelper;

public class TransactionReportService {

    private DataStoreHelper dataStoreHelper;

    public TransactionReportService(DataStoreHelper dataStoreHelper) {
        this.dataStoreHelper = dataStoreHelper;
    }

    public double totalDeposits() {

        return dataStoreHelper.getTransactions()
                .stream()
                .filter(transaction ->
                        "DEPOSIT".equalsIgnoreCase(
                                transaction.getTransactionType()))
                .mapToDouble(Transaction::getAmount)
                .sum();
    }

    public double totalWithdrawals() {

        return dataStoreHelper.getTransactions()
                .stream()
                .filter(transaction ->
                        "WITHDRAW".equalsIgnoreCase(
                                transaction.getTransactionType()))
                .mapToDouble(Transaction::getAmount)
                .sum();
    }

    public Map<Long, Long> countTransactionsPerAccount() {

        return dataStoreHelper.getTransactions()
                .stream()
                .collect(
                        Collectors.groupingBy(
                                Transaction::getAccountNumber,
                                Collectors.counting()));
    }

    public Optional<Transaction> latestTransaction() {

        return dataStoreHelper.getTransactions()
                .stream()
                .max(
                        Comparator.comparing(
                                Transaction::getTransactionDate));
    }

    public List<Transaction> getDepositTransactions() {

        return dataStoreHelper.getTransactions()
                .stream()
                .filter(transaction ->
                        "DEPOSIT".equalsIgnoreCase(
                                transaction.getTransactionType()))
                .collect(Collectors.toList());
    }

    public List<Transaction> getWithdrawalTransactions() {

        return dataStoreHelper.getTransactions()
                .stream()
                .filter(transaction ->
                        "WITHDRAW".equalsIgnoreCase(
                                transaction.getTransactionType()))
                .collect(Collectors.toList());
    }

    public List<Transaction> getTransferTransactions() {

        return dataStoreHelper.getTransactions()
                .stream()
                .filter(transaction ->
                        "TRANSFER".equalsIgnoreCase(
                                transaction.getTransactionType()))
                .collect(Collectors.toList());
    }

    public void generateTransactionSummaryReport() {

        long totalTransactions =
                dataStoreHelper.getTransactions()
                        .size();

        double totalDeposits =
                totalDeposits();

        double totalWithdrawals =
                totalWithdrawals();

        System.out.println();
        System.out.println("========================================");
        System.out.println("     TRANSACTION SUMMARY REPORT");
        System.out.println("========================================");
        System.out.println("Total Transactions : " + totalTransactions);
        System.out.println("Total Deposits     : " + totalDeposits);
        System.out.println("Total Withdrawals  : " + totalWithdrawals);
        System.out.println("========================================");
    }

    public void displayLatestTransaction() {

        Optional<Transaction> transaction =
                latestTransaction();

        if (transaction.isPresent()) {
            System.out.println(transaction.get());
        } else {
            System.out.println("No Transaction Records Found");
        }
    }

    public void displayTransactionCountPerAccount() {

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
                                + " --> "
                                + count
                                + " Transaction(s)"));
    }

    public void displayDepositTransactions() {

        List<Transaction> transactions =
                getDepositTransactions();

        if (transactions.isEmpty()) {
            System.out.println("No Deposit Transactions Found");
            return;
        }

        transactions.forEach(System.out::println);
    }

    public void displayWithdrawalTransactions() {

        List<Transaction> transactions =
                getWithdrawalTransactions();

        if (transactions.isEmpty()) {
            System.out.println("No Withdrawal Transactions Found");
            return;
        }

        transactions.forEach(System.out::println);
    }

    public void displayTransferTransactions() {

        List<Transaction> transactions =
                getTransferTransactions();

        if (transactions.isEmpty()) {
            System.out.println("No Transfer Transactions Found");
            return;
        }

        transactions.forEach(System.out::println);
    }
}
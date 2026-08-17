package com.crimsonlogic.bankingandloanmanagementsystem.services.reportservice;

import java.util.Comparator;
import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import com.crimsonlogic.bankingandloanmanagementsystem.helper.DataStoreHelper;
import com.crimsonlogic.bankingandloanmanagementsystem.model.abstractclasses.Account;
import com.crimsonlogic.bankingandloanmanagementsystem.model.implementations.accountimplementation.SavingsAccount;

public class AccountReportService {

    private DataStoreHelper dataStoreHelper;

    public AccountReportService(DataStoreHelper dataStoreHelper) {
        this.dataStoreHelper = dataStoreHelper;
    }

    public List<Account> getActiveAccounts() {

        return dataStoreHelper.getAccounts()
                .values()
                .stream()
                .filter(account ->
                        "ACTIVE".equalsIgnoreCase(
                                account.getAccountStatus()))
                .collect(Collectors.toList());
    }

    public List<Account> getSavingsAccounts() {

        return dataStoreHelper.getAccounts()
                .values()
                .stream()
                .filter(account ->
                        account instanceof SavingsAccount)
                .collect(Collectors.toList());
    }

    public List<Account> sortAccountsByBalance() {

        return dataStoreHelper.getAccounts()
                .values()
                .stream()
                .sorted(
                        Comparator.comparingDouble(
                                Account::getBalance))
                .collect(Collectors.toList());
    }

    public List<Account> topFiveAccounts() {

        return dataStoreHelper.getAccounts()
                .values()
                .stream()
                .sorted(
                        Comparator.comparingDouble(
                                Account::getBalance)
                                .reversed())
                .limit(5)
                .collect(Collectors.toList());
    }

    public Optional<Account> accountWithHighestBalance() {

        return dataStoreHelper.getAccounts()
                .values()
                .stream()
                .max(
                        Comparator.comparingDouble(
                                Account::getBalance));
    }

    public DoubleSummaryStatistics balanceStatistics() {

        return dataStoreHelper.getAccounts()
                .values()
                .stream()
                .collect(
                        Collectors.summarizingDouble(
                                Account::getBalance));
    }

    public Optional<Account> earliestAccountOpened() {

        return dataStoreHelper.getAccounts()
                .values()
                .stream()
                .min(
                        Comparator.comparing(
                                Account::getOpenedDate));
    }

    public Optional<Account> findAccount(
            long accountNumber) {

        return Optional.ofNullable(
                dataStoreHelper.getAccounts()
                        .get(accountNumber));
    }

    public Map<String, List<Account>> groupAccountsByType() {

        return dataStoreHelper.getAccounts()
                .values()
                .stream()
                .collect(
                        Collectors.groupingBy(
                                account ->
                                        account.getClass()
                                                .getSimpleName()));
    }

    public void generateAccountSummaryReport() {

        long totalAccounts =
                dataStoreHelper.getAccounts()
                        .size();

        long activeAccounts =
                dataStoreHelper.getAccounts()
                        .values()
                        .stream()
                        .filter(account ->
                                "ACTIVE".equalsIgnoreCase(
                                        account.getAccountStatus()))
                        .count();

        long inactiveAccounts =
                dataStoreHelper.getAccounts()
                        .values()
                        .stream()
                        .filter(account ->
                                "INACTIVE".equalsIgnoreCase(
                                        account.getAccountStatus()))
                        .count();

        double totalBalance =
                dataStoreHelper.getAccounts()
                        .values()
                        .stream()
                        .mapToDouble(
                                Account::getBalance)
                        .sum();

        System.out.println();
        System.out.println("========================================");
        System.out.println("      ACCOUNT SUMMARY REPORT");
        System.out.println("========================================");
        System.out.println("Total Accounts   : " + totalAccounts);
        System.out.println("Active Accounts  : " + activeAccounts);
        System.out.println("Inactive Accounts: " + inactiveAccounts);
        System.out.println("Total Balance    : " + totalBalance);
        System.out.println("========================================");
    }

    public void displayActiveAccounts() {

        List<Account> accounts =
                getActiveAccounts();

        if (accounts.isEmpty()) {
            System.out.println("No Active Accounts Found");
            return;
        }

        accounts.forEach(System.out::println);
    }

    public void displaySavingsAccounts() {

        List<Account> accounts =
                getSavingsAccounts();

        if (accounts.isEmpty()) {
            System.out.println("No Savings Accounts Found");
            return;
        }

        accounts.forEach(System.out::println);
    }

    public void displayTopFiveAccounts() {

        List<Account> accounts =
                topFiveAccounts();

        if (accounts.isEmpty()) {
            System.out.println("No Accounts Found");
            return;
        }

        accounts.forEach(System.out::println);
    }

    public void displayAccountsGroupedByType() {

        Map<String, List<Account>> groupedAccounts =
                groupAccountsByType();

        if (groupedAccounts.isEmpty()) {
            System.out.println("No Accounts Found");
            return;
        }

        groupedAccounts.forEach((type, accounts) -> {

            System.out.println("\nAccount Type : " + type);

            accounts.forEach(System.out::println);
        });
    }
}
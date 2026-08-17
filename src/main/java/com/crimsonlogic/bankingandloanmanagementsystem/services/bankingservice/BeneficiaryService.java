package com.crimsonlogic.bankingandloanmanagementsystem.services.bankingservice;

import java.util.Scanner;

import com.crimsonlogic.bankingandloanmanagementsystem.entities.Beneficiary;
import com.crimsonlogic.bankingandloanmanagementsystem.exceptionhandling.BeneficiaryNotFoundException;
import com.crimsonlogic.bankingandloanmanagementsystem.helper.DataStoreHelper;

public class BeneficiaryService {

    private DataStoreHelper dataStoreHelper;

    public BeneficiaryService(
            DataStoreHelper dataStoreHelper) {

        this.dataStoreHelper = dataStoreHelper;
    }

    public boolean addBeneficiary(
            Beneficiary beneficiary) {

        boolean exists =
                dataStoreHelper.getBeneficiaries()
                        .stream()
                        .anyMatch(b ->
                                b.getBeneficiaryId()
                                        .equals(beneficiary.getBeneficiaryId()));

        if (exists) {

            return false;
        }

        dataStoreHelper.getBeneficiaries()
                .add(beneficiary);

        return true;
    }

    public void addBeneficiary() {

        Scanner scanner =
                new Scanner(System.in);

        System.out.print(
                "Enter Beneficiary Id : ");

        String beneficiaryId =
                scanner.nextLine();

        System.out.print(
                "Enter Beneficiary Name : ");

        String beneficiaryName =
                scanner.nextLine();

        System.out.print(
                "Enter Account Number : ");

        long accountNumber =
                scanner.nextLong();

        scanner.nextLine();

        System.out.print(
                "Enter Bank Name : ");

        String bankName =
                scanner.nextLine();

        Beneficiary beneficiary =
                new Beneficiary(
                        beneficiaryId,
                        beneficiaryName,
                        accountNumber,
                        bankName);

        boolean added =
                addBeneficiary(
                        beneficiary);

        if (added) {

            System.out.println(
                    "Beneficiary Added Successfully");

        } else {

            System.out.println(
                    "Beneficiary Already Exists");
        }
    }

    public boolean removeBeneficiary(
            String beneficiaryId) {

        return dataStoreHelper.getBeneficiaries()
                .removeIf(b ->
                        b.getBeneficiaryId()
                                .equals(beneficiaryId));
    }

    public void removeBeneficiary() {

        Scanner scanner =
                new Scanner(System.in);

        System.out.print(
                "Enter Beneficiary Id : ");

        String beneficiaryId =
                scanner.nextLine();

        boolean removed =
                removeBeneficiary(
                        beneficiaryId);

        if (removed) {

            System.out.println(
                    "Beneficiary Removed Successfully");

        } else {

            System.out.println(
                    "Beneficiary Not Found");
        }
    }

    public Beneficiary searchBeneficiary(
            String beneficiaryId) {

        Beneficiary beneficiary =
                dataStoreHelper.getBeneficiaries()
                        .stream()
                        .filter(b ->
                                b.getBeneficiaryId()
                                        .equals(beneficiaryId))
                        .findFirst()
                        .orElse(null);

        if (beneficiary == null) {

            throw new BeneficiaryNotFoundException(
                    "Beneficiary not found");
        }

        return beneficiary;
    }

    public void searchBeneficiary() {

        Scanner scanner =
                new Scanner(System.in);

        System.out.print(
                "Enter Beneficiary Id : ");

        String beneficiaryId =
                scanner.nextLine();

        try {

            Beneficiary beneficiary =
                    searchBeneficiary(
                            beneficiaryId);

            System.out.println(
                    beneficiary);

        } catch (BeneficiaryNotFoundException e) {

            System.out.println(
                    e.getMessage());
        }
    }

    public void viewAllBeneficiaries() {

        if (dataStoreHelper.getBeneficiaries()
                .isEmpty()) {

            System.out.println(
                    "No beneficiaries found");

            return;
        }

        dataStoreHelper.getBeneficiaries()
                .forEach(System.out::println);
    }
}
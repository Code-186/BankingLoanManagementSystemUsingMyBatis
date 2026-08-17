package com.crimsonlogic.bankingandloanmanagementsystem.entities;

import java.util.Objects;

public class Beneficiary {

    private String  beneficiaryId;
    private String customerId;

    private String beneficiaryName;
    private long accountNumber;
    private String bankName;

    private String branchId;
    private String status;

    public Beneficiary() {
    }

    // Added constructor for BeneficiaryService
    public Beneficiary(
            String beneficiaryId,
            String beneficiaryName,
            long accountNumber,
            String bankName) {

        this.beneficiaryId = beneficiaryId;
        this.beneficiaryName = beneficiaryName;
        this.accountNumber = accountNumber;
        this.bankName = bankName;
    }

    public Beneficiary(
            String beneficiaryId,
            String customerId,
            String beneficiaryName,
            long accountNumber,
            String bankName,
            String branchId,
            String status) {

        this.beneficiaryId = beneficiaryId;
        this.customerId = customerId;
        this.beneficiaryName = beneficiaryName;
        this.accountNumber = accountNumber;
        this.bankName = bankName;
        this.branchId = branchId;
        this.status = status;
    }

    public String getBeneficiaryId() {
        return beneficiaryId;
    }

    public void setBeneficiaryId(String beneficiaryId) {
        this.beneficiaryId = beneficiaryId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getBeneficiaryName() {
        return beneficiaryName;
    }

    public void setBeneficiaryName(String beneficiaryName) {
        this.beneficiaryName = beneficiaryName;
    }

    public long getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(long accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBranchId() {
        return branchId;
    }

    public void setBranchId(String branchId) {
        this.branchId = branchId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Beneficiary that = (Beneficiary) o;

        return beneficiaryId == that.beneficiaryId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(beneficiaryId);
    }

    @Override
    public String toString() {
        return "Beneficiary{" +
                "beneficiaryId=" + beneficiaryId +
                ", customerId=" + customerId +
                ", beneficiaryName='" + beneficiaryName + '\'' +
                ", accountNumber=" + accountNumber +
                ", bankName='" + bankName + '\'' +
                ", branchId=" + branchId +
                ", status='" + status + '\'' +
                '}';
    }
}
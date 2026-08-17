package com.crimsonlogic.bankingandloanmanagementsystem.entities;

import java.time.LocalDate;
import java.util.Objects;

public class EMI {

    private String emiId;
    private String loanId;
    private double emiAmount;
    private LocalDate dueDate;

    private boolean paid;
    private String status;

    public EMI() {
    }

    public EMI(String emiId,
    		String loanId,
               double emiAmount,
               LocalDate dueDate,
               boolean paid,
               String status) {

        this.emiId = emiId;
        this.loanId = loanId;
        this.emiAmount = emiAmount;
        this.dueDate = dueDate;
        this.paid = paid;
        this.status = status;
    }

    public String getEmiId() {
        return emiId;
    }

    public void setEmiId(String emiId) {
        this.emiId = emiId;
    }

    public String getLoanId() {
        return loanId;
    }

    public void setLoanId(String loanId) {
        this.loanId = loanId;
    }

    public double getEmiAmount() {
        return emiAmount;
    }

    public void setEmiAmount(double emiAmount) {
        this.emiAmount = emiAmount;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public boolean isPaid() {
        return paid;
    }

    public void setPaid(boolean paid) {
        this.paid = paid;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "EMI{" +
                "emiId=" + emiId +
                ", loanId=" + loanId +
                ", emiAmount=" + emiAmount +
                ", dueDate=" + dueDate +
                ", paid=" + paid +
                ", status='" + status + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        EMI emi = (EMI) o;

        return emiId == emi.emiId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(emiId);
    }
}
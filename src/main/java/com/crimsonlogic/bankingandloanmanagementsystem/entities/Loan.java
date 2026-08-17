package com.crimsonlogic.bankingandloanmanagementsystem.entities;

public class Loan {
    private String loanId;
    private String customerId;
    private String loanType;
    private Double loanAmount;
    private Double interestRate;
    private Integer tenureMonths;
    private String status;

    public Loan() {}

    public Loan(String loanId, String customerId, String loanType, Double loanAmount, 
                Double interestRate, Integer tenureMonths, String status) {
        this.loanId = loanId;
        this.customerId = customerId;
        this.loanType = loanType;
        this.loanAmount = loanAmount;
        this.interestRate = interestRate;
        this.tenureMonths = tenureMonths;
        this.status = status;
    }

    public String getLoanId() { return loanId; }
    public void setLoanId(String loanId) { this.loanId = loanId; }

    public String getCustomerId() { return customerId; }
    public void setCustomerId(String customerId) { this.customerId = customerId; }

    public String getLoanType() { return loanType; }
    public void setLoanType(String loanType) { this.loanType = loanType; }

    public Double getLoanAmount() { return loanAmount; }
    public void setLoanAmount(Double loanAmount) { this.loanAmount = loanAmount; }

    public Double getInterestRate() { return interestRate; }
    public void setInterestRate(Double interestRate) { this.interestRate = interestRate; }

    public Integer getTenureMonths() { return tenureMonths; }
    public void setTenureMonths(Integer tenureMonths) { this.tenureMonths = tenureMonths; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    // Helper method to calculate monthly EMI
    public double calculateMonthlyEmi() {
        if (loanAmount == null || interestRate == null || tenureMonths == null || tenureMonths == 0) {
            return 0.0;
        }
        double monthlyRate = (interestRate / 100) / 12;
        return (loanAmount * monthlyRate * Math.pow(1 + monthlyRate, tenureMonths)) 
                / (Math.pow(1 + monthlyRate, tenureMonths) - 1);
    }
}
package com.crimsonlogic.bankingandloanmanagementsystem.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import com.crimsonlogic.bankingandloanmanagementsystem.entities.Loan;

public interface LoanMapper {
    int insertLoan(Loan loan);
    Loan getLoanById(@Param("loanId") String loanId);
    List<Loan> getLoansByCustomerId(@Param("customerId") String customerId);
    List<Loan> getAllLoans();
    List<Loan> getLoansByStatus(@Param("status") String status);
    int updateLoanStatus(@Param("loanId") String loanId, @Param("status") String status);
}
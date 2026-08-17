package com.crimsonlogic.bankingandloanmanagementsystem.dao;

import java.util.List;
import org.apache.ibatis.session.SqlSession;
import com.crimsonlogic.bankingandloanmanagementsystem.config.MyBatisUtil;
import com.crimsonlogic.bankingandloanmanagementsystem.entities.Loan;
import com.crimsonlogic.bankingandloanmanagementsystem.mapper.LoanMapper;

public class LoanDao {

    public boolean insertLoan(Loan loan) {
        try (SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession(true)) {
            LoanMapper mapper = session.getMapper(LoanMapper.class);
            return mapper.insertLoan(loan) > 0;
        }
    }

    public Loan getLoanById(String loanId) {
        try (SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession()) {
            LoanMapper mapper = session.getMapper(LoanMapper.class);
            return mapper.getLoanById(loanId);
        }
    }

    public List<Loan> getLoansByCustomerId(String customerId) {
        try (SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession()) {
            LoanMapper mapper = session.getMapper(LoanMapper.class);
            return mapper.getLoansByCustomerId(customerId);
        }
    }

    public List<Loan> getAllLoans() {
        try (SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession()) {
            LoanMapper mapper = session.getMapper(LoanMapper.class);
            return mapper.getAllLoans();
        }
    }

    public List<Loan> getLoansByStatus(String status) {
        try (SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession()) {
            LoanMapper mapper = session.getMapper(LoanMapper.class);
            return mapper.getLoansByStatus(status);
        }
    }

    public boolean updateLoanStatus(String loanId, String status) {
        try (SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession(true)) {
            LoanMapper mapper = session.getMapper(LoanMapper.class);
            return mapper.updateLoanStatus(loanId, status) > 0;
        }
    }
}
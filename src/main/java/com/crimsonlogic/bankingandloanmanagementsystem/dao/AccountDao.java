package com.crimsonlogic.bankingandloanmanagementsystem.dao;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.session.SqlSession;
import com.crimsonlogic.bankingandloanmanagementsystem.config.MyBatisUtil;
import com.crimsonlogic.bankingandloanmanagementsystem.mapper.AccountMapper;

public class AccountDao {

    public boolean insertAccount(Map<String, Object> accountParams) {
        try (SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession(true)) {
            AccountMapper mapper = session.getMapper(AccountMapper.class);
            return mapper.insertAccount(accountParams) > 0;
        }
    }

    public List<Map<String, Object>> getAccountsByCustomerId(String customerId) {
        try (SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession()) {
            AccountMapper mapper = session.getMapper(AccountMapper.class);
            return mapper.getAccountsByCustomerId(customerId);
        }
    }

    public Map<String, Object> getAccountByNumber(String accountNumber) {
        try (SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession()) {
            AccountMapper mapper = session.getMapper(AccountMapper.class);
            return mapper.getAccountByNumber(accountNumber);
        }
    }

    public boolean updateBalanceAndStatus(String accountNumber, Double balance, String accountStatus) {
        try (SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession(true)) {
            AccountMapper mapper = session.getMapper(AccountMapper.class);
            return mapper.updateBalanceAndStatus(accountNumber, balance, accountStatus) > 0;
        }
    }

    public boolean updateMpin(String accountNumber, String mpin) {
        try (SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession(true)) {
            AccountMapper mapper = session.getMapper(AccountMapper.class);
            return mapper.updateMpin(accountNumber, mpin) > 0;
        }
    }
}
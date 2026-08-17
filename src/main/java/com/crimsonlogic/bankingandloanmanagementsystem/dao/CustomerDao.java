package com.crimsonlogic.bankingandloanmanagementsystem.dao;

import java.util.List;
import org.apache.ibatis.session.SqlSession;
import com.crimsonlogic.bankingandloanmanagementsystem.config.MyBatisUtil;
import com.crimsonlogic.bankingandloanmanagementsystem.mapper.CustomerMapper;
import com.crimsonlogic.bankingandloanmanagementsystem.model.implementations.userimplementations.Customer;

public class CustomerDao {

    public boolean insertCustomer(Customer customer) {
        try (SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession(true)) {
            CustomerMapper mapper = session.getMapper(CustomerMapper.class);
            return mapper.insertCustomer(customer) > 0;
        }
    }

    public Customer getCustomerByEmail(String email) {
        try (SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession()) {
            CustomerMapper mapper = session.getMapper(CustomerMapper.class);
            return mapper.getCustomerByEmail(email);
        }
    }

    public Customer getCustomerById(String customerId) {
        try (SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession()) {
            CustomerMapper mapper = session.getMapper(CustomerMapper.class);
            return mapper.getCustomerById(customerId);
        }
    }

    public List<Customer> getAllCustomers() {
        try (SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession()) {
            CustomerMapper mapper = session.getMapper(CustomerMapper.class);
            return mapper.getAllCustomers();
        }
    }

    public List<Customer> getCustomersByBranchId(String branchId) {
        try (SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession()) {
            CustomerMapper mapper = session.getMapper(CustomerMapper.class);
            return mapper.getCustomersByBranchId(branchId);
        }
    }

    public boolean updateCustomerStatus(String customerId, String status) {
        try (SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession(true)) {
            CustomerMapper mapper = session.getMapper(CustomerMapper.class);
            return mapper.updateCustomerStatus(customerId, status) > 0;
        }
    }

    public boolean updateCustomerPhone(String customerId, String phoneNumber) {
        try (SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession(true)) {
            CustomerMapper mapper = session.getMapper(CustomerMapper.class);
            return mapper.updateCustomerPhone(customerId, phoneNumber) > 0;
        }
    }

    public boolean updateCustomerAddress(String customerId, String address) {
        try (SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession(true)) {
            CustomerMapper mapper = session.getMapper(CustomerMapper.class);
            return mapper.updateCustomerAddress(customerId, address) > 0;
        }
    }

    public boolean updateCustomerNominee(String customerId, String name, String rel, String phone) {
        try (SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession(true)) {
            CustomerMapper mapper = session.getMapper(CustomerMapper.class);
            return mapper.updateCustomerNominee(customerId, name, rel, phone) > 0;
        }
    }

    public boolean updateCustomerPassword(String customerId, String password) {
        try (SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession(true)) {
            CustomerMapper mapper = session.getMapper(CustomerMapper.class);
            return mapper.updateCustomerPassword(customerId, password) > 0;
        }
    }
}
package com.crimsonlogic.bankingandloanmanagementsystem.dao;

import java.util.List;
import org.apache.ibatis.session.SqlSession;
import com.crimsonlogic.bankingandloanmanagementsystem.config.MyBatisUtil;
import com.crimsonlogic.bankingandloanmanagementsystem.mapper.EmployeeMapper;
import com.crimsonlogic.bankingandloanmanagementsystem.model.implementations.userimplementations.Employee;

public class EmployeeDao {

    public boolean insertEmployee(Employee employee) {
        try (SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession(true)) {
            EmployeeMapper mapper = session.getMapper(EmployeeMapper.class);
            return mapper.insertEmployee(employee) > 0;
        }
    }

    public Employee getEmployeeByEmail(String email) {
        try (SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession()) {
            EmployeeMapper mapper = session.getMapper(EmployeeMapper.class);
            return mapper.getEmployeeByEmail(email);
        }
    }

    public Employee getEmployeeById(String employeeId) {
        try (SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession()) {
            EmployeeMapper mapper = session.getMapper(EmployeeMapper.class);
            return mapper.getEmployeeById(employeeId);
        }
    }

    public List<Employee> getAllEmployees() {
        try (SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession()) {
            EmployeeMapper mapper = session.getMapper(EmployeeMapper.class);
            return mapper.getAllEmployees();
        }
    }

    public List<Employee> getEmployeesByBranchId(String branchId) {
        try (SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession()) {
            EmployeeMapper mapper = session.getMapper(EmployeeMapper.class);
            return mapper.getEmployeesByBranchId(branchId);
        }
    }

    public boolean updateEmployeeStatus(String employeeId, String status) {
        try (SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession(true)) {
            EmployeeMapper mapper = session.getMapper(EmployeeMapper.class);
            return mapper.updateEmployeeStatus(employeeId, status) > 0;
        }
    }
}
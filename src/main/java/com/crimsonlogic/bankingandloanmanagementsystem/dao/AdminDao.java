package com.crimsonlogic.bankingandloanmanagementsystem.dao;

import java.util.List;
import org.apache.ibatis.session.SqlSession;
import com.crimsonlogic.bankingandloanmanagementsystem.config.MyBatisUtil;
import com.crimsonlogic.bankingandloanmanagementsystem.mapper.AdminMapper;
import com.crimsonlogic.bankingandloanmanagementsystem.model.implementations.userimplementations.Admin;

public class AdminDao {

    public boolean insertAdmin(Admin admin) {
        try (SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession(true)) {
            AdminMapper mapper = session.getMapper(AdminMapper.class);
            return mapper.insertAdmin(admin) > 0;
        }
    }

    public Admin getAdminByEmail(String email) {
        try (SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession()) {
            AdminMapper mapper = session.getMapper(AdminMapper.class);
            return mapper.getAdminByEmail(email);
        }
    }

    public Admin getAdminById(String adminId) {
        try (SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession()) {
            AdminMapper mapper = session.getMapper(AdminMapper.class);
            return mapper.getAdminById(adminId);
        }
    }

    public List<Admin> getAllAdmins() {
        try (SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession()) {
            AdminMapper mapper = session.getMapper(AdminMapper.class);
            return mapper.getAllAdmins();
        }
    }

    public boolean updateAdminStatus(String adminId, String status) {
        try (SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession(true)) {
            AdminMapper mapper = session.getMapper(AdminMapper.class);
            return mapper.updateAdminStatus(adminId, status) > 0;
        }
    }
}
package com.crimsonlogic.bankingandloanmanagementsystem.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import com.crimsonlogic.bankingandloanmanagementsystem.model.implementations.userimplementations.Admin;

public interface AdminMapper {
    int insertAdmin(Admin admin);
    Admin getAdminByEmail(@Param("email") String email);
    Admin getAdminById(@Param("adminId") String adminId);
    List<Admin> getAllAdmins();
    int updateAdminStatus(@Param("adminId") String adminId, @Param("status") String status);
}
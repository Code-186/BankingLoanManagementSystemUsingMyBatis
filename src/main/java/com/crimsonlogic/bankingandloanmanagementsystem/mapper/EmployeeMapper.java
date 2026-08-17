package com.crimsonlogic.bankingandloanmanagementsystem.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import com.crimsonlogic.bankingandloanmanagementsystem.model.implementations.userimplementations.Employee;

public interface EmployeeMapper {
    int insertEmployee(Employee employee);
    Employee getEmployeeByEmail(@Param("email") String email);
    Employee getEmployeeById(@Param("employeeId") String employeeId);
    List<Employee> getAllEmployees();
    List<Employee> getEmployeesByBranchId(@Param("branchId") String branchId);
    int updateEmployeeStatus(@Param("employeeId") String employeeId, @Param("status") String status);
}
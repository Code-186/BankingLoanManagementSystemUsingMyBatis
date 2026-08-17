package com.crimsonlogic.bankingandloanmanagementsystem.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import com.crimsonlogic.bankingandloanmanagementsystem.model.implementations.userimplementations.Customer;

public interface CustomerMapper {
    int insertCustomer(Customer customer);
    Customer getCustomerByEmail(@Param("email") String email);
    Customer getCustomerById(@Param("customerId") String customerId);
    List<Customer> getAllCustomers();
    List<Customer> getCustomersByBranchId(@Param("branchId") String branchId);
    int updateCustomerStatus(@Param("customerId") String customerId, @Param("status") String status);
    int updateCustomerPhone(@Param("customerId") String customerId, @Param("phoneNumber") String phoneNumber);
    int updateCustomerAddress(@Param("customerId") String customerId, @Param("address") String address);
    int updateCustomerNominee(@Param("customerId") String customerId, 
                              @Param("nomineeName") String nomineeName, 
                              @Param("nomineeRelationship") String nomineeRelationship, 
                              @Param("nomineePhoneNumber") String nomineePhoneNumber);
    int updateCustomerPassword(@Param("customerId") String customerId, @Param("password") String password);
}
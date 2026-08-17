package com.crimsonlogic.bankingandloanmanagementsystem.mapper;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Param;

public interface AccountMapper {
    int insertAccount(Map<String, Object> params);
    List<Map<String, Object>> getAccountsByCustomerId(@Param("customerId") String customerId);
    Map<String, Object> getAccountByNumber(@Param("accountNumber") String accountNumber);
    int updateBalanceAndStatus(@Param("accountNumber") String accountNumber, 
                               @Param("balance") Double balance, 
                               @Param("accountStatus") String accountStatus);
    int updateMpin(@Param("accountNumber") String accountNumber, @Param("mpin") String mpin);
}
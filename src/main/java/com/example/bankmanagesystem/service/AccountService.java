package com.example.bankmanagesystem.service;

import com.example.bankmanagesystem.dto.account.*;


public interface AccountService {

    AccountResponseDTO createAccount(CreateAccountDTO dto);

    BalanceResponseDTO getBalance(String accountNumber);

    AccountResponseDTO findByAccountNumber(String accountNumber);

    // 新增：根据用户ID查询其账户列表
    java.util.List<AccountResponseDTO> findByUserId(Long userId);
}

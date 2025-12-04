package com.example.bankmanagesystem.service;

import com.example.bankmanagesystem.dto.account.*;


public interface AccountService {

    AccountResponseDTO createAccount(CreateAccountDTO dto);

    BalanceResponseDTO getBalance(String accountNumber);

    AccountResponseDTO findByAccountNumber(String accountNumber);
}

package com.example.bankmanagesystem.service.impl;

import com.example.bankmanagesystem.dto.account.*;
import com.example.bankmanagesystem.entity.Account;
import com.example.bankmanagesystem.entity.User;
import com.example.bankmanagesystem.exception.BusinessException;
import com.example.bankmanagesystem.exception.ErrorCode;
import com.example.bankmanagesystem.repository.AccountRepository;
import com.example.bankmanagesystem.repository.UserRepository;
import com.example.bankmanagesystem.service.AccountService;
import com.example.bankmanagesystem.service.helper.AccountNumberGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final UserRepository userRepository;
    private final AccountNumberGenerator accountNumberGenerator;

    @Override
    public AccountResponseDTO createAccount(CreateAccountDTO dto) {

        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));

        String accountNumber = accountNumberGenerator.generateAccountNumber();

        Account account = new Account();
        account.setUser(user);
        account.setAccountNumber(accountNumber);
        account.setType(dto.getType());
        account.setBalance(BigDecimal.ZERO);

        accountRepository.save(account);

        return new AccountResponseDTO(
                account.getId(),
                account.getAccountNumber(),
                account.getType(),
                account.getBalance()
        );
    }

    @Override
    public BalanceResponseDTO getBalance(String accountNumber) {

        Account account = accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new BusinessException(ErrorCode.ACCOUNT_NOT_FOUND));

        return new BalanceResponseDTO(
                account.getAccountNumber(),
                account.getBalance()
        );
    }

    @Override
    public AccountResponseDTO findByAccountNumber(String accountNumber) {

        Account account = accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new BusinessException(ErrorCode.ACCOUNT_NOT_FOUND));

        return new AccountResponseDTO(
                account.getId(),
                account.getAccountNumber(),
                account.getType(),
                account.getBalance()
        );
    }

    @Override
    public List<AccountResponseDTO> findByUserId(Long userId) {
        List<Account> accounts = accountRepository.findByUserId(userId);
        return accounts.stream()
                .map(a -> new AccountResponseDTO(
                        a.getId(),
                        a.getAccountNumber(),
                        a.getType(),
                        a.getBalance()
                ))
                .collect(Collectors.toList());
    }
}

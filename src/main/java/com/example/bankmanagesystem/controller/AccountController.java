package com.example.bankmanagesystem.controller;

import com.example.bankmanagesystem.dto.account.*;
import com.example.bankmanagesystem.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/accounts")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    /**
     * 创建账户
     */
    @PostMapping("/create")
    public AccountResponseDTO createAccount(@RequestBody CreateAccountDTO dto) {
        return accountService.createAccount(dto);
    }

    /**
     * 查询余额
     */
    @GetMapping("/{accountNumber}/balance")
    public BalanceResponseDTO getBalance(@PathVariable String accountNumber) {
        return accountService.getBalance(accountNumber);
    }

    /**
     * 查询账户详情
     */
    @GetMapping("/{accountNumber}")
    public AccountResponseDTO getAccount(@PathVariable String accountNumber) {
        return accountService.findByAccountNumber(accountNumber);
    }
}

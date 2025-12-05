package com.example.bankmanagesystem.controller;

import com.example.bankmanagesystem.dto.account.*;
import com.example.bankmanagesystem.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpServletRequest;
import java.util.List;
import com.example.bankmanagesystem.util.JwtUtil;

@RestController
@RequestMapping("/api/accounts")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;
    private final JwtUtil jwtUtil;

    /**
     * 创建账户
     */
    @PostMapping("/create")
    public AccountResponseDTO createAccount(@RequestBody CreateAccountDTO dto, HttpServletRequest request) {
        String auth = request.getHeader("Authorization");
        if (auth != null && auth.startsWith("Bearer ")) {
            String token = auth.substring(7);
            Long userId = jwtUtil.getUserId(token);
            dto.setUserId(userId);
        }
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

    /**
     * 获取当前登录用户的账户列表
     */
    @GetMapping("/my")
    public List<AccountResponseDTO> myAccounts(HttpServletRequest request) {
        String auth = request.getHeader("Authorization");
        if (auth != null && auth.startsWith("Bearer ")) {
            String token = auth.substring(7);
            Long userId = jwtUtil.getUserId(token);
            return accountService.findByUserId(userId);
        }
        return List.of();
    }
}

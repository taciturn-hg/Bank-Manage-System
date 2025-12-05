package com.example.bankmanagesystem.controller;

import com.example.bankmanagesystem.dto.transaction.DepositDTO;
import com.example.bankmanagesystem.dto.transaction.WithdrawDTO;
import com.example.bankmanagesystem.dto.transaction.TransferDTO;
import com.example.bankmanagesystem.entity.Transaction;
import com.example.bankmanagesystem.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import com.example.bankmanagesystem.util.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import com.example.bankmanagesystem.dto.transaction.TransactionRecordDTO;
import java.util.List;

@RestController
@RequestMapping("/api/transactions")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;
    private final JwtUtil jwtUtil;

    @PostMapping("/deposit")
    public Transaction deposit(@RequestBody DepositDTO dto) {
        return transactionService.deposit(dto);
    }

    @PostMapping("/withdraw")
    public Transaction withdraw(@RequestBody WithdrawDTO dto) {
        return transactionService.withdraw(dto);
    }

    @PostMapping("/transfer")
    public Transaction transfer(@RequestBody TransferDTO dto) {
        return transactionService.transfer(dto);
    }

    /**
     * 获取当前登录用户的交易记录
     */
    @GetMapping("/my")
    public List<TransactionRecordDTO> myTransactions(@RequestParam(required = false) String type, HttpServletRequest request) {
        String auth = request.getHeader("Authorization");
        if (auth != null && auth.startsWith("Bearer ")) {
            String token = auth.substring(7);
            Long userId = jwtUtil.getUserId(token);
            return transactionService.myTransactions(userId, type);
        }
        return List.of();
    }
}

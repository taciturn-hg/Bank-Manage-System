package com.example.bankmanagesystem.controller;

import com.example.bankmanagesystem.dto.transaction.DepositDTO;
import com.example.bankmanagesystem.dto.transaction.WithdrawDTO;
import com.example.bankmanagesystem.dto.transaction.TransferDTO;
import com.example.bankmanagesystem.entity.Transaction;
import com.example.bankmanagesystem.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/transactions")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

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
}

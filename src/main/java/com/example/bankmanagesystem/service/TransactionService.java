package com.example.bankmanagesystem.service;

import com.example.bankmanagesystem.dto.transaction.DepositDTO;
import com.example.bankmanagesystem.dto.transaction.WithdrawDTO;
import com.example.bankmanagesystem.dto.transaction.TransferDTO;
import com.example.bankmanagesystem.entity.Transaction;

public interface TransactionService {

    Transaction deposit(DepositDTO dto);

    Transaction withdraw(WithdrawDTO dto);

    Transaction transfer(TransferDTO dto);
}

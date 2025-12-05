package com.example.bankmanagesystem.service;

import com.example.bankmanagesystem.dto.transaction.DepositDTO;
import com.example.bankmanagesystem.dto.transaction.WithdrawDTO;
import com.example.bankmanagesystem.dto.transaction.TransferDTO;
import com.example.bankmanagesystem.entity.Transaction;
import com.example.bankmanagesystem.dto.transaction.TransactionRecordDTO;
import java.util.List;

public interface TransactionService {

    Transaction deposit(DepositDTO dto);

    Transaction withdraw(WithdrawDTO dto);

    Transaction transfer(TransferDTO dto);

    // 为当前用户查询其所有账户的交易记录（可选按类型过滤）
    List<TransactionRecordDTO> myTransactions(Long userId, String type);
}

package com.example.bankmanagesystem.service.impl;

import com.example.bankmanagesystem.dto.transaction.DepositDTO;
import com.example.bankmanagesystem.dto.transaction.WithdrawDTO;
import com.example.bankmanagesystem.dto.transaction.TransferDTO;
import com.example.bankmanagesystem.entity.Account;
import com.example.bankmanagesystem.entity.Transaction;
import com.example.bankmanagesystem.exception.BusinessException;
import com.example.bankmanagesystem.exception.ErrorCode;
import com.example.bankmanagesystem.repository.AccountRepository;
import com.example.bankmanagesystem.repository.TransactionRepository;
import com.example.bankmanagesystem.service.TransactionService;
import com.example.bankmanagesystem.service.helper.TransactionValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;
    private final TransactionValidator validator;

    /**
     * 存款
     */
    @Override
    @Transactional
    public Transaction deposit(DepositDTO dto) {

        // 幂等校验
        if (transactionRepository.existsByTxId(dto.getTxId())) {
            throw new BusinessException(ErrorCode.COMMIT_DUPLICATE_REQUEST);
        }

        validator.validateAmount(dto.getAmount());

        // 悲观锁账户
        Account account = accountRepository.findByAccountNumberForUpdate(dto.getAccountNumber())
                .orElseThrow(() -> new BusinessException(ErrorCode.ACCOUNT_NOT_FOUND));

        // 修改余额
        account.setBalance(account.getBalance().add(dto.getAmount()));
        accountRepository.save(account);

        // 记录交易
        return saveTransaction(
                dto.getTxId(),
                null,
                dto.getAccountNumber(),
                dto.getAmount(),
                "deposit",
                "SUCCESS",
                "用户存款"
        );
    }

    /**
     * 取款
     */
    @Override
    @Transactional
    public Transaction withdraw(WithdrawDTO dto) {

        if (transactionRepository.existsByTxId(dto.getTxId())) {
            throw new BusinessException(ErrorCode.DEPOSIT_DUPLICATE_REQUEST);
        }

        validator.validateAmount(dto.getAmount());

        Account account = accountRepository.findByAccountNumberForUpdate(dto.getAccountNumber())
                .orElseThrow(() -> new BusinessException(ErrorCode.ACCOUNT_NOT_FOUND));

        if (account.getBalance().compareTo(dto.getAmount()) < 0) {
            throw new BusinessException(ErrorCode.BALANCE_NOT_ENOUGH);
        }

        account.setBalance(account.getBalance().subtract(dto.getAmount()));
        accountRepository.save(account);

        return saveTransaction(
                dto.getTxId(),
                dto.getAccountNumber(),
                null,
                dto.getAmount(),
                "withdraw",
                "SUCCESS",
                "用户取款"
        );
    }

    /**
     * 转账
     */
    @Override
    @Transactional
    public Transaction transfer(TransferDTO dto) {

        if (transactionRepository.existsByTxId(dto.getTxId())) {
            throw new BusinessException(ErrorCode.TRANSACTION_DUPLICATE_REQUEST);
        }

        validator.validateAmount(dto.getAmount());

        // 加锁两个账户（顺序很重要，防止死锁）
        Account from = accountRepository.findByAccountNumberForUpdate(dto.getFromAccount())
                .orElseThrow(() -> new BusinessException(ErrorCode.FROM_ACCOUNT_NOT_FOUND));

        Account to = accountRepository.findByAccountNumberForUpdate(dto.getToAccount())
                .orElseThrow(() -> new BusinessException(ErrorCode.TO_ACCOUNT_NOT_FOUND));

        if (from.getBalance().compareTo(dto.getAmount()) < 0) {
            throw new BusinessException(ErrorCode.BALANCE_NOT_ENOUGH);
        }

        from.setBalance(from.getBalance().subtract(dto.getAmount()));
        to.setBalance(to.getBalance().add(dto.getAmount()));

        accountRepository.save(from);
        accountRepository.save(to);

        return saveTransaction(
                dto.getTxId(),
                dto.getFromAccount(),
                dto.getToAccount(),
                dto.getAmount(),
                "transfer",
                "SUCCESS",
                "用户转账"
        );
    }

    /**
     * 保存交易记录
     */
    private Transaction saveTransaction(
            String txId,
            String from,
            String to,
            BigDecimal amount,
            String type,
            String status,
            String remark
    ) {
        Transaction tx = new Transaction();
        tx.setTxId(txId);
        tx.setFromAccount(from);
        tx.setToAccount(to);
        tx.setAmount(amount);
        tx.setType(type);
        tx.setStatus(status);
        tx.setRemark(remark);
        tx.setCreatedTime(LocalDateTime.now());
        return transactionRepository.save(tx);
    }
}

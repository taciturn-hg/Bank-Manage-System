package com.example.bankmanagesystem.repository;

import com.example.bankmanagesystem.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    Optional<Transaction> findByTxId(String txId);

    @Query("SELECT t FROM Transaction t WHERE t.fromAccount = :accountNumber OR t.toAccount = :accountNumber ORDER BY t.createdTime DESC")
    List<Transaction> findByAccountNumber(@Param("accountNumber") String accountNumber);

    // 兼容测试：同义方法
    @Query("SELECT t FROM Transaction t WHERE t.fromAccount = :accountNumber OR t.toAccount = :accountNumber ORDER BY t.createdTime DESC")
    List<Transaction> findAllByAccountNumber(@Param("accountNumber") String accountNumber);

    List<Transaction> findByCreatedTimeBetween(LocalDateTime start, LocalDateTime end);

    // 修复这一行
    List<Transaction> findByFromAccountAndType(String fromAccount, String type);
    List<Transaction> findByToAccountAndType(String toAccount, String type);

    // 兼容测试：方法名包含下划线，使用显式查询实现
    @Query("SELECT t FROM Transaction t WHERE t.fromAccount = :accountNumber AND t.type = :type ORDER BY t.createdTime DESC")
    List<Transaction> findByFromAccount_AccountNumberAndType(@Param("accountNumber") String accountNumber, @Param("type") String type);

    boolean existsByTxId(String txId);

    // 可选：同时查询转入和转出某种类型交易
    @Query("SELECT t FROM Transaction t WHERE (t.fromAccount = :accountNumber OR t.toAccount = :accountNumber) AND t.type = :type ORDER BY t.createdTime DESC")
    List<Transaction> findByAccountNumberAndType(@Param("accountNumber") String accountNumber, @Param("type") String type);
}

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

    // 根据交易ID查询（用于幂等校验）
    Optional<Transaction> findByTxId(String txId);

    // 查询某个账户的所有交易（转入或转出）
    @Query("SELECT t FROM Transaction t WHERE t.fromAccount.accountNumber = :accountNumber OR t.toAccount.accountNumber = :accountNumber ORDER BY t.createdTime DESC")
    List<Transaction> findAllByAccountNumber(@Param("accountNumber") String accountNumber);

    // 查询某个时间段内的交易
    List<Transaction> findByCreatedTimeBetween(LocalDateTime start, LocalDateTime end);

    // 查询某个账户的某种类型交易
    List<Transaction> findByFromAccount_AccountNumberAndType(String accountNumber, String type);

    boolean existsByTxId(String txId);
}

package com.example.bankmanagesystem.repository;

import com.example.bankmanagesystem.entity.Account;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    // 根据账户号查询
    Optional<Account> findByAccountNumber(String accountNumber);

    // 根据用户ID查询其所有账户
    List<Account> findByUserId(Long userId);

    // 根据账户号查询并加锁（用于转账事务）
    @Query("SELECT a FROM Account a WHERE a.accountNumber = :accountNumber")
    Optional<Account> findByAccountNumberForUpdate(@Param("accountNumber") String accountNumber);

    // 查询所有正常状态的账户
    List<Account> findByStatus(Integer status);

    Optional<Object> lockByAccountNumber(@NotBlank(message = "账户号不能为空") String accountNumber);
}

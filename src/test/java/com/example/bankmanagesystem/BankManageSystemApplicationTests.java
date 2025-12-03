package com.example.bankmanagesystem;

import com.example.bankmanagesystem.entity.Account;
import com.example.bankmanagesystem.entity.Transaction;
import com.example.bankmanagesystem.entity.User;
import com.example.bankmanagesystem.repository.AccountRepository;
import com.example.bankmanagesystem.repository.TransactionRepository;
import com.example.bankmanagesystem.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BankManageSystemApplicationTests {

    @Autowired
    private UserRepository userRepository;

    @Test
    @DisplayName("测试User和UserRespository接口是否能正常运行")
    void contextLoads() {
        User user = User.builder()
                .username("wxy")
                .password("123")
                .name("小王")
                .id_card("123123123")
                .phone("123123123")
                .build();

        userRepository.save(user);

        assertTrue(userRepository.findByUsername("123").isPresent());
        assertTrue(userRepository.existsByUsername("123"));
    }

    @Autowired
    private AccountRepository accountRepository;

    @Test
    @DisplayName("测试Account和AccountRepository接口是否能正常运行")
    void testAccountRepository() {
        // 1. 先创建或获取一个用户
        User user;
        Optional<User> existingUser = userRepository.findByUsername("test_user_for_account");
        if (existingUser.isPresent()) {
            user = existingUser.get();
        } else {
            user = User.builder()
                    .username("test_user_for_account")
                    .password("test123")
                    .name("测试用户")
                    .id_card("123456789012345678")
                    .phone("13800138000")
                    .build();
            user = userRepository.save(user);
        }

// 2. 创建账户
        Account account = Account.builder()
                .accountNumber("ACCT202400001")
                .user(user)  // 关联用户
                .type("储蓄账户")
                .balance(new BigDecimal("1000.00"))
                .currency("CNY")
                .status(1)
                .build();

// 3. 保存账户
        Account savedAccount = accountRepository.save(account);

// 4. 验证保存成功
        assertNotNull(savedAccount.getId());
        assertEquals("ACCT202400001", savedAccount.getAccountNumber());
        assertEquals("储蓄账户", savedAccount.getType());
        assertEquals(0, new BigDecimal("1000.00").compareTo(savedAccount.getBalance()));
        assertEquals("CNY", savedAccount.getCurrency());
        assertEquals(1, savedAccount.getStatus());
        assertEquals(user.getId(), savedAccount.getUser().getId());

// 5. 测试查询方法
        Optional<Account> foundByAccountNumber = accountRepository.findByAccountNumber("ACCT202400001");
        assertTrue(foundByAccountNumber.isPresent());
        assertEquals(savedAccount.getId(), foundByAccountNumber.get().getId());

// 6. 测试按用户ID查询
        List<Account> userAccounts = accountRepository.findByUserId(user.getId());
        assertFalse(userAccounts.isEmpty());
        assertEquals(savedAccount.getId(), userAccounts.get(0).getId());

// 7. 测试按状态查询
        List<Account> activeAccounts = accountRepository.findByStatus(1);
        assertTrue(activeAccounts.stream()
                .anyMatch(acc -> acc.getAccountNumber().equals("ACCT202400001")));

    }

    @Autowired
    private TransactionRepository transactionRepository;

    @Test
    @DisplayName("测试Transaction和TransactionRepository接口是否能正常运行")
    void testTransactionRepository() {
        transactionRepository.deleteAll();
        accountRepository.deleteAll();
        userRepository.deleteAll();
        
        // 1. 准备测试数据 - 创建两个账户
        User user = userRepository.save(User.builder()
                .username("test_user_for_tx")
                .password("test123")
                .name("交易测试用户")
                .id_card("123456789012345679")
                .phone("13800138001")
                .build());


        Account fromAccount = accountRepository.save(Account.builder()
                .accountNumber("FROM001")
                .user(user)
                .type("储蓄账户")
                .balance(new BigDecimal("5000.00"))
                .currency("CNY")
                .status(1)
                .build());

        Account toAccount = accountRepository.save(Account.builder()
                .accountNumber("TO001")
                .user(user)
                .type("储蓄账户")
                .balance(new BigDecimal("1000.00"))
                .currency("CNY")
                .status(1)
                .build());

// 2. 创建存款交易
        Transaction depositTx = Transaction.builder()
                .txId("DEPOSIT_" + System.currentTimeMillis())
                .toAccount(toAccount)
                .amount(new BigDecimal("500.00"))
                .type("deposit")
                .status("SUCCESS")
                .remark("工资存款")
                .build();

        Transaction savedDepositTx = transactionRepository.save(depositTx);

// 验证存款交易
        assertNotNull(savedDepositTx.getId());
        assertEquals("deposit", savedDepositTx.getType());
        assertEquals("SUCCESS", savedDepositTx.getStatus());
        assertEquals(toAccount.getAccountNumber(), savedDepositTx.getToAccount().getAccountNumber());
        assertNull(savedDepositTx.getFromAccount());  // 存款没有转出账户

// 3. 创建取款交易
        Transaction withdrawTx = Transaction.builder()
                .txId("WITHDRAW_" + System.currentTimeMillis())
                .fromAccount(fromAccount)
                .amount(new BigDecimal("1000.00"))
                .type("withdraw")
                .status("SUCCESS")
                .remark("现金取款")
                .build();

        Transaction savedWithdrawTx = transactionRepository.save(withdrawTx);

// 验证取款交易
        assertEquals("withdraw", savedWithdrawTx.getType());
        assertEquals(fromAccount.getAccountNumber(), savedWithdrawTx.getFromAccount().getAccountNumber());

// 4. 创建转账交易
        Transaction transferTx = Transaction.builder()
                .txId("TRANSFER_" + System.currentTimeMillis())
                .fromAccount(fromAccount)
                .toAccount(toAccount)
                .amount(new BigDecimal("500.00"))
                .type("transfer")
                .status("SUCCESS")
                .remark("朋友转账")
                .build();

        Transaction savedTransferTx = transactionRepository.save(transferTx);

// 验证转账交易
        assertEquals("transfer", savedTransferTx.getType());
        assertEquals(fromAccount.getAccountNumber(), savedTransferTx.getFromAccount().getAccountNumber());
        assertEquals(toAccount.getAccountNumber(), savedTransferTx.getToAccount().getAccountNumber());

// 5. 测试查询方法

// 5.1 按交易ID查询（幂等性检查）
        Optional<Transaction> foundByTxId = transactionRepository.findByTxId(savedDepositTx.getTxId());
        assertTrue(foundByTxId.isPresent());
        assertEquals(savedDepositTx.getId(), foundByTxId.get().getId());

// 5.2 查询账户的所有交易
        List<Transaction> fromAccountTransactions = transactionRepository.findAllByAccountNumber("FROM001");
        assertTrue(fromAccountTransactions.size() >= 2);  // 至少包含取款和转账

        List<Transaction> toAccountTransactions = transactionRepository.findAllByAccountNumber("TO001");
        assertTrue(toAccountTransactions.size() >= 2);  // 至少包含存款和转账

// 5.3 查询时间范围内的交易
        LocalDateTime startTime = LocalDateTime.now().minusMinutes(5);
        LocalDateTime endTime = LocalDateTime.now().plusMinutes(5);
        List<Transaction> recentTransactions = transactionRepository.findByCreatedTimeBetween(startTime, endTime);
        assertTrue(recentTransactions.size() >= 3);

// 5.4 查询特定类型交易
        List<Transaction> depositTransactions = transactionRepository.findByFromAccount_AccountNumberAndType("FROM001", "withdraw");
        assertFalse(depositTransactions.isEmpty());
        assertEquals("withdraw", depositTransactions.get(0).getType());

    }
}

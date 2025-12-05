package com.example.bankmanagesystem.dto.transaction;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class TransferDTO {

    private String txId;          // 幂等 ID
    private String fromAccount;   // 转出账号
    private String toAccount;     // 转入账号
    private BigDecimal amount;    // 金额
    private String remark;        // 转账备注（可选）
}

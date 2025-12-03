package com.example.bankmanagesystem.dto.transaction;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransactionRecordDTO {

    private String txId;
    private String type;            // deposit, withdraw, transfer
    private String fromAccount;     // null 表示无
    private String toAccount;       // null 表示无

    private BigDecimal amount;
    private String status;          // SUCCESS / FAIL

    private LocalDateTime time;
    private String remark;
}

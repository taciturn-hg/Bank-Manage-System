package com.example.bankmanagesystem.dto.transaction;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransferDTO {

    @NotBlank(message = "转出账户不能为空")
    private String fromAccount;

    @NotBlank(message = "转入账户不能为空")
    private String toAccount;

    @DecimalMin(value = "0.01", message = "转账金额必须大于 0")
    private BigDecimal amount;

    // 用于幂等性，防止重复转账（重要）
    @NotBlank(message = "交易 ID 不能为空")
    private String txId;

    private String remark; // 可选
}

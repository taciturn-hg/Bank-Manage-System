package com.example.bankmanagesystem.dto.transaction;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DepositDTO {

    @NotBlank(message = "账户号不能为空")
    private String accountNumber;

    @DecimalMin(value = "0.01", message = "存款金额必须大于 0")
    private BigDecimal amount;

    private String remark; // 可选
}

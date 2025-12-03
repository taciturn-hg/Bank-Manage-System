package com.example.bankmanagesystem.dto.account;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateAccountDTO {

    @NotBlank(message = "账户类型不能为空")
    private String accountType;   // 如：saving / salary / investment

    @NotBlank(message = "币种不能为空")
    private String currency;      // 如：CNY / USD
}

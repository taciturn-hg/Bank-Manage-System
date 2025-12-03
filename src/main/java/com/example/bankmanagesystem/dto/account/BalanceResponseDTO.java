package com.example.bankmanagesystem.dto.account;

import lombok.*;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BalanceResponseDTO {

    private String accountNumber;
    private BigDecimal balance;
    private String currency;
}

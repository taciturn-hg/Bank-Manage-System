package com.example.bankmanagesystem.dto.account;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class AccountResponseDTO {
    private Long id;
    private String accountNumber;
    private String type;
    private BigDecimal balance;
}

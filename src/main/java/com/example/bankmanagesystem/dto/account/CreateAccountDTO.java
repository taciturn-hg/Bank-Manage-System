package com.example.bankmanagesystem.dto.account;

import lombok.Data;

@Data
public class CreateAccountDTO {
    private Long userId;
    private String type;  // SAVINGS / CHECKING
}

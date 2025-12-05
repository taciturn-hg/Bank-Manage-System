package com.example.bankmanagesystem.service.helper;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class TransactionValidator {

    public void validateAmount(BigDecimal amount) {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("金额必须大于 0");
        }
    }

    public void validateAccount(String accountNumber) {
        if (accountNumber == null || accountNumber.isEmpty()) {
            throw new IllegalArgumentException("账户号不能为空");
        }
    }
}

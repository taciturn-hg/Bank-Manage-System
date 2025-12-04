package com.example.bankmanagesystem.service.helper;

import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class AccountNumberGenerator {

    public String generateAccountNumber() {
        return "AC-" + UUID.randomUUID().toString().substring(0, 10).toUpperCase();
    }
}

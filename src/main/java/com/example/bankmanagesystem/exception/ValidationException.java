package com.example.bankmanagesystem.exception;

import lombok.Getter;

@Getter
public class ValidationException extends RuntimeException {

    private final int code;

    public ValidationException(String message) {
        super(message);
        this.code = ErrorCode.VALIDATION_ERROR.getCode();
    }

    public ValidationException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.code = errorCode.getCode();
    }
}

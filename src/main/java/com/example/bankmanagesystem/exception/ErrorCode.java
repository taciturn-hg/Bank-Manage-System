package com.example.bankmanagesystem.exception;

import lombok.Getter;

@Getter
public enum ErrorCode {
    // 用户相关
    USER_NOT_FOUND(1001, "用户不存在"),
    USER_ALREADY_EXISTS(1002, "用户已存在"),
    PASSWORD_ERROR(1003, "密码错误"),

    // 参数校验
    VALIDATION_ERROR(2001, "参数校验失败"),

    // 业务异常
    BUSINESS_ERROR(3001, "业务异常"),

    // 系统异常
    SYSTEM_ERROR(5000, "系统异常");

    private final int code;
    private final String message;

    ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }
}

package com.example.bankmanagesystem.exception;

import jakarta.validation.ConstraintViolationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;

public class GlobalExceptionHandler {
    /**
     * 处理业务异常
     */
    @ExceptionHandler(BusinessException.class)
    public ApiResponse<?> handleBusinessException(BusinessException e) {
        return ApiResponse.error(e.getCode(), e.getMessage());
    }

    /**
     * 处理参数校验异常（@Valid）
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ApiResponse<?> handleValidException(MethodArgumentNotValidException e) {
        String msg = e.getBindingResult().getFieldError().getDefaultMessage();
        return ApiResponse.error(ErrorCode.VALIDATION_ERROR.getCode(), msg);
    }

    /**
     * 处理 ConstraintViolationException
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public ApiResponse<?> handleConstraintException(ConstraintViolationException e) {
        return ApiResponse.error(ErrorCode.VALIDATION_ERROR.getCode(), e.getMessage());
    }

    /**
     * 处理 ValidationException
     */
    @ExceptionHandler(ValidationException.class)
    public ApiResponse<?> handleValidationException(ValidationException e) {
        return ApiResponse.error(e.getCode(), e.getMessage());
    }

    /**
     * 处理未知异常
     */
    @ExceptionHandler(Exception.class)
    public ApiResponse<?> handleException(Exception e) {
        e.printStackTrace();
        return ApiResponse.error(ErrorCode.SYSTEM_ERROR.getCode(), "系统异常，请稍后再试");
    }
}

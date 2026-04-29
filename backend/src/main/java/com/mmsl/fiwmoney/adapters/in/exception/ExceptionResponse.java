package com.mmsl.fiwmoney.adapters.in.exception;

public record ExceptionResponse (
    String title,
    int errorCode,
    String message
){}
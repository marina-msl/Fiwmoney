package com.mmsl.fiwmoney.domain.exception;

import lombok.Getter;

@Getter
public abstract class AbstractException extends RuntimeException{
    private String title;
    private int errorCode;
    
    public AbstractException(String title, int errorCode, String message) {
        super(message);
        this.title = title;
        this.errorCode = errorCode;
    }
}

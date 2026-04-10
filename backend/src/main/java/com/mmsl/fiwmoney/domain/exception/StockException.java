package com.mmsl.fiwmoney.domain.exception;

public class StockException extends RuntimeException {

    public StockException(String msg) {
        System.out.println(msg);
    }
}
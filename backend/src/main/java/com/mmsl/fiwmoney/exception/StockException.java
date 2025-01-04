package com.mmsl.fiwmoney.exception;

public class StockException extends RuntimeException {

    public StockException(String msg) {
        System.out.println(msg);
    }
}
package com.mmsl.fiwmoney.dto;

public class StockRequest {

    private String code;
    private double averagePrice;
    private boolean notify;

    public String getCode() {
        return code;
    }

    public double getAveragePrice() {
        return averagePrice;
    }

    public boolean getNotify() {
        return notify;
    }
}

package com.mmsl.fiwmoney.dto;

public class StockResult {

    private String code;
    private double currentPrice;
    private double averagePrice;

    public StockResult(String code, double currentPrice, double averagePrice) {
        this.code = code;
        this.currentPrice = currentPrice;
        this.averagePrice = averagePrice;
    }

    public String getCode() {
        return code;
    }

    public double getCurrentPrice() {
        return currentPrice;
    }

    public double getAveragePrice() {
        return averagePrice;
    }
}

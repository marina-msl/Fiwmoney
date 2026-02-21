package com.mmsl.fiwmoney.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
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

    public boolean isNotify() {
        return notify;
    }
}

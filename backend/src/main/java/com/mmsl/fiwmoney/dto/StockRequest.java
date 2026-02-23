package com.mmsl.fiwmoney.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class StockRequest {

    private String code;
    private BigDecimal averagePrice;
    private boolean notify;

    public String getCode() {
        return code;
    }

    public BigDecimal getAveragePrice() {
        return averagePrice;
    }

    public boolean isNotify() {
        return notify;
    }
}

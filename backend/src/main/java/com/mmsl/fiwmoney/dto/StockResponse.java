package com.mmsl.fiwmoney.dto;

import java.math.BigDecimal;

import com.mmsl.fiwmoney.domain.entities.Stock;

public record StockResponse(
    Long id,
    String code,
    BigDecimal currentPrice,
    BigDecimal averagePrice,
    boolean notifyEnabled
) {

    public static StockResponse fromEntity(Stock stock) {
        return new StockResponse(
            stock.getId(),
            stock.getCode(),
            stock.getCurrentPrice(),
            stock.getAveragePrice(),
            stock.isNotify()
        );
    }

}

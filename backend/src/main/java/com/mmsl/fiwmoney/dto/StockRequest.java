package com.mmsl.fiwmoney.dto;

import java.math.BigDecimal;

import com.mmsl.fiwmoney.domain.entities.Stock;

public record StockRequest(
    String code,
    BigDecimal averagePrice,
    boolean notifyEnabled
) {

    public Stock toEntity(BigDecimal currentPrice) {
        return Stock.builder()
                .code(this.code)
                .currentPrice(currentPrice)
                .averagePrice(this.averagePrice)
                .notify(this.notifyEnabled)
                .build();
    }

}

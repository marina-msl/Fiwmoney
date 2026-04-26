package com.mmsl.fiwmoney.domain.entities;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@AllArgsConstructor
public class Stock {

    private Long id;
    private String code;
    private BigDecimal currentPrice;
    private BigDecimal averagePrice;
    private boolean notify;

}
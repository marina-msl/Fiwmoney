package com.mmsl.fiwmoney.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class StockDTO {

    private Long id;
    private String code;
    private BigDecimal currentPrice;
    private BigDecimal averagePrice;
    private boolean notify;

}

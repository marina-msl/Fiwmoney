package com.mmsl.fiwmoney.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class StockResultMin {

    private String code;
    private String currency;
    private BigDecimal price;
    private String shortName;


}

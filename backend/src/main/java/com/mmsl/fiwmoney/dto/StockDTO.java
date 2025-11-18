package com.mmsl.fiwmoney.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class StockDTO {

    private String code;
    private double currentPrice;
    private double averagePrice;
    private boolean notify;

}

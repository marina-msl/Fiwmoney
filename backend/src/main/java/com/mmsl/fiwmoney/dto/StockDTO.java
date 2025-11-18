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

    // public StockDTO(String code, double currentPrice, double averagePrice, boolean notify) {
    //     this.code = code;
    //     this.currentPrice = currentPrice;
    //     this.averagePrice = averagePrice;
    //     this.notify = notify;
    // }

    // public String getCode() {
    //     return code;
    // }

    // public double getCurrentPrice() {
    //     return currentPrice;
    // }

    // public double getAveragePrice() {
    //     return averagePrice;
    // }

    // public boolean getNotify() {
    //     return notify;
    // }
}

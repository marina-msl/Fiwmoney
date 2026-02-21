package com.mmsl.fiwmoney.model;

import com.mmsl.fiwmoney.dto.StockDTO;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Entity
@Table(name = "stock")
public class Stock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long walletId;
    private String code;
    private BigDecimal currentPrice;
    private BigDecimal averagePrice;
    private boolean notify;

    public static Stock to(StockDTO stockDTO) {
       Stock stock = new Stock();
       stock.setCode(stockDTO.getCode());
       stock.setCurrentPrice(stockDTO.getCurrentPrice());
       stock.setAveragePrice(stockDTO.getAveragePrice());
       stock.setNotify(stockDTO.isNotify());

       return stock;
    }
}
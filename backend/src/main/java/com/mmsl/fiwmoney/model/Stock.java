package com.mmsl.fiwmoney.model;

import com.mmsl.fiwmoney.dto.StockDTO;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Data;

@Data
@Entity
@Table(name = "stock", uniqueConstraints= @UniqueConstraint(columnNames="code"))
public class Stock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String code;
    private Double currentPrice;
    private Double averagePrice;
    private boolean notify;

    public Stock() {

    }

    public Stock(Long id, String code, Double currentPrice, Double averagePrice,
                boolean notify) {
        this.id = id;
        this.code = code;
        this.currentPrice = currentPrice;
        this.averagePrice = averagePrice;
        this.notify = notify;
    }


    public static Stock to(StockDTO stockDTO) {
       Stock stock = new Stock();
       stock.setCode(stockDTO.getCode());
       stock.setCurrentPrice(stockDTO.getCurrentPrice());
       stock.setAveragePrice(stockDTO.getAveragePrice());
       stock.setNotify(stockDTO.isNotify());

       return stock;
    }
}
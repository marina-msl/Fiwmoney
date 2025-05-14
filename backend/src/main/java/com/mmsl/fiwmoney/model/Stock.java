package com.mmsl.fiwmoney.model;

import com.mmsl.fiwmoney.dto.StockResult;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(name = "stocks", uniqueConstraints= @UniqueConstraint(columnNames="code"))
public class Stock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String code;
    private Double currentPrice;
    private Double averagePrice;

    public Stock() {

    }

    public Stock(Long id, String code, Double currentPrice, Double averagePrice) {
        this.id = id;
        this.code = code;
        this.currentPrice = currentPrice;
        this.averagePrice = averagePrice;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((code == null) ? 0 : code.hashCode());
        result = prime * result + ((currentPrice == null) ? 0 : currentPrice.hashCode());
        result = prime * result + ((averagePrice == null) ? 0 : averagePrice.hashCode());
        return result;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setCurrentPrice(Double currentPrice) {
        this.currentPrice = currentPrice;
    }

    public void setAveragePrice(Double averagePrice) {
        this.averagePrice = averagePrice;
    }

    public Long getId() {
        return id;
    }

    public String getCode() {
        return code;
    }

    public Double getCurrentPrice() {
        return currentPrice;
    }

    public Double getAveragePrice() {
        return averagePrice;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Stock other = (Stock) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (code == null) {
            if (other.code != null)
                return false;
        } else if (!code.equals(other.code))
            return false;
        if (currentPrice == null) {
            if (other.currentPrice != null)
                return false;
        } else if (!currentPrice.equals(other.currentPrice))
            return false;
        if (averagePrice == null) {
            if (other.averagePrice != null)
                return false;
        } else if (!averagePrice.equals(other.averagePrice))
            return false;
        return true;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Stock{");
        sb.append("id=").append(id);
        sb.append(", code=").append(code);
        sb.append(", currentPrice=").append(currentPrice);
        sb.append(", averagePrice=").append(averagePrice);
        sb.append('}');
        return sb.toString();
    }

    public static Stock to(StockResult stockResult) {
       Stock stock = new Stock();
       stock.setCode(stockResult.getCode());
       stock.setCurrentPrice(stockResult.getCurrentPrice());
       stock.setAveragePrice(stockResult.getAveragePrice());

       return stock;
    }
}
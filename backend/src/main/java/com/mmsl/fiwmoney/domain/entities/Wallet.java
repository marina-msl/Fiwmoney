package com.mmsl.fiwmoney.domain.entities;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Wallet {


    private Long id;
    private List<Stock> stocks;

    public Stock addStock(Stock stock) {
        this.stocks.add(stock);
        return stock;
    }

    public void removeStock(Stock stock) {
        this.stocks.remove(stock);
    }

    public void replace(List<Stock> stocks) {
        this.stocks = stocks;
    }

    public List <Stock> getStocks() {
        return new ArrayList<>(this.stocks);
    }
}

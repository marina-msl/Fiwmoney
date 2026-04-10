package com.mmsl.fiwmoney.domain.entities;

import java.util.ArrayList;
import java.util.List;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Wallet {


    private Long id;
    private List<Stock> stocks = new ArrayList<>();

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

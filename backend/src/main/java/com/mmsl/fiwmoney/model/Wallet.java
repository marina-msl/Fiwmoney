package com.mmsl.fiwmoney.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Wallet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private List<Stock> stocks;

    public Wallet() {
        this.stocks = new ArrayList<>();
    }

    public Wallet(Long id, List<Stock> stocks) {
        this.id = id;
        this.stocks = stocks;
    }

    public void addStock(Stock stock) {
        this.stocks.add(stock);
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

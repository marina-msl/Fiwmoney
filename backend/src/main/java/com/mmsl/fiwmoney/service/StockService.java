package com.mmsl.fiwmoney.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mmsl.fiwmoney.model.Stock;
import com.mmsl.fiwmoney.repository.StockRepository;

@Service
public class StockService {

    @Autowired
    private StockRepository repository;

    public List<Stock> findAll() {
        return repository.findAll();
    }

    public Stock findByCode(String code) {
        return repository.findByCode(code);
    }

    public void save(String code) {
        
        // Stock stock = new Stock(code, currentAveragePrice, averagePrice);
        // this.repository.save(stock);
    }
}

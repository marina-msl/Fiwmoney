package com.mmsl.fiwmoney.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.mmsl.fiwmoney.model.Stock;
import com.mmsl.fiwmoney.repository.StockRepository;

@Service
public class StockService {

    private final StockRepository repository;

    private final RestTemplate restTemplate = new RestTemplate();

    public StockService(StockRepository repository) {
        this.repository = repository;
    }

    public List<Stock> findAll() {
        return repository.findAll();
    }

  //  public Stock findByCode(String code) {
  //      return repository.findByCode(code);
  //  }

    public void save(String code) {
        
        // Stock stock = new Stock(code, currentAveragePrice, averagePrice);
        // this.repository.save(stock);
    }

    public double fetchCurrentPrice(String stockCode) {
        String url = "http://localhost:8090/stocks/" + stockCode;
        Stock response = restTemplate.getForObject(url, Stock.class);
        return response != null ? response.getCurrentPrice() : 0.0;

    }
}

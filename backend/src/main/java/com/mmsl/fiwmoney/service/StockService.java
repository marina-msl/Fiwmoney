package com.mmsl.fiwmoney.service;

import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.mmsl.fiwmoney.dto.StockResult;
import com.mmsl.fiwmoney.dto.StockResultMin;
import com.mmsl.fiwmoney.model.Stock;
import com.mmsl.fiwmoney.repository.StockRepository;

import jakarta.transaction.Transactional;

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

    @Scheduled(fixedRate=3600000)
    public void updateStockPrices() {
        List<Stock> stocks = repository.findAll();

        for (Stock stock : stocks) {
            double currentPrice = fetchCurrentPrice(stock.getCode());

            if (currentPrice != -1) {
                stock.setCurrentPrice(currentPrice);
                repository.save(stock);
                System.out.println("Update price for: " + stock.getCode());
            } else {
                System.out.println("Stock not found: " + stock.getCode());
            }
        }
    }

    public double fetchCurrentPrice(String stockCode) {
        String url = "http://localhost:8090/stocks/" + stockCode;

        try {
            StockResultMin response = restTemplate.getForObject(url, StockResultMin.class);
            return response != null ? response.getPrice() : 0.0;
        } catch (HttpClientErrorException.NotFound e) {
            return -1.0;
        }    
    }

    public void save(StockResult stockResult) {
        repository.save(Stock.to(stockResult));
    }

    @Transactional
    public void updateNotify(String code, boolean notify) {
        repository.updateNotify(code, notify);
    }
}

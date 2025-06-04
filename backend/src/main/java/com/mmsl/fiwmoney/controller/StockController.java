package com.mmsl.fiwmoney.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mmsl.fiwmoney.dto.StockRequest;
import com.mmsl.fiwmoney.dto.StockResult;
import com.mmsl.fiwmoney.model.Stock;
import com.mmsl.fiwmoney.service.StockService;

@RestController
@RequestMapping(value = "/")
public class StockController {
    
    @Autowired
    private StockService service;

    @CrossOrigin(origins = "http://localhost:5173")
    @PostMapping(value = "/stock")
    public ResponseEntity<StockResult> getStock(@RequestBody StockRequest stock) {
        if (stock.getCode() == null) {
            return ResponseEntity.badRequest().build();
        }

        String code = stock.getCode();
        double averagePrice = stock.getAveragePrice();
        double currentPrice = service.fetchCurrentPrice(code);

        if (currentPrice == -1) {
            return ResponseEntity.notFound().build();
        }

        StockResult stockResult = new StockResult(code, currentPrice, averagePrice);
        service.save(stockResult);

       return ResponseEntity.ok()
       .header("X-Custom-Info", "Stock Data Response")
       .body(stockResult);
    }

    @GetMapping(value = "/stocks")
    public ResponseEntity<List<Stock>> getAll() {
        List<Stock> stocks = service.findAll();

        return stocks.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(stocks);
    }
}

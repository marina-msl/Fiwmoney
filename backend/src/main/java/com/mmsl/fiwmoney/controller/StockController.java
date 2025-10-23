package com.mmsl.fiwmoney.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
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
    
    private static final int NOT_FOUND = -1;
    
    @Autowired
    private StockService service;

    @PostMapping(value = "/stocks")
    public ResponseEntity<StockResult> getStock(@RequestBody StockRequest stock) {
        if (stock.getCode() == null) {
            return ResponseEntity.badRequest().build();
        }

        String code = stock.getCode();
        double averagePrice = stock.getAveragePrice();
        boolean notify = stock.getNotify();
        double currentPrice = service.fetchCurrentPrice(code);

        if (currentPrice == NOT_FOUND) {
            return ResponseEntity.notFound().build();
        }

        StockResult stockResult = new StockResult(code, currentPrice, averagePrice, notify);
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

    @PatchMapping("/notify")
    public ResponseEntity<String> isNotify(@RequestBody StockRequest stock) {
        service.updateNotify(stock.getCode(), stock.getNotify());

        return ResponseEntity.ok("Notify status updated for: " + stock.getCode());
    }
}

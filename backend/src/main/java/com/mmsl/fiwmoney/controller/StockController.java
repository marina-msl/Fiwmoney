package com.mmsl.fiwmoney.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mmsl.fiwmoney.dto.StockRequest;
import com.mmsl.fiwmoney.dto.StockResult;
import com.mmsl.fiwmoney.service.StockService;

@RestController
@RequestMapping(value = "stock")
public class StockController {
    
    @Autowired
    private StockService service;

    // @CrossOrigin(origins = "http://localhost:9000")
    @PostMapping(value = "/all")
    public ResponseEntity<StockResult> getStock(@RequestBody StockRequest stock) {
        if (stock.getCode() == null) {
            return ResponseEntity.badRequest().build();
        }

        String code = stock.getCode();
        double averagePrice = stock.getAveragePrice();
        double currentPrice = service.fetchCurrentPrice(code);

        StockResult stockResult = new StockResult(code, currentPrice, averagePrice);

       return ResponseEntity.ok()
       .header("X-Custom-Info", "Stock Data Response")
       .body(stockResult);
    }

    @GetMapping("/test")
    public String testEndpoint() {
        return "StockController is working!";
    }
}

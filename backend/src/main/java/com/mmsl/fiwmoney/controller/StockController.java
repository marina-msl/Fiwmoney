package com.mmsl.fiwmoney.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mmsl.fiwmoney.model.Stock;
import com.mmsl.fiwmoney.service.StockService;

@RestController
@RequestMapping("/stocks")
public class StockController {
    
    @Autowired
    private StockService service;

    @GetMapping("/stock")
    public List<Stock> findAll() {
        return service.findAll();
    }

}

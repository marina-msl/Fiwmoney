package com.mmsl.fiwmoney.event;

import com.mmsl.fiwmoney.model.Stock;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class StockPriceUpdatedEvent {
    
    private final Stock stock;
    
}

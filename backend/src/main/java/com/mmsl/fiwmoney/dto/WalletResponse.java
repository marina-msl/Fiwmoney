package com.mmsl.fiwmoney.dto;

import java.util.List;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class WalletResponse {

    private Long id;
    private List<StockResponse> stocks;
    
}

package com.mmsl.fiwmoney.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class WalletDTO {
    
    private Long id;
    private List<StockDTO> stocks;


    

}

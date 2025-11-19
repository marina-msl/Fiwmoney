package com.mmsl.fiwmoney.model;

import java.util.ArrayList;
import java.util.List;

import com.mmsl.fiwmoney.dto.StockDTO;
import com.mmsl.fiwmoney.dto.WalletDTO;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Wallet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private List<Stock> stocks;

    public Wallet() {
        this.stocks = new ArrayList<>();
    }

    public Wallet(Long id, List<Stock> stocks) {
        this.id = id;
        this.stocks = stocks;
    }

    public void addStock(Stock stock) {
        this.stocks.add(stock);
    }

    public void removeStock(Stock stock) {
        this.stocks.remove(stock);
    }

    public void replace(List<Stock> stocks) {
        this.stocks = stocks;
    }

    public List <Stock> getStocks() {
        return new ArrayList<>(this.stocks);
    }
    
    public WalletDTO toDTO(Wallet wallet) {
        
        this.stocks = wallet.getStocks();
        
        List<StockDTO> stockDtos = new ArrayList<>();

        for (Stock stock : stocks) {
        
            StockDTO dto = new StockDTO(
                stock.getCode(),
                stock.getCurrentPrice(),
                stock.getAveragePrice(),
                stock.isNotify()
            );

            stockDtos.add(dto);
        }    
        return new WalletDTO(wallet.id, stockDtos);

    }


}

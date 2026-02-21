package com.mmsl.fiwmoney.model;

import java.util.ArrayList;
import java.util.List;

import com.mmsl.fiwmoney.dto.StockDTO;
import com.mmsl.fiwmoney.dto.WalletDTO;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Wallet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "wallet_id")
    private List<Stock> stocks;

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
    
    public WalletDTO toDTO() {
        List<StockDTO> stockDtos = new ArrayList<>();

        for (Stock stock : this.stocks) {
            StockDTO dto = new StockDTO(
                stock.getId(),
                stock.getCode(),
                stock.getCurrentPrice(),
                stock.getAveragePrice(),
                stock.isNotify()
        );
        stockDtos.add(dto);
    }

        return new WalletDTO(this.id, stockDtos);
    }


}

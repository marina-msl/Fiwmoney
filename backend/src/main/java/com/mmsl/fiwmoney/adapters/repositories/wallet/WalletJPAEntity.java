package com.mmsl.fiwmoney.adapters.repositories.wallet;

import java.util.ArrayList;
import java.util.List;

import com.mmsl.fiwmoney.adapters.repositories.stock.StockJPAEntity;
import com.mmsl.fiwmoney.domain.entities.Stock;
import com.mmsl.fiwmoney.domain.entities.Wallet;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "wallet")
public class WalletJPAEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "wallet_id")
    private List<StockJPAEntity> stocks = new ArrayList<>();

    public StockJPAEntity addStock(StockJPAEntity stock) {
        this.stocks.add(stock);
        return stock;
    }

    public void removeStock(StockJPAEntity stock) {
        this.stocks.remove(stock);
    }

    public void replace(List<StockJPAEntity> stocks) {
        this.stocks = stocks;
    }

    public List <StockJPAEntity> getStocks() {
        return new ArrayList<>(this.stocks);
    }
 
    private static List<StockJPAEntity> toStockMappers(List<Stock> stocks) {
        return stocks.stream()
                .map(stock -> new StockJPAEntity(stock.getId(), stock.getCode(), stock.getCurrentPrice(), stock.getAveragePrice(), stock.isNotify()))
                .toList();
    }

    private List<Stock> toStockEntity(List<StockJPAEntity> stockMappers) {
        return stockMappers.stream()
                .map(stockerMapper -> new Stock(stockerMapper.getId(), stockerMapper.getCode(), stockerMapper.getCurrentPrice(), stockerMapper.getAveragePrice(), stockerMapper.isNotify()))
                .toList();
    }

    public static WalletJPAEntity fromEntity(Wallet wallet) {
        return new WalletJPAEntity(wallet.getId(), toStockMappers(wallet.getStocks()));
    }

    public Wallet toEntity(WalletJPAEntity walletMapper) {
        return new Wallet(walletMapper.getId(), toStockEntity(walletMapper.getStocks()));  
    }
}

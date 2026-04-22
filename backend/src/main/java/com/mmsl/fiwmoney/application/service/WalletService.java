package com.mmsl.fiwmoney.application.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.mmsl.fiwmoney.domain.entities.Stock;
import com.mmsl.fiwmoney.domain.entities.Wallet;
import com.mmsl.fiwmoney.domain.exception.WalletNotFoundException;
import com.mmsl.fiwmoney.domain.ports.Fetch;
import com.mmsl.fiwmoney.domain.ports.WalletRepository;
import com.mmsl.fiwmoney.dto.StockRequest;
import com.mmsl.fiwmoney.dto.StockResponse;
import com.mmsl.fiwmoney.dto.WalletResponse;

import jakarta.transaction.Transactional;



@Service
public class WalletService {

    private static final Logger log = LoggerFactory.getLogger(WalletService.class);
    private static final int ONE_HOUR = 3600000;
   
    private final WalletRepository walletRepository;
    
    private final Fetch fetch;

    public WalletService(WalletRepository walletRepository, Fetch fetch) {
        this.walletRepository = walletRepository;
        this.fetch = fetch;
    }

    private boolean averagePriceIsHigher(Stock stock) {
        return stock.getAveragePrice().compareTo(stock.getCurrentPrice()) > 0;
    }

    @Transactional
    @Scheduled(fixedRate=ONE_HOUR)
    // @Scheduled(fixedRate=5000)
    public void updateStockPrices() {
//        List<Stock> stocks = walletRepository.findAll();
        List<Wallet> wallets = walletRepository.findAll();

        //TODO MELHORAR ESSA PARTE QUE ESTA O(n)^2
        for (Wallet wallet : wallets) {
            for (Stock stock : wallet.getStocks()) {
                BigDecimal currentPrice = this.fetch.getStockPrice(stock.getCode());

                if (currentPrice.compareTo(BigDecimal.valueOf(-1)) != 0) {
                    stock.setCurrentPrice(currentPrice);
                    log.info("Updating price for: " + stock.getCode());
                } else {
                    log.info("Stock not found: " + stock.getCode());
                }
            }
            walletRepository.save(wallet);
        }
    }

    //@Scheduled(fixedRate=5000)
    public void notifySendMessage(Long walletId) {
        Optional<Wallet> wallet = walletRepository.findById(walletId);
        Wallet walletOrg = wallet.orElse(null);

        assert walletOrg != null;
        List<Stock> stocks = walletOrg.getStocks();

        for (Stock stock : stocks) {
            if (averagePriceIsHigher(stock)) {

                log.info("Stock price updated for: " + stock.getCode());
            }
        }
    }

    public StockResponse addStockToWallet(Long walletId, StockRequest request) {
        Wallet wallet = walletRepository.findById(walletId)
                .orElseThrow(() -> new WalletNotFoundException(walletId));

        BigDecimal currentPrice = this.fetch.getStockPrice(request.code());

        Stock stock = request.toEntity(currentPrice);

        wallet.addStock(stock);
        walletRepository.save(wallet);
        
        StockResponse stockResponse = StockResponse.fromEntity(stock);

        return stockResponse;
    }

    @Transactional
    public void updateNotify(Long walletId, String code, boolean notify) {
        Wallet wallet = walletRepository.findById(walletId)
                .orElseThrow(()-> new WalletNotFoundException(walletId));

        wallet.getStocks().stream()
                        .filter(stock -> stock.getCode().equals(code))
                        .findFirst()
                        .ifPresent(stock -> stock.setNotify(notify));

        walletRepository.save(wallet);
    }

    public Optional<WalletResponse> getWalletById(Long id) {

          return walletRepository.findById(id)
            .map(wallet -> new WalletResponse(
                wallet.getId(),
                wallet.getStocks().stream()
                    .map(StockResponse::fromEntity)
                    .toList())); 
    } 

    public void removeStockFromWallet(Long walletId, String code) {
        Wallet wallet = walletRepository.findById(walletId)
                .orElseThrow(() -> new WalletNotFoundException(walletId));

        wallet.getStocks().stream()
                        .filter(stock -> stock.getCode().equals(code))
                        .findFirst()
                        .ifPresent(stock -> wallet.removeStock(stock));


        walletRepository.save(wallet);
    }
}

package com.mmsl.fiwmoney.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.mmsl.fiwmoney.dto.StockDTO;
import com.mmsl.fiwmoney.dto.StockRequest;
import com.mmsl.fiwmoney.dto.StockResultMin;
import com.mmsl.fiwmoney.event.StockPriceUpdatedEvent;
import com.mmsl.fiwmoney.exception.WalletNotFoundException;
import com.mmsl.fiwmoney.model.Stock;
import com.mmsl.fiwmoney.model.Wallet;
import com.mmsl.fiwmoney.repository.WalletRepository;

import jakarta.transaction.Transactional;


@Service
public class WalletService {

    @Autowired
    private WalletRepository walletRepository;

    private static final Logger log = LoggerFactory.getLogger(WalletService.class);
    private static final int ONE_HOUR = 3600000;
    
    @Value("${stock.api.url}")
    private String stockSearchUrl;

    private final RestTemplate restTemplate = new RestTemplate();
   
    @Autowired
    private ApplicationEventPublisher eventPublisher;

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
                BigDecimal currentPrice = fetchCurrentPrice(stock.getCode());

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
    public void sendMessage(Long walletId) {
        Optional<Wallet> wallet = walletRepository.findById(walletId);
        Wallet walletOrg = wallet.orElse(null);

        assert walletOrg != null;
        List<Stock> stocks = walletOrg.getStocks();

        for (Stock stock : stocks) {
            if (averagePriceIsHigher(stock)) {
                eventPublisher.publishEvent(new StockPriceUpdatedEvent(stock));
            }
        }
    }

    private BigDecimal fetchCurrentPrice(String stockCode) {
        String url = stockSearchUrl + stockCode;

        try {
            StockResultMin response = restTemplate.getForObject(url, StockResultMin.class);
            return response != null ? response.getPrice() : BigDecimal.ZERO;
        } catch (HttpClientErrorException.NotFound e) {
            return BigDecimal.valueOf(-1);
        }
    }

    public StockDTO addStockToWallet(Long walletId, StockRequest request) {
        Wallet wallet = walletRepository.findById(walletId)
                .orElseThrow(() -> new WalletNotFoundException(walletId));

        BigDecimal currentPrice = fetchCurrentPrice(request.getCode());

        Stock stock = new Stock();
        stock.setCode(request.getCode());
        stock.setCurrentPrice(currentPrice);
        stock.setAveragePrice(request.getAveragePrice());
        stock.setNotify(request.isNotify());


        wallet.addStock(stock);
        walletRepository.save(wallet);


        return new StockDTO(stock.getId(), stock.getCode(), stock.getCurrentPrice(), stock.getAveragePrice(), stock.isNotify());

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

    public Optional<Wallet> getWalletById(Long id) {
        return walletRepository.findById(id);
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

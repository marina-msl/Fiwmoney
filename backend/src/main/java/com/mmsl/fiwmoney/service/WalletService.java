package com.mmsl.fiwmoney.service;

import java.util.Optional;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.mmsl.fiwmoney.model.Stock;
import com.mmsl.fiwmoney.model.Wallet;
import com.mmsl.fiwmoney.repository.WalletRepository;


@Service
public class WalletService {

    @Autowired
    private WalletRepository walletRepository;

    private static final Logger log = LoggerFactory.getLogger(StockService.class);
    private static final int ONE_HOUR = 3600000;
    private static final String STOCK_SEARCH_URL = "http://localhost:8090/stocks/";

    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    private SimpleMailMessage templateMessage;

    private final RestTemplate restTemplate = new RestTemplate();
    
    private void sendMessage(Stock stock) {
        try {
            SimpleMailMessage msg = new SimpleMailMessage(templateMessage);
            msg.setTo("marina.msleide@gmail.com");
            msg.setText("Stocks with lower prices: " + stock.getCode());
            this.mailSender.send(msg);
            log.info("Email was sent!");
        } catch (MailException ex) {
            log.error("Error: " + ex);
        }
    }

    // @Scheduled(fixedRate=5000)
    public void sendMessage() {
        List<Stock> stocks = repository.findAll();

        for (Stock stock : stocks) {
            if (stock.getAveragePrice() > stock.getCurrentPrice()) {
                sendMessage(stock);
            }
        }
    }

    @Scheduled(fixedRate=ONE_HOUR)
    // @Scheduled(fixedRate=5000)
    public void updateStockPrices() {
        List<Stock> stocks = repository.findAll();

        for (Stock stock : stocks) {
            double currentPrice = fetchCurrentPrice(stock.getCode());

            if (currentPrice != -1) {
                stock.setCurrentPrice(currentPrice);
                repository.save(stock);
                log.info("Updating price for : " + stock.getCode());
            } else {
                log.info("Stock not found: " + stock.getCode());
            }
        }
    }

    private double fetchCurrentPrice(String stockCode) {
        String url = STOCK_SEARCH_URL + stockCode;

        try {
            StockResultMin response = restTemplate.getForObject(url, StockResultMin.class);
            return response != null ? response.getPrice() : 0.0;
        } catch (HttpClientErrorException.NotFound e) {
            return -1.0;
        }    
    }

    public void addStockToWallet(Long walletId, StockRequest stock) {
        BigDecimal currentPrice = fetchCurrentPrice(stock.getCode());
        repository.addStockToWallet(walletId, stock, currentPrice);
    }

    @Transactional
    public void updateNotify(Long walletId, String code, boolean notify) {
        repository.updateNotify(walletId, code, notify);
    }

    public Optional<Wallet> getWalletById(Long id) {
        return walletRepository.findById(id);
    } 
    
}

package com.mmsl.fiwmoney.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.mmsl.fiwmoney.dto.StockResult;
import com.mmsl.fiwmoney.dto.StockResultMin;
import com.mmsl.fiwmoney.model.Stock;
import com.mmsl.fiwmoney.repository.StockRepository;

import jakarta.transaction.Transactional;

@Service
public class StockService {

    private static final Logger log = LoggerFactory.getLogger(StockService.class);
    private static final int ONE_HOUR = 3600000;
    private final StockRepository repository;

    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    private SimpleMailMessage templateMessage;

    private final RestTemplate restTemplate = new RestTemplate();
    
    public void sendSimpleMessge(Stock stock) {
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

    public StockService(StockRepository repository) {
        this.repository = repository;
    }

    public List<Stock> findAll() {
        return repository.findAll();
    }

    @Scheduled(fixedRate=5000)
    public void sendMessage() {
        List<Stock> stocks = repository.findAll();

        for (Stock stock : stocks) {
            if (stock.getAveragePrice() > stock.getCurrentPrice()) {
                sendSimpleMessge(stock);
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

    public double fetchCurrentPrice(String stockCode) {
        String url = "http://localhost:8090/stocks/" + stockCode;

        try {
            StockResultMin response = restTemplate.getForObject(url, StockResultMin.class);
            return response != null ? response.getPrice() : 0.0;
        } catch (HttpClientErrorException.NotFound e) {
            return -1.0;
        }    
    }

    public void save(StockResult stockResult) {
        repository.save(Stock.to(stockResult));
    }

    @Transactional
    public void updateNotify(String code, boolean notify) {
        repository.updateNotify(code, notify);
    }
}

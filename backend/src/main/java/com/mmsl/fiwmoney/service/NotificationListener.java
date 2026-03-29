package com.mmsl.fiwmoney.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import com.mmsl.fiwmoney.event.StockPriceUpdatedEvent;
import com.mmsl.fiwmoney.model.Stock;


@Component
public class NotificationListener {

    private static final Logger log = LoggerFactory.getLogger(WalletService.class);
    
    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    private SimpleMailMessage templateMessage;

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

    @EventListener
    public void onStockPriceUpdated(StockPriceUpdatedEvent event) {
        Stock stock = event.getStock();
        if (stock.isNotify() && stock.getAveragePrice().compareTo(stock.getCurrentPrice()) > 0) {
            sendMessage(stock);
        }
    }
      
}

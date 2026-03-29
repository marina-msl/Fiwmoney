package com.mmsl.fiwmoney.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import com.mmsl.fiwmoney.model.Stock;
import com.mmsl.fiwmoney.model.Wallet;


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

    // @Scheduled(fixedRate=5000)
    public void sendMessage(Long walletId) {
        Optional<Wallet> wallet = walletRepository.findById(walletId);
        Wallet walletOrg = wallet.orElse(null);

        assert walletOrg != null;
        List<Stock> stocks = walletOrg.getStocks();

        for (Stock stock : stocks) {
            if (averagePriceIsHigher(stock)) {
                sendMessage(stock);
            }
        }
    }
    
}

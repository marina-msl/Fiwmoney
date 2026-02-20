package com.mmsl.fiwmoney.controller;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mmsl.fiwmoney.dto.StockDTO;
import com.mmsl.fiwmoney.dto.StockRequest;
import com.mmsl.fiwmoney.dto.WalletDTO;
import com.mmsl.fiwmoney.model.Wallet;
import com.mmsl.fiwmoney.service.WalletService;

@RestController
@RequestMapping(value = "/")
public class WalletController {
    
    private static final int NOT_FOUND = -1;
    
    @Autowired
    private Wallet service;

    @Autowired
    private WalletService walletService;


    @PostMapping(value = "/wallet/{id}/stock")
    public ResponseEntity<StockDTO> getStock(@PathVariable("id") Long walletId, 
                                            @RequestBody StockRequest stock) {

        if (stock.getCode() == null) {
            return ResponseEntity.badRequest().build();
        }

       walletService.addStockToWallet(walletId, stock);

       return ResponseEntity.ok()
            .header("X-Custom-Info", "Stock Data Response")
            .body(stockDTO);
    }

    @GetMapping(value = "/wallet/{id}")
    public ResponseEntity<WalletDTO> getStock(@PathVariable ("id") Long id) {

        return walletService.getWalletById(id)
                .map(wallet -> ResponseEntity.ok(wallet.toDTO()))
                .orElse(ResponseEntity.notFound().build());
    }

    @PatchMapping("/wallet/{id}/notify")
    public ResponseEntity<String> isNotify(@PathVariable ("id") Long walletId,
                                           @RequestBody StockRequest stock) {
        walletService.updateNotify(walletId, stock.getCode(), stock.getNotify());

        return ResponseEntity.ok("Notify status updated for: " + stock.getCode());
    }
}

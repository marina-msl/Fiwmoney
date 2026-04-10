package com.mmsl.fiwmoney.adapters.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mmsl.fiwmoney.dto.StockResponse;
import com.mmsl.fiwmoney.dto.StockRequest;
import com.mmsl.fiwmoney.dto.WalletDTO;
import com.mmsl.fiwmoney.service.WalletService;

@RestController
@RequestMapping(value = "/")
public class WalletController {

    @Autowired
    private WalletService walletService;

    @PostMapping(value = "/wallet/{walletId}/stock")
    public ResponseEntity<StockResponse> addStockToWallet(@PathVariable Long walletId,
                                                     @RequestBody StockRequest stock) {

        if (stock.code() == null) {
            return ResponseEntity.badRequest().build();
        }

       StockResponse stockDTO = this.walletService.addStockToWallet(walletId, stock);

       return ResponseEntity.ok()
            .header("X-Custom-Info", "Stock Data Response")
            .body(stockDTO);
    }

    @GetMapping(value = "/wallet/{id}")
    public ResponseEntity<WalletDTO> getWallet(@PathVariable Long id) {

        return this.walletService.getWalletById(id)
                .map(wallet -> ResponseEntity.ok(wallet.toDTO()))
                .orElse(ResponseEntity.notFound().build());
    }

    @PatchMapping("/wallet/{walletId}/stock/{code}/notify")
    public ResponseEntity<String> updateNotify(@PathVariable Long walletId,
                                           @PathVariable String code,
                                           @RequestBody StockRequest stock) {
        this.walletService.updateNotify(walletId, code, stock.notifyEnabled());

        return ResponseEntity.ok("Notify status updated for: " + code);
    }

    @DeleteMapping("/wallet/{id}/stock/{code}")
    public ResponseEntity<String> removeStock(@PathVariable ("id") Long walletId,
                                             @PathVariable ("code") String code) {
        this.walletService.removeStockFromWallet(walletId, code);
        return ResponseEntity.ok("Stock removed from wallet");
    }
}
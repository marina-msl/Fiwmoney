package com.mmsl.fiwmoney.adapters.in.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mmsl.fiwmoney.adapters.in.security.SecurityUtils;
import com.mmsl.fiwmoney.application.service.WalletOwnershipService;
import com.mmsl.fiwmoney.application.service.WalletService;
import com.mmsl.fiwmoney.dto.NotifyStockRequest;
import com.mmsl.fiwmoney.dto.StockRequest;
import com.mmsl.fiwmoney.dto.StockResponse;
import com.mmsl.fiwmoney.dto.WalletResponse;

@RestController
@RequestMapping(value = "/api/v1")
public class WalletController {

    private final WalletService walletService;
    private final WalletOwnershipService walletByUsernameService;


    public WalletController(WalletService walletService, WalletOwnershipService walletByUsernameService) {
        this.walletService = walletService;
        this.walletByUsernameService = walletByUsernameService;
    }

    @PostMapping(value = "/wallet/stock")
    public ResponseEntity<StockResponse> addStockToWallet(@RequestBody StockRequest stock) {

        if (stock.code() == null) {
            return ResponseEntity.badRequest().build();
        }

        long walletId = walletByUsernameService.getWalletByUser(SecurityUtils.getUsername());

        StockResponse stockDTO = this.walletService.addStockToWallet(walletId, stock);

       return ResponseEntity.ok()
            .header("X-Custom-Info", "Stock Data Response")
            .body(stockDTO);
    }

    @GetMapping(value = "/wallet")
    public ResponseEntity<WalletResponse> getWallet() {

        long walletId = walletByUsernameService.getWalletByUser(SecurityUtils.getUsername());

        return this.walletService.getWalletById(walletId)
                .map(walletResponse -> ResponseEntity.ok(walletResponse))
                .orElse(ResponseEntity.notFound().build());
    }

    @PatchMapping("/wallet/stock/{code}/notify")
    public ResponseEntity<String> updateNotify(@PathVariable String code,
                                                @RequestBody NotifyStockRequest request) {

        long walletId = walletByUsernameService.getWalletByUser(SecurityUtils.getUsername());

        this.walletService.updateNotify(walletId, code, request.notifyEnabled());

        return ResponseEntity.ok("Notify status updated for: " + code);
    }

    @DeleteMapping("/wallet/stock/{code}")
    public ResponseEntity<String> removeStock(@PathVariable String code) {

        long walletId = walletByUsernameService.getWalletByUser(SecurityUtils.getUsername());
        
        this.walletService.removeStockFromWallet(walletId, code);

        return ResponseEntity.ok("Stock removed from wallet");
    }

}
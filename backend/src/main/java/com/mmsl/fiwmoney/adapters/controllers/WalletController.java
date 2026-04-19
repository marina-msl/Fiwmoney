package com.mmsl.fiwmoney.adapters.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mmsl.fiwmoney.application.service.UserService;
import com.mmsl.fiwmoney.application.service.WalletOwnershipService;
import com.mmsl.fiwmoney.application.service.WalletService;
import com.mmsl.fiwmoney.dto.StockRequest;
import com.mmsl.fiwmoney.dto.StockResponse;
import com.mmsl.fiwmoney.dto.WalletResponse;

import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping(value = "/")
public class WalletController {

    private final WalletService walletService;
    private final UserService userService;
    private final WalletOwnershipService walletByUsernameService;


    public WalletController(WalletService walletService, UserService userService,
                            WalletOwnershipService walletByUsernameService) {
        this.walletService = walletService;
        this.userService = userService;
        this.walletByUsernameService = walletByUsernameService;
    }

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
    public ResponseEntity<WalletResponse> getWallet(@PathVariable Long id) {

        if(!isUserAllowed(id)) {
            return ResponseEntity.status(HttpServletResponse.SC_FORBIDDEN).build();
        }

        return this.walletService.getWalletById(id)
                .map(walletResponse -> ResponseEntity.ok(walletResponse))
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

    private Boolean isUserAllowed(long walletId) {

        boolean result = true;

        String username = SecurityContextHolder.getContext().getAuthentication().getName();

         if (!userService.findByUsername(username).isPresent()) {
             result = false;
        }

        long walletIdFound = walletByUsernameService.getWalletByUser(username);

        if (walletId != walletIdFound) {
            result = false;
        }

        return result;
    }
}
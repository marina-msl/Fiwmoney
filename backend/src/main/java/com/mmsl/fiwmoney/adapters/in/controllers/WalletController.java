package com.mmsl.fiwmoney.adapters.in.controllers;

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
import com.mmsl.fiwmoney.dto.NotifyStockRequest;
import com.mmsl.fiwmoney.dto.StockRequest;
import com.mmsl.fiwmoney.dto.StockResponse;
import com.mmsl.fiwmoney.dto.WalletResponse;

import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping(value = "/api/v1")
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

    @PostMapping(value = "/wallet/stock")
    public ResponseEntity<StockResponse> addStockToWallet(@RequestBody StockRequest stock) {

         if(!isUserAllowed()) {
            return ResponseEntity.status(HttpServletResponse.SC_FORBIDDEN).build();
        }

        if (stock.code() == null) {
            return ResponseEntity.badRequest().build();
        }

       StockResponse stockDTO = this.walletService.addStockToWallet(getWalletId(), stock);

       return ResponseEntity.ok()
            .header("X-Custom-Info", "Stock Data Response")
            .body(stockDTO);
    }

    @GetMapping(value = "/wallet")
    public ResponseEntity<WalletResponse> getWallet() {

        if(!isUserAllowed()) {
            return ResponseEntity.status(HttpServletResponse.SC_FORBIDDEN).build();
        }

        long walletId = getWalletId();

        return this.walletService.getWalletById(walletId)
                .map(walletResponse -> ResponseEntity.ok(walletResponse))
                .orElse(ResponseEntity.notFound().build());
    }

    @PatchMapping("/wallet/stock/{code}/notify")
    public ResponseEntity<String> updateNotify(@PathVariable String code,
                                                @RequestBody NotifyStockRequest request) {

        if(!isUserAllowed()) {
            return ResponseEntity.status(HttpServletResponse.SC_FORBIDDEN).build();
        }

        long walletId = getWalletId();

        this.walletService.updateNotify(walletId, code, request.notifyEnabled());

        return ResponseEntity.ok("Notify status updated for: " + code);
    }

    @DeleteMapping("/wallet/stock/{code}")
    public ResponseEntity<String> removeStock(@PathVariable String code) {

        if(!isUserAllowed()) {
            return ResponseEntity.status(HttpServletResponse.SC_FORBIDDEN).build();
        }

        long walletId = getWalletId();
        
        this.walletService.removeStockFromWallet(walletId, code);

        return ResponseEntity.ok("Stock removed from wallet");
    }

     private Boolean isUserAllowed() {

        boolean result = false;

        String username = SecurityContextHolder.getContext().getAuthentication().getName();

         if (userService.findByUsername(username).isPresent()) {
             result = true;
        }

        return result;
    }

    private long getWalletId() {
        return walletByUsernameService.getWalletByUser(getUsername());
    }

    private String getUsername() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }
}
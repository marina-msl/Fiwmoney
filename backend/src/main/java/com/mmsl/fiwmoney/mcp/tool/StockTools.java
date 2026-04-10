package com.mmsl.fiwmoney.mcp.tool;

import java.util.Optional;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Service;

import com.mmsl.fiwmoney.application.service.WalletService;
import com.mmsl.fiwmoney.dto.WalletResponse;

import jakarta.annotation.Nonnull;

@Service
public class StockTools {
    
    private final WalletService walletService;

    public StockTools(WalletService walletService) {
        this.walletService = walletService;
    }   

    @Tool(
        name="getWalletById", 
        description="Return wallet details including all stocks for given a wallet ID"
    )
    public Optional<WalletResponse> getWallet(@Nonnull Long id) {
            return walletService.getWalletById(id);
    }
}



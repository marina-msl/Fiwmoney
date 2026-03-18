package com.mmsl.fiwmoney.mcp.tool;

import java.util.List;

import javax.validation.constraints.NotNull;

import org.checkerframework.checker.index.qual.Positive;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Service;

import com.mmsl.fiwmoney.exception.WalletNotFoundException;
import com.mmsl.fiwmoney.model.Stock;
import com.mmsl.fiwmoney.model.Wallet;
import com.mmsl.fiwmoney.service.WalletService;

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
    public List<Stock> getWallet(@NotNull @Positive Long id) {
        return walletService.getWalletById(id)
            .map(Wallet::getStocks)
            .orElseThrow(() -> new WalletNotFoundException(id));
    }
}



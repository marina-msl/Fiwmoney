package com.mmsl.fiwmoney.adapters.in.mcp.tool;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.Optional;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Service;

import com.mmsl.fiwmoney.application.service.WalletService;
import com.mmsl.fiwmoney.dto.StockResponse;
import com.mmsl.fiwmoney.dto.WalletResponse;

import jakarta.annotation.Nonnull;

@Service
public class StockTools {
    
    private final WalletService walletService;

    public StockTools(WalletService walletService) {
        this.walletService = walletService;
    }   

    @Tool(description = "Given a wallet ID or a username, return the wallet details including all stocks." +
                        "If the wallet ID or username does not exist, return an appropriate error message.",
        name="getWalletById")
    public Optional<WalletResponse> getWallet(@Nonnull Long id) {
            return walletService.getWalletById(id);
    }


    @Tool(description = "Get the a wallet and return the stock with the lowest average price.")
    public BigDecimal getLowestAveragePriceStock(@Nonnull Long walletId) {
        Optional<WalletResponse> wallet = walletService.getWalletById(walletId);
        return wallet.map(WalletResponse::stocks)
                     .flatMap(stocks -> stocks.stream()
                                              .min(Comparator.comparing(StockResponse::averagePrice)))
                     .map(stockResponse -> stockResponse.averagePrice())
                     .orElseThrow(() -> new RuntimeException("No stocks found for wallet ID: " + walletId));
    }
}



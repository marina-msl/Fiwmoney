package com.mmsl.fiwmoney.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.mmsl.fiwmoney.application.service.WalletService;
import com.mmsl.fiwmoney.domain.entities.Stock;
import com.mmsl.fiwmoney.domain.entities.Wallet;
import com.mmsl.fiwmoney.domain.exception.WalletNotFoundException;
import com.mmsl.fiwmoney.domain.ports.IFetch;
import com.mmsl.fiwmoney.domain.ports.IWalletRepository;
import com.mmsl.fiwmoney.dto.StockRequest;
import com.mmsl.fiwmoney.dto.StockResponse;

@ExtendWith(MockitoExtension.class)
class WalletServiceTest {

    @Mock
    private IWalletRepository walletRepository;

    @Mock
    private IFetch fetch;

    @InjectMocks
    private WalletService walletService;

    @Test
    void addStockToWallet_HappyPath() {
        //ARRANGE
        Long walletId = 1L;
        StockRequest request = new StockRequest("AAPL", new BigDecimal("160.00"), true);

        List<Stock> stocks = new ArrayList<>();
        Wallet wallet = new Wallet(walletId, stocks);

        when(walletRepository.findById(walletId)).thenReturn(Optional.of(wallet));

        when(fetch.getStockPrice(request.code())).thenReturn(new BigDecimal("150.00"));

        //ACT
        StockResponse stockResponse = walletService.addStockToWallet(walletId, request);

        //ASSERT
        assertNotNull(stockResponse);
        assertEquals("AAPL", stockResponse.code());
        assertEquals(new BigDecimal("160.00"), stockResponse.averagePrice());
        assertEquals(new BigDecimal("150.00"), stockResponse.currentPrice());
        assertTrue(stockResponse.notifyEnabled());
        verify(walletRepository).save(any(Wallet.class));
    }

    @Test
    void addStockToWallet_WalletNotFound() {
        //ARRANGE
        Long walletId = 1L;
        StockRequest request = new StockRequest("AAPL", new BigDecimal("160.00"), true);

        when(walletRepository.findById(walletId)).thenReturn(Optional.empty());
        //ACT & ASSERT
        WalletNotFoundException exception = assertThrows(WalletNotFoundException.class, () -> walletService.addStockToWallet(walletId, request));
        assertEquals("Wallet not found with ID: " + walletId, exception.getMessage());
    }

 
    // TODO: test fetchCurrentPrice when API returns 404 (should return -1)
    // TODO: test fetchCurrentPrice when API returns null response (should return BigDecimal.ZERO)
}

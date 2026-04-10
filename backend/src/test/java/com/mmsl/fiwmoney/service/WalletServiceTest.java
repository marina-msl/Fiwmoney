package com.mmsl.fiwmoney.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Optional;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import com.mmsl.fiwmoney.application.service.WalletService;
import com.mmsl.fiwmoney.domain.entities.Wallet;
import com.mmsl.fiwmoney.domain.ports.IFetch;
import com.mmsl.fiwmoney.domain.ports.IWalletRepository;
import com.mmsl.fiwmoney.dto.StockResponse;
import com.mmsl.fiwmoney.dto.StockRequest;
import com.mmsl.fiwmoney.dto.StockResultMin;

@ExtendWith(MockitoExtension.class)
class WalletServiceTest {

    @Mock
    private IWalletRepository walletRepository;

    @Mock
    private IFetch fetch;

    @InjectMocks
    private WalletService walletService;

    @Test
    @Disabled("mocks ainda não configurados — ver TODOs dentro do método")
    void addStockToWallet_HappyPath() {
        //ARRANGE
        Long walletId = 1L;
        StockRequest request = new StockRequest("AAPL", new BigDecimal("160.00"), true);

        Wallet wallet = new Wallet();
        // wallet.setId(walletId);

        // TODO: test fetchCurrentPrice when API returns 404 (should return -1)
        // TODO: test fetchCurrentPrice when API returns null response (should return BigDecimal.ZERO)
        StockResultMin stockResultMin = new StockResultMin();
        stockResultMin.setPrice(new BigDecimal("150.00"));

        // when(walletRepository.findById(walletId)).thenReturn(Optional.of(wallet));
        // when(restTemplate.getForObject(anyString(), eq(StockResultMin.class))).thenReturn(stockResultMin);

        //ACT
        StockResponse result = walletService.addStockToWallet(walletId, request);

        //ASSERT
        assertNotNull(result);
        // assertEquals("AAPL", result.getCode());
        // assertEquals(new BigDecimal("150.00"), result.getCurrentPrice());
        // assertEquals(new BigDecimal("160.00"), result.getAveragePrice());
        // assertTrue(result.isNotify());
        // verify(walletRepository).save(any(Wallet.class));
    }
}

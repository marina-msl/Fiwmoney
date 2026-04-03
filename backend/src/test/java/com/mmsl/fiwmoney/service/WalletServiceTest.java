package com.mmsl.fiwmoney.service;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;

import com.mmsl.fiwmoney.dto.StockDTO;
import com.mmsl.fiwmoney.dto.StockRequest;
import com.mmsl.fiwmoney.dto.StockResultMin;
import com.mmsl.fiwmoney.model.Stock;
import com.mmsl.fiwmoney.model.Wallet;
import com.mmsl.fiwmoney.repository.WalletRepository;

@ExtendWith(MockitoExtension.class)
class WalletServiceTest {

    @Mock
    private WalletRepository walletRepository;

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private WalletService walletService;

    @Test
    void addStockToWallet_HappyPath() {
        //ARRANGE
        Long walletId = 1L;
        StockRequest request = new StockRequest("AAPL", new BigDecimal("160.00"), true);

        Wallet wallet = new Wallet();
        wallet.setId(walletId);

        StockResultMin stockResultMin = new StockResultMin();
        stockResultMin.setPrice(new BigDecimal("150.00"));

        when(walletRepository.findById(walletId)).thenReturn(Optional.of(wallet));
        when(restTemplate.getForObject(anyString(), eq(StockResultMin.class))).thenReturn(stockResultMin);

        //ACT
        StockDTO result = walletService.addStockToWallet(walletId, request);

        //ASSERT
        assertNotNull(result);
        assertEquals("AAPL", result.getCode());
        assertEquals(new BigDecimal("150.00"), result.getCurrentPrice());
        assertEquals(new BigDecimal("160.00"), result.getAveragePrice());
        assertTrue(result.isNotify());
        verify(walletRepository).save(any(Wallet.class));
    }
}

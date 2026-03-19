package com.mmsl.fiwmoney.service;

import com.mmsl.fiwmoney.exception.WalletNotFoundException;
import com.mmsl.fiwmoney.model.Stock;
import com.mmsl.fiwmoney.model.Wallet;
import com.mmsl.fiwmoney.repository.WalletRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class WalletServiceTest {

    @Mock
    private WalletRepository walletRepository;

    @Mock
    private JavaMailSender mailSender;

    @Mock
    private SimpleMailMessage templateMessage;

    @InjectMocks
    private WalletService walletService;

    private Wallet wallet;
    private Stock stock;

    @BeforeEach
    void setUp() {
        stock = new Stock();
        stock.setCode("AAPL");
        stock.setCurrentPrice(new BigDecimal("150.00"));
        stock.setAveragePrice(new BigDecimal("160.00"));
        stock.setNotify(true);

        wallet = new Wallet();
        wallet.setId(1L);
        wallet.setStocks(new ArrayList<>());
        wallet.addStock(stock);
    }

    @Test
    void getWalletById_whenExists_returnsWallet() {
        when(walletRepository.findById(1L)).thenReturn(Optional.of(wallet));

        Optional<Wallet> result = walletService.getWalletById(1L);

        assertTrue(result.isPresent());
        assertEquals(1L, result.get().getId());
    }

    @Test
    void getWalletById_whenNotExists_returnsEmpty() {
        when(walletRepository.findById(99L)).thenReturn(Optional.empty());

        Optional<Wallet> result = walletService.getWalletById(99L);

        assertFalse(result.isPresent());
    }

    @Test
    void updateNotify_whenWalletNotFound_throwsException() {
        when(walletRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(WalletNotFoundException.class,
                () -> walletService.updateNotify(99L, "AAPL", true));
    }

    @Test
    void updateNotify_updatesStockNotifyFlag() {
        when(walletRepository.findById(1L)).thenReturn(Optional.of(wallet));

        walletService.updateNotify(1L, "AAPL", false);

        assertFalse(stock.isNotify());
        verify(walletRepository).save(wallet);
    }

    @Test
    void removeStockFromWallet_whenWalletNotFound_throwsException() {
        when(walletRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(WalletNotFoundException.class,
                () -> walletService.removeStockFromWallet(99L, "AAPL"));
    }

    @Test
    void removeStockFromWallet_removesStockAndSaves() {
        when(walletRepository.findById(1L)).thenReturn(Optional.of(wallet));

        walletService.removeStockFromWallet(1L, "AAPL");

        assertTrue(wallet.getStocks().isEmpty());
        verify(walletRepository).save(wallet);
    }
}

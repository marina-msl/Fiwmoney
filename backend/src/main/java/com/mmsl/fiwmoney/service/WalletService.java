package com.mmsl.fiwmoney.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import com.mmsl.fiwmoney.model.Wallet;
import com.mmsl.fiwmoney.repository.WalletRepository;

public class WalletService {

    @Autowired
    private WalletRepository walletRepository;

    public Optional<Wallet> getWalletById(Long id) {
        return walletRepository.findById(id);
    } 
    
}

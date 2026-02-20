package com.mmsl.fiwmoney.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mmsl.fiwmoney.model.Wallet;
import com.mmsl.fiwmoney.repository.WalletRepository;


@Service
public class WalletService {

    @Autowired
    private WalletRepository walletRepository;

    public Optional<Wallet> getWalletById(Long id) {
        return walletRepository.findById(id);
    } 
    
}

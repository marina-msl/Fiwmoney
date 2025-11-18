package com.mmsl.fiwmoney.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mmsl.fiwmoney.model.Wallet;

public interface WalletRepository extends JpaRepository<Wallet, Long> {
    
}


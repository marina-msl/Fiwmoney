package com.mmsl.fiwmoney.adapters.repositories.wallet;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mmsl.fiwmoney.domain.entities.Wallet;

@Repository
public interface WalletJPARepository extends JpaRepository<Wallet, Long> {
    
}


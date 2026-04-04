package com.mmsl.fiwmoney.adapters.repositories.wallet;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Repository;

import com.mmsl.fiwmoney.domain.entities.Wallet;
import com.mmsl.fiwmoney.domain.ports.IWalletRepository;

@Repository
@ConditionalOnProperty(name = "wallet.repository", havingValue = "jpa")
public class WalletRepositoryImpl implements IWalletRepository {

    @Autowired
    private WalletJPARepository walletJPARepository;

    @Override
    public List<Wallet> findAll() {
        return walletJPARepository.findAll();
    }

    @Override
    public void save(Wallet wallet) {
        walletJPARepository.save(wallet);
    }

    @Override
    public Optional<Wallet> findById(Long id) {
        return walletJPARepository.findById(id);
    }

    
}

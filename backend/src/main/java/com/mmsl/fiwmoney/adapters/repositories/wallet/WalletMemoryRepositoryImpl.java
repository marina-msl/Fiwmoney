package com.mmsl.fiwmoney.adapters.repositories.wallet;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Repository;

import com.mmsl.fiwmoney.domain.entities.Wallet;
import com.mmsl.fiwmoney.domain.ports.WalletRepository;

@Repository
@ConditionalOnProperty(name = "wallet.repository", havingValue = "memory")
public class WalletMemoryRepositoryImpl implements WalletRepository {

    private final List<Wallet> wallets = new ArrayList<>();

    @Override
    public List<Wallet> findAll() {
        return new ArrayList<>(wallets);
    }

    @Override
    public void save(Wallet wallet) {
        wallets.add(wallet);
    }

    @Override
    public Optional<Wallet> findById(Long id) {
        return wallets.stream()
                .filter(w -> w.getId().equals(id))
                .findFirst();
    }
}

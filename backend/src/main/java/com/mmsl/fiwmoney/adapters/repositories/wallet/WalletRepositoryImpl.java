package com.mmsl.fiwmoney.adapters.repositories.wallet;

import java.util.List;
import java.util.Optional;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Repository;

import com.mmsl.fiwmoney.adapters.repositories.stock.StockJPAEntity;
import com.mmsl.fiwmoney.domain.entities.Wallet;
import com.mmsl.fiwmoney.domain.ports.WalletRepository;

@Repository
@ConditionalOnProperty(name = "wallet.repository", havingValue = "jpa")
public class WalletRepositoryImpl implements WalletRepository {

    private final WalletJPARepository walletJPARepository;

    public WalletRepositoryImpl(WalletJPARepository walletJPARepository) {
        this.walletJPARepository = walletJPARepository;
    }

    @Override
    public List<Wallet> findAll() {
        List<WalletJPAEntity> walletMappers = walletJPARepository.findAll();
        return walletMappers.stream()
                .map(walletMapper -> walletMapper.toEntity(walletMapper))
                .toList();
    }

    @Override
    public void save(Wallet wallet) {
        List<StockJPAEntity> stockMappers = wallet.getStocks().stream()
                .map(stock -> new StockJPAEntity(stock.getId(), stock.getCode(), stock.getCurrentPrice(), stock.getAveragePrice(), stock.isNotify()))
                .toList();
        WalletJPAEntity walletMapper = 
                    new WalletJPAEntity(wallet.getId(), stockMappers);
        walletJPARepository.save(walletMapper);
    }

    @Override
    public Optional<Wallet> findById(Long id) {
        return walletJPARepository.findById(id).map(walletMapper -> walletMapper.toEntity(walletMapper));
    }

}

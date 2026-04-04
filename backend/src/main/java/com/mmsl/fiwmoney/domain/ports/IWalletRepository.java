package com.mmsl.fiwmoney.domain.ports;

import java.util.List;
import java.util.Optional;

import com.mmsl.fiwmoney.domain.entities.Wallet;

public interface IWalletRepository {

   List<Wallet> findAll();

   void save(Wallet wallet);

   Optional<Wallet> findById(Long id);

}

package com.mmsl.fiwmoney.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.mmsl.fiwmoney.model.Wallet;

public interface WalletRepository extends JpaRepository<Wallet, Long> {
    
  @Modifying
  @Query("UPDATE Stock s SET s.notify = :notify WHERE s.walletId = :walletId  s.code = :code")
  void updateNotify(@Param("walletId") Long id, @Param("code") String code,
                    @Param("notify") boolean notify);


}


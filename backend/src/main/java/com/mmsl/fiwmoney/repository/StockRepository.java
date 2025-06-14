package com.mmsl.fiwmoney.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.mmsl.fiwmoney.model.Stock;

@Repository
public interface StockRepository extends JpaRepository<Stock, Long> {

  @Modifying
  @Query("UPDATE Stock s SET s.notify = :notify WHERE s.code = :code")
  void updateNotify(@Param("code") String code, @Param("notify") boolean notify);

}

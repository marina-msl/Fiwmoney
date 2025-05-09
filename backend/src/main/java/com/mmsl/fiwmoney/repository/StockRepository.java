package com.mmsl.fiwmoney.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mmsl.fiwmoney.model.Stock;

@Repository
public interface StockRepository extends JpaRepository<Stock, Long> {

    

   // List<Stock> findByName(String name);

   // Optional<Stock> findById(Long id);

  //  public Stock findByCode(String code);


}

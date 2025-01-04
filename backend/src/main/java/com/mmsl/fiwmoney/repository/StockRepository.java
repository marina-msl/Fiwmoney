package com.mmsl.fiwmoney.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mmsl.fiwmoney.model.Stock;

public interface StockRepository extends JpaRepository<Stock, Long> {

    List<Stock> findByName(String name);

    Optional<Stock> findById(Long id);

    public Stock findByCode(String code);


}

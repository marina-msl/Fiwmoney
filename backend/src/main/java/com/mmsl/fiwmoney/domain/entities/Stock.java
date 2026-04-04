package com.mmsl.fiwmoney.domain.entities;

import com.mmsl.fiwmoney.dto.StockResponse;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Builder
@Data
@Entity
@Table(name = "stock", uniqueConstraints = @UniqueConstraint(columnNames = {"wallet_id", "code"}))
public class Stock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String code;
    private BigDecimal currentPrice;
    private BigDecimal averagePrice;
    private boolean notify;

}
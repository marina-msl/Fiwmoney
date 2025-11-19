package com.mmsl.fiwmoney.dto;

import java.util.List;

public record WalletDTO(Long id,List<StockDTO> stocks) {}

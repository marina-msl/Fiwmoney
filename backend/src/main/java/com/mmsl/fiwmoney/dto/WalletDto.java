package com.mmsl.fiwmoney.dto;

import java.util.List;

public record WalletDto(
    Long id,
    List<StockResult> stocks
) {}

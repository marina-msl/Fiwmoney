package com.mmsl.fiwmoney.dto;

import java.util.List;

public record WalletResponse (
    Long id,
    List<StockResponse> stocks
) {}
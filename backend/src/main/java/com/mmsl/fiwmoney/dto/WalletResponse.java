package com.mmsl.fiwmoney.dto;

import java.util.List;

public record WalletResponse (
    List<StockResponse> stocks
) {}
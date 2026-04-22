package com.mmsl.fiwmoney.domain.ports;

import java.math.BigDecimal;


public interface Fetch {
    
    BigDecimal getStockPrice(String code);
    
}


package com.mmsl.fiwmoney.domain.ports;

import java.math.BigDecimal;


public interface IFetch {
    
    BigDecimal getStockPrice(String code);
    
}


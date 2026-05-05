package com.mmsl.fiwmoney.application.exception;

import com.mmsl.fiwmoney.domain.exception.AbstractException;

public class APIStockNotFoundException extends AbstractException {

    public APIStockNotFoundException(String code) {
        super(
            "Stock not found",
            404,
            "Stock with code " + code + " not found in search-stock-api"
        );
    }

}

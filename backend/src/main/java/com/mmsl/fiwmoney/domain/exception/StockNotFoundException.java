package com.mmsl.fiwmoney.domain.exception;

public class StockNotFoundException extends AbstractException {

    public StockNotFoundException(String code) {
        super(
            "Stock not found",
            404,
            "Stock with code " + code + " not found in the wallet"
        );
    }

}

package com.mmsl.fiwmoney.domain.exception;

public class WalletNotFoundException extends AbstractException {
    
    public WalletNotFoundException() {
        super(
            "Wallet not found",
            404,
            String.format("Wallet not found")
        );
    }
}

package com.mmsl.fiwmoney.domain.exception;

public class WalletNotFoundException extends AbstractException {
    
    public WalletNotFoundException(Long id) {
        super(
            "Wallet not found",
            404,
            String.format("Wallet with id %d not found", id)
        );
    }
}

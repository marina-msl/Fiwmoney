package com.mmsl.fiwmoney.exception;

public class WalletNotFoundException extends RuntimeException {
    
    public WalletNotFoundException(Long id) {
        super("Wallet not found with ID: " + id);
    }
}

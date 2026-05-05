package com.mmsl.fiwmoney.application.service;

import org.springframework.stereotype.Service;

import com.mmsl.fiwmoney.domain.exception.WalletNotFoundException;
import com.mmsl.fiwmoney.domain.ports.UserRepository;

@Service
public class WalletOwnershipService {

    private final UserRepository userRepository;

    public WalletOwnershipService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Long getWalletByUser(String username) {
        Long walletId = userRepository.findWalletByUsername(username);

        if (walletId == null) {
            throw new WalletNotFoundException();
        }

        return walletId;
    }
}

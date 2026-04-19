package com.mmsl.fiwmoney.application.service;

import com.mmsl.fiwmoney.domain.ports.IUserRepository;

public class WalletOwnershipService {


    private IUserRepository userRepository;


    public WalletOwnershipService(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }


    public Long getWalletByUser(String username) {

            return userRepository.findWalletByUsername(username);
    }
    
}

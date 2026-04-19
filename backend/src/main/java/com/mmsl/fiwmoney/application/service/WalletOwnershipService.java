package com.mmsl.fiwmoney.application.service;

import com.mmsl.fiwmoney.domain.ports.IUserRepository;

public class WalletByUsernameService {


    private IUserRepository userRepository;


    public WalletByUsernameService(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }


    public Long getWalletByUser(String username) {

            return userRepository.findWalletByUsername(username);
    }
    
}

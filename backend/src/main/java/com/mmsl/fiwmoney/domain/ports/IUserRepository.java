package com.mmsl.fiwmoney.domain.ports;

import java.util.Optional;

import com.mmsl.fiwmoney.domain.entities.User;

public interface IUserRepository {

    void save(User user);
    
    Optional<User> findByUsername(String username);

    Long findWalletByUsername(String username);
    
}

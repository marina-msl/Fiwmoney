package com.mmsl.fiwmoney.domain.ports;

import java.util.Optional;

import com.mmsl.fiwmoney.domain.entities.User;

public interface IUserRepository {

    Optional<User> save(User user);
    
    Optional<User> findByUsername(String username);
    
}

package com.mmsl.fiwmoney.adapters.repositories.user;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Repository;

import com.mmsl.fiwmoney.domain.entities.User;
import com.mmsl.fiwmoney.domain.ports.IUserRepository;

@Repository
@ConditionalOnProperty(name = "user.repository", havingValue = "memory")
public class UserRepositoryImpl implements IUserRepository {

    @Autowired
    private UserJPARepository userJPARepository;

    @Override
    public Optional<User> save(User user) {
        return Optional.ofNullable(userJPARepository.save(user));
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return userJPARepository.findByUsername(username);
    }
}

package com.mmsl.fiwmoney.adapters.repositories.user;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Repository;

import com.mmsl.fiwmoney.domain.entities.User;
import com.mmsl.fiwmoney.domain.ports.IUserRepository;

@Repository
@ConditionalOnProperty(name = "user.repository", havingValue = "memory")
public class UserMemoryRepositoryImpl implements IUserRepository {

    private final List<User> users = new ArrayList<>();

    @Override
    public void save(User user) {
        this.users.add(user);
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return users.stream()
                .filter(user -> user.getUsername().equals(username))
                .findFirst();
    }

    @Override
    public Long findWalletByUsername(String username) {
        return users.stream()
                .filter(user -> user.getUsername().equals(username))
                .findFirst()
                .map(user -> user.getWallet().getId())
                .orElse(null);
    }
}

package com.mmsl.fiwmoney.adapters.repositories.user;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Repository;

import com.mmsl.fiwmoney.domain.entities.User;
import com.mmsl.fiwmoney.domain.entities.Wallet;
import com.mmsl.fiwmoney.domain.ports.UserRepository;

@Repository
@ConditionalOnProperty(name = "user.repository", havingValue = "memory")
public class UserMemoryRepositoryImpl implements UserRepository {

    private final List<User> users = new ArrayList<>();
    private final AtomicLong walletIdSequence = new AtomicLong(1);

    @Override
    public void save(User user) {
        Wallet walletWithId = Wallet.builder()
                .id(walletIdSequence.getAndIncrement())
                .stocks(new ArrayList<>())
                .build();

        User userWithWallet = User.builder()
                .id(user.getId())
                .username(user.getUsername())
                .password(user.getPassword())
                .name(user.getName())
                .email(user.getEmail())
                .wallet(walletWithId)
                .build();
                
        this.users.add(userWithWallet);
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

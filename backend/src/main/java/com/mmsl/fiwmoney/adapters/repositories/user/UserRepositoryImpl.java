package com.mmsl.fiwmoney.adapters.repositories.user;

import java.util.Optional;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Repository;

import com.mmsl.fiwmoney.adapters.repositories.wallet.WalletJPAEntity;
import com.mmsl.fiwmoney.domain.entities.User;
import com.mmsl.fiwmoney.domain.ports.UserRepository;

@Repository
@ConditionalOnProperty(name = "user.repository", havingValue = "jpa")
public class UserRepositoryImpl implements UserRepository {

    private final UserJPARepository userJPARepository;

    public UserRepositoryImpl(UserJPARepository userJPARepository) {
        this.userJPARepository = userJPARepository;
    }

    @Override
    public void save(User user) {
        UserJPAEntity userJPAEntity = new UserJPAEntity(user.getId(),
                    user.getUsername(),
                    user.getPassword(),
                    user.getName(),
                    user.getEmail(),
                    WalletJPAEntity.fromEntity(user.getWallet()));
        userJPARepository.save(userJPAEntity);
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return userJPARepository.findByUsername(username)
            .map(e -> new User(e.getId(), e.getUsername(), e.getPassword(), e.getName(), e.getEmail(),
                e.getWallet().toEntity(e.getWallet())));
    }

    @Override
    public Long findWalletByUsername(String username) {
        return userJPARepository.findWalletByUsername(username);
    }
}

package com.mmsl.fiwmoney.application.service;

import java.time.Duration;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.mmsl.fiwmoney.domain.ports.UserRepository;

@Service
public class WalletOwnershipService {

    private final UserRepository userRepository;

    private final RedisTemplate<String, Long> redisTemplate;

    public WalletOwnershipService(UserRepository userRepository,
                                            RedisTemplate<String, Long> redisTemplate) {
                this.userRepository = userRepository;
                this.redisTemplate = redisTemplate;
    }


    public Long getWalletByUser(String username) {

            Long walletId = redisTemplate.opsForValue().get(username);

            if (walletId != null) {
                return walletId;
            }

            walletId = userRepository.findWalletByUsername(username);

            redisTemplate.opsForValue().set(username, walletId, Duration.ofDays(1) ); // cache for one day

            return walletId;
    }
    
}

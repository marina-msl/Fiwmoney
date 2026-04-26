package com.mmsl.fiwmoney.adapters.repositories.user;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface UserJPARepository extends JpaRepository <UserJPAEntity, Long> {

    Optional<UserJPAEntity> findByUsername(String username);

    @Query("SELECT u.wallet.id FROM UserJPAEntity u WHERE u.username = :username")
    Long findWalletByUsername(String username);

}

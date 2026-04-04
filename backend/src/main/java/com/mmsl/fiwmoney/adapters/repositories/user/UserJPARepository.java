package com.mmsl.fiwmoney.adapters.repositories.user;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mmsl.fiwmoney.domain.entities.User;

@Repository
public interface UserJPARepository extends JpaRepository <User, Long> {

    Optional<User> findByUsername(String username);

}

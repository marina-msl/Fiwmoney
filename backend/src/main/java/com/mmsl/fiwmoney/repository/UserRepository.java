package com.mmsl.fiwmoney.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mmsl.fiwmoney.model.User;

public interface UserRepository extends JpaRepository <User, Long> {

    Optional<User> findByUsername(String username);

    Boolean existsByUsername(String username);


    
}

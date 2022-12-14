package com.youngjun.bank.domain.repository;

import com.youngjun.bank.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByAccountNumber(String accountNumber);
    Optional<User> findByUserName(String name);
}

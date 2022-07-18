package com.example.springsecurity.repository;

import com.example.springsecurity.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccoutRepository extends JpaRepository<Account, Long> {
    Optional<Account> findAccountByUsername (String username);
}

package com.springboot.banking.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springboot.banking.entity.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findByAccountAccountId(Long accountId);
}

package com.tharun.EquiTrade.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tharun.EquiTrade.Model.Transaction;
import com.tharun.EquiTrade.Model.User;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findByUserOrderByTransactionDateDesc(User user);
    List<Transaction> findAllByOrderByTransactionDateDesc();
    
}

package com.tharun.EquiTrade.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tharun.EquiTrade.Model.Transaction;
import com.tharun.EquiTrade.Model.User;
import com.tharun.EquiTrade.Repository.TransactionRepository;
import com.tharun.EquiTrade.Repository.UserRepository;

@Service
public class AdminService {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private TransactionRepository transactionRepo;

    public List<User> getAllUsers() {
        return userRepo.findAll();
    }

    public List<Transaction> getAllTransactions() {
        return transactionRepo.findAll();
    }

    public Map<String, Object> getSummaryReport() {

        Map<String, Object> summary = new HashMap<>();

        summary.put("totalUsers", userRepo.count());

        summary.put("totalTransactions", transactionRepo.count());

        double totalVolume = transactionRepo.findAll()
                .stream()
                .mapToDouble(Transaction::getTotalAmount)
                .sum();

        summary.put("totalVolume", totalVolume);

        return summary;
    }
}
package com.tharun.EquiTrade.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tharun.EquiTrade.Model.Portfolio;
import com.tharun.EquiTrade.Model.Transaction;
import com.tharun.EquiTrade.Model.User;
import com.tharun.EquiTrade.Repository.TransactionRepository;
import com.tharun.EquiTrade.Repository.UserRepository;
import com.tharun.EquiTrade.Service.PortfolioService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@PreAuthorize("hasAnyRole('USER','ADMIN')")
@RequestMapping("/api/portfolio")
public class PortfolioController {

    @Autowired
    private PortfolioService portfolioService;

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private TransactionRepository transactionRepo;


    @GetMapping
    public List<Portfolio> getPortfolio(Authentication authentication) {

        return portfolioService.getPortfolioByUsername(authentication.getName());
    }

    @DeleteMapping("/reset")
    public ResponseEntity<?> resetPortfolio(Authentication authentication) {

        portfolioService.resetPortfolio(authentication.getName());

        return ResponseEntity.ok("Portfolio reset successfully!");
    }

    @GetMapping("/transactions")
    public List<Transaction> getTransactions(Authentication authentication) {

        User user = userRepo.findByUserName(authentication.getName())
                .orElseThrow(() ->
                        new IllegalArgumentException("User not found"));

        return transactionRepo.findByUserOrderByTransactionDateDesc(user);
    }
}

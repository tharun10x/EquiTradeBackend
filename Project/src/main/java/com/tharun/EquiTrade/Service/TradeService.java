package com.tharun.EquiTrade.Service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tharun.EquiTrade.Enum.TransactionType;
import com.tharun.EquiTrade.Model.Portfolio;
import com.tharun.EquiTrade.Model.Stock;
import com.tharun.EquiTrade.Model.Transaction;
import com.tharun.EquiTrade.Model.User;
import com.tharun.EquiTrade.Repository.PortfolioRepository;
import com.tharun.EquiTrade.Repository.TransactionRepository;
import com.tharun.EquiTrade.Repository.UserRepository;

import org.springframework.transaction.annotation.Transactional;

@Service
public class TradeService {
    @Autowired
    StockService stkser;
    @Autowired
    UserRepository userRepo;
    @Autowired
    private PortfolioRepository portfolioRepo;

    @Autowired
    private TransactionRepository transactionRepo;

    @Transactional
    public void buyStock(String username, Long stockId, int qty){
        if (qty <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than zero.");
        }   
        Stock stk = stkser.getStockById(stockId)
            .orElseThrow(() -> new IllegalArgumentException("Stock not found with ID: " + stockId));
        User urs = userRepo.findByUserName(username)
        .orElseThrow(()->new IllegalArgumentException("User not found with username: "+username));


        double totalCost = stk.getCurrentPrice()*qty;
        if (urs.getWalletBalance() < totalCost) {
            throw new RuntimeException("Insufficient balance");
            }
        urs.setWalletBalance(urs.getWalletBalance() - totalCost );
        userRepo.save(urs);

        Portfolio portfolio =
        portfolioRepo.findByUserAndStock(urs, stk)
                     .orElse(null); 
        
        if (portfolio == null) {

        portfolio = new Portfolio();
        portfolio.setUser(urs);
        portfolio.setStock(stk);
        portfolio.setQuantity(qty);
        portfolio.setAvgBuyPrice(stk.getCurrentPrice());

        }else {

            int oldQty = portfolio.getQuantity();
            double oldAvgPrice = portfolio.getAvgBuyPrice();

            double newAvgPrice =
                    ((oldQty * oldAvgPrice)
                    + (qty * stk.getCurrentPrice()))
                    / (oldQty + qty);

            portfolio.setQuantity(oldQty + qty);
            portfolio.setAvgBuyPrice(newAvgPrice);
        }

        portfolioRepo.save(portfolio);

    
        Transaction transaction = new Transaction();
        transaction.setUser(urs);
        transaction.setStock(stk);
        transaction.setType(TransactionType.BUY);
        transaction.setQuantity(qty);
        transaction.setPrice(stk.getCurrentPrice());
        transaction.setTotalAmount(totalCost);

        transactionRepo.save(transaction);
    }
    
    @Transactional
    public void sellStock(String username, long stockId, int quantity){
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than zero.");
        }

        // TODO: Modify the general exception with custom exception using the golbal exception handler
        Stock stk = stkser.getStockById(stockId)
            .orElseThrow(() -> new IllegalArgumentException("Stock not found with ID: " + stockId));
        User urs = userRepo.findByUserName(username)
        .orElseThrow(()->new IllegalArgumentException("User not found with username: "+username));

        Portfolio portfolio =
        portfolioRepo.findByUserAndStock(urs, stk)
        .orElse(null); 

        if (portfolio == null) {
            throw new IllegalArgumentException("You don't own this stock.");
        }

        if (portfolio.getQuantity() < quantity) {
            throw new IllegalArgumentException("Insufficient shares.");
        }

        double totalAmount = stk.getCurrentPrice() * quantity;

        urs.setWalletBalance(urs.getWalletBalance() + totalAmount);
        userRepo.save(urs);

        int remainingQty = portfolio.getQuantity() - quantity;

        if (remainingQty == 0) {
            portfolioRepo.delete(portfolio);
        }
        else {
            portfolio.setQuantity(remainingQty);
            portfolioRepo.save(portfolio);
        }
        Transaction transaction = new Transaction();

        transaction.setUser(urs);
        transaction.setStock(stk);
        transaction.setType(TransactionType.SELL);
        transaction.setQuantity(quantity);
        transaction.setPrice(stk.getCurrentPrice());
        transaction.setTotalAmount(totalAmount);

        transactionRepo.save(transaction);
    }
    public List<Transaction> getTradeHistory(String username) {

    User user = userRepo.findByUserName(username)
            .orElseThrow(() ->
                    new IllegalArgumentException("User not found with username: " + username));

    return transactionRepo.findByUserOrderByTransactionDateDesc(user);
}

}

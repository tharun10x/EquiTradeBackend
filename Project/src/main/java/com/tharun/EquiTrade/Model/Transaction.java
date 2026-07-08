package com.tharun.EquiTrade.Model;

import java.time.LocalDateTime;
import com.tharun.EquiTrade.Enum.TransactionType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;


@Table(name="Transactions")
@Entity
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)     
    private long id;

    @Enumerated(EnumType.STRING)
    private TransactionType type;
    private int Quantity;
    private double price;
    private double totalAmount;
    @Column(nullable = false, updatable = false)
    private LocalDateTime TransactionDate = LocalDateTime.now();
     
    @ManyToOne     
    @JoinColumn(name="stock_id")     
    private Stock stock;     
     
    @ManyToOne     
    @JoinColumn(name="user_id")     
    private User user;

    public Transaction() {
    }

    public Transaction(long id, TransactionType type, int quantity, double price, double totalAmount,
        LocalDateTime transactionDate, Stock stock, User user) {
        this.id = id;
        this.type = type;
        Quantity = quantity;
        this.price = price;
        this.totalAmount = totalAmount;
        TransactionDate = transactionDate;
        this.stock = stock;
        this.user = user;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public TransactionType getType() {
        return type;
    }

    public void setType(TransactionType type) {
        this.type = type;
    }

    public int getQuantity() {
        return Quantity;
    }

    public void setQuantity(int quantity) {
        Quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public LocalDateTime getTransactionDate() {
        return TransactionDate;
    }

    public void setTransactionDate(LocalDateTime transactionDate) {
        TransactionDate = transactionDate;
    }

    public Stock getStock() {
        return stock;
    }

    public void setStock(Stock stock) {
        this.stock = stock;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }     

}

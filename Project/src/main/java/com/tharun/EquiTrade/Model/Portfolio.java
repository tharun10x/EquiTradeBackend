package com.tharun.EquiTrade.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Table(name="Portfolios")
@Entity
public class Portfolio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private int quantity;
    private double avgBuyPrice;

    @ManyToOne
    @JoinColumn(name="stock_id")
    private Stock stock;

    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;

    public Portfolio() {
    }

    public Portfolio(long Id, int qty, double avgBPrice, Stock stock, User user) {
        id = Id;
        quantity = qty;
        avgBuyPrice = avgBPrice;
        this.stock = stock;
        this.user = user;
    }

    public long getId() {
        return id;
    }

    public void setId(long Id) {
        id = Id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int qty) {
        quantity = qty;
    }

    public double getAvgBuyPrice() {
        return avgBuyPrice;
    }

    public void setAvgBuyPrice(double avgBPrice) {
        avgBuyPrice = avgBPrice;
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

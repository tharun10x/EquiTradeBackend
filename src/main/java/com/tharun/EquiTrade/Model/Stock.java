package com.tharun.EquiTrade.Model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Table(name="Stocks")
@Entity
public class Stock {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(unique = true, nullable = false)
    private String symbol;
    @Column(nullable = false)
    private String companyName;
    @Column(nullable = false  )
    private double currentPrice;
    public String getSymbol() {
        return symbol;
    }
    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    private String sector;
    public Stock() {
    }
    public Stock(int id, String symbol, String companyName,
             double currentPrice, String sector) {
 
    this.id = id;
    this.symbol = symbol;
    this.companyName = companyName;
    this.currentPrice = currentPrice;
    this.sector = sector;
    }
    public Long getId() {
    return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public double getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(double currentPrice) {
        this.currentPrice = currentPrice;
    }

    public String getSector() {
        return sector;
    }

    public void setSector(String sector) {
        this.sector = sector;
    }


}

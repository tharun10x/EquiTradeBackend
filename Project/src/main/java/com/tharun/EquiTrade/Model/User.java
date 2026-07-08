package com.tharun.EquiTrade.Model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;

@Table(name="Users")
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private long Id;

    @NotBlank(message = "UserName cannot be blank")
    @Column(unique = true)
    private String userName;

    @NotBlank(message = "Password cannot be blank")
    private String password;

    private String role;
    private Double walletBalance=100000.0;

    public User() {
    }
    public User(long id, @NotBlank(message = "UserName cannot be blank") String userName,
            @NotBlank(message = "Password cannot be blank") String password, String role, Double walletBalance) {
        Id = id;
        this.userName = userName;
        this.password = password;
        this.role = role;
        this.walletBalance = walletBalance;
    }
    public long getId() {
        return Id;
    }
    public void setId(long id) {
        this.Id=id;
    }
    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getRole() {
        return role;
    }
    public void setRole(String role) {
        this.role = role;
    }
    public Double getWalletBalance() {
        return walletBalance;
    }
    public void setWalletBalance(Double walletBalance) {
        this.walletBalance = walletBalance;
    }
}


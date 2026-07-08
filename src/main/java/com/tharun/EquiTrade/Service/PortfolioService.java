package com.tharun.EquiTrade.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tharun.EquiTrade.Model.Portfolio;
import com.tharun.EquiTrade.Model.User;
import com.tharun.EquiTrade.Repository.PortfolioRepository;
import com.tharun.EquiTrade.Repository.UserRepository;

@Service
public class PortfolioService {
    @Autowired
    PortfolioRepository portfolioRepo;
    @Autowired
    UserRepository userRepo;

    public List<Portfolio> getPortfolioByUsername(String username) {

    User user = userRepo.findByUserName(username)
            .orElseThrow(() ->
                    new IllegalArgumentException(
                            "User not found with username: " + username));

    return portfolioRepo.findByUser(user);
}

    public void resetPortfolio(String username) {

    User user = userRepo.findByUserName(username)
            .orElseThrow(() ->
                    new IllegalArgumentException(
                            "User not found with username: " + username));

    portfolioRepo.deleteByUser(user);

    user.setWalletBalance(100000.0);

    userRepo.save(user);
    }

}

package com.tharun.EquiTrade.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tharun.EquiTrade.Model.Portfolio;
import com.tharun.EquiTrade.Model.Stock;
import com.tharun.EquiTrade.Model.User;

public interface PortfolioRepository extends JpaRepository<Portfolio,Long > {
    List<Portfolio> findByUser(User user);
    Optional<Portfolio> findByUserAndStock(User user, Stock stock);
    void deleteByUser(User user);
}

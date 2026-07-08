package com.tharun.EquiTrade.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tharun.EquiTrade.Model.Stock;

public interface StockRepository extends JpaRepository<Stock,Long> {
    Optional<Stock> findBySymbol(String symbol);
    List<Stock> findBySector(String sector);
    //TODO:Used for the search endpoint GET /api/stocks/search/{keyword}.

    List<Stock> findBySymbolContainingIgnoreCaseOrCompanyNameContainingIgnoreCase(String symbol, String companyName);
}

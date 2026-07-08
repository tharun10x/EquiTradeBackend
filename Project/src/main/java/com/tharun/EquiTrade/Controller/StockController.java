package com.tharun.EquiTrade.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tharun.EquiTrade.Model.Stock;
import com.tharun.EquiTrade.Service.StockService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/stocks")
public class StockController {
    @Autowired
    StockService stkService;

    @GetMapping()
    public List<Stock> getAllStocks(){
        return stkService.getAllStocks();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Stock> getStockById(@PathVariable Long id){
        return stkService.getStockById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/search/{keyword}")
    public List<Stock> searchStocks(@PathVariable String keyword){
        return stkService.searchStocks(keyword);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    // TODO:Add Http status code
    public Stock createStock(@RequestBody Stock stock){
        return stkService.createStock(stock);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Stock> updateStock(
        @PathVariable Long id,
        @RequestBody Stock stock){
            Stock updatedStock = stkService.updateStock(id, stock);
            if (updatedStock == null) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(updatedStock);

    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> deleteStock(@PathVariable Long id) {

        stkService.deleteStock(id);

        return ResponseEntity.ok("Stock deleted successfully.");
    }
}

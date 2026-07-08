package com.tharun.EquiTrade.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tharun.EquiTrade.Model.Stock;
import com.tharun.EquiTrade.Repository.StockRepository;

@Service
public class StockService {
    @Autowired
    StockRepository stkRepo;
    public List<Stock> getAllStocks(){
        List<Stock> stocks = new ArrayList<>();
        stkRepo.findAll().forEach(stocks::add);
        return stocks;
    }
    public Optional<Stock> getStockById(Long id){
        return stkRepo.findById(id);
    }

    public List<Stock> searchStocks(String Keyword){
        return stkRepo.findBySymbolContainingIgnoreCaseOrCompanyNameContainingIgnoreCase(Keyword, Keyword);
    }
    public Stock createStock(Stock stock){
        return stkRepo.save(stock);
    }
    // TODO: Need to complete this method
    public Stock updateStock(Long id, Stock stockDetails){
        Stock stk = stkRepo.findById(id)
        .orElseThrow(() -> new IllegalArgumentException("Stock not Found "+id));

        String[] ignoredProperties = getNullAndIdPropertyNames(stockDetails);
        BeanUtils.copyProperties(stockDetails, stk, ignoredProperties);

        return stkRepo.save(stk);
    }

    public void deleteStock(Long id){
        if (!stkRepo.existsById(id)) {
        throw new IllegalArgumentException("Stock not found with ID: " + id);
        }
        stkRepo.deleteById(id);
    }

    //Helper function for updatestock 
    private String[] getNullAndIdPropertyNames(Object source) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();

        Set<String> emptyNames = new HashSet<>();
        emptyNames.add("id"); // Always ignore the ID

        for (java.beans.PropertyDescriptor pd : pds) {
            Object srcValue = src.getPropertyValue(pd.getName());
            if (srcValue == null) {
                emptyNames.add(pd.getName());
            }
        }
        
        return emptyNames.toArray(new String[0]);
    }
}

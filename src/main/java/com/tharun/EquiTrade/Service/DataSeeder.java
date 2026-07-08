package com.tharun.EquiTrade.Service;

import com.tharun.EquiTrade.Model.Stock;
import com.tharun.EquiTrade.Repository.StockRepository;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
@ConditionalOnProperty(name = "app.seeding.enabled", havingValue = "true", matchIfMissing = false)
public class DataSeeder implements CommandLineRunner {

    private final StockRepository stockRepository;

    public DataSeeder(StockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        long currentCount = stockRepository.count();
        System.out.println(">>> [SEEDER LOG] Current stock rows in MySQL: " + currentCount);
        
        if (currentCount == 0) {
            System.out.println(">>> [SEEDER LOG] Database is empty. Initiating CSV reading...");
            loadStockData();
        } else {
            System.out.println(">>> [SEEDER LOG] Stock database table is already seeded. Skipping initialization.");
        }
    }

    private void loadStockData() {
        // Explicitly defining the header mappings as seen in your CSV file screenshot
        CSVFormat format = CSVFormat.DEFAULT.builder()
                .setHeader("SYMBOL", "COMPANY NAME", "Sector", "Current_Price")
                .setSkipHeaderRecord(true)
                .build();

        List<Stock> stockList = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(
                new ClassPathResource("nifty500.csv").getInputStream(), "UTF-8"));
             CSVParser csvParser = new CSVParser(reader, format)) {

            System.out.println(">>> [SEEDER LOG] Successfully loaded nifty500.csv file from resources.");

            for (CSVRecord record : csvParser) {
                // Fetch fields reliably using the precise header names
                String symbol = record.get("SYMBOL").trim();
                String companyName = record.get("COMPANY NAME").trim();
                String sector = record.get("Sector").trim();
                
                // Strips out any internal comma separators (like in "4,356.5") so it parses smoothly
                String priceStr = record.get("Current_Price").trim().replace(",", "");
                
                double currentPrice = 0.0;
                try {
                    currentPrice = Double.parseDouble(priceStr);
                } catch (NumberFormatException e) {
                    System.err.println(">>> [SEEDER WARNING] Could not parse price for " + symbol + ", defaulting to 0.0");
                }

                // Pass 0 as the ID since MySQL @GeneratedValue(strategy = GenerationType.IDENTITY) will automatically assign it
                Stock stock = new Stock(0, symbol, companyName, currentPrice, sector);
                stockList.add(stock);
            }

            System.out.println(">>> [SEEDER LOG] Parsed " + stockList.size() + " stocks. Transmitting to MySQL database...");
            
            if (!stockList.isEmpty()) {
                stockRepository.saveAll(stockList);
                stockRepository.flush(); // Forces immediate write execution into MySQL
                System.out.println(">>> [SEEDER LOG] Done! New stock count verified in DB: " + stockRepository.count());
            }

        } catch (IOException e) {
            System.err.println(">>> [SEEDER ERROR] Failed to parse or locate 'nifty500.csv'. Check filename/extension.");
            e.printStackTrace();
        }
    }
}
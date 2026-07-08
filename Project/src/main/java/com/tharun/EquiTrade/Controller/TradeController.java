package com.tharun.EquiTrade.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tharun.EquiTrade.Dto.TradeRequest;
import com.tharun.EquiTrade.Model.Transaction;
import com.tharun.EquiTrade.Service.TradeService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("api/trade")
@PreAuthorize("hasAnyRole('USER','ADMIN')")
public class TradeController {
     @Autowired
    private TradeService tradeService;

    @PostMapping("/buy")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<String> buyStock(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestBody TradeRequest request) {

        tradeService.buyStock(
                userDetails.getUsername(),
                request.getStockId(),
                request.getQuantity());

        return ResponseEntity.ok("Purchase successful!");
    }

    @PostMapping("/sell")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<String> sellStock(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestBody TradeRequest request) {

        tradeService.sellStock(
                userDetails.getUsername(),
                request.getStockId(),
                request.getQuantity());

        return ResponseEntity.ok("Sale successful!");
    }

    @GetMapping("/history")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<List<Transaction>> getTradeHistory(
            @AuthenticationPrincipal UserDetails userDetails) {

        return ResponseEntity.ok(
                tradeService.getTradeHistory(userDetails.getUsername()));
    }
}

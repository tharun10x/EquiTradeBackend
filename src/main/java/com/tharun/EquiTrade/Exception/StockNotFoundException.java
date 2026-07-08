package com.tharun.EquiTrade.Exception;

public class StockNotFoundException extends RuntimeException{
    StockNotFoundException(String text){
        super(text);
    }
}

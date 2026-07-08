package com.tharun.EquiTrade.Exception;

public class EntityNotFoundException extends RuntimeException {
    EntityNotFoundException(String text){
        super(text);
    }
}

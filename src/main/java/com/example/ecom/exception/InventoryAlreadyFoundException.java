package com.example.ecom.exception;

public class InventoryAlreadyFoundException extends RuntimeException{

    public InventoryAlreadyFoundException(String message) {
        super(message);
    }
}

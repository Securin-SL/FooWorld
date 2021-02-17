package com.example.ecom.exception;

public class InsufficientInventoryException extends RuntimeException{

    public InsufficientInventoryException(int actual, int expected) {
        super("Inventory is insufficient to process the order, actual quantity : "+actual+", expected quantity : "+expected);
    }
}

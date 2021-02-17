package com.example.ecom.exception;


import com.example.ecom.model.OrderStatus;

public class OrderStatusCannotBeChangedException extends RuntimeException{

    public OrderStatusCannotBeChangedException(OrderStatus oldStatus, OrderStatus newStatus){
        super("Order Status cannot be changed from "+oldStatus+" to "+newStatus);
    }
}

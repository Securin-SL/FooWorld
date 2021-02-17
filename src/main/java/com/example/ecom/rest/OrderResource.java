package com.example.ecom.rest;


import com.example.ecom.model.entity.Order;
import com.example.ecom.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/orders")
public class OrderResource {

    private OrderService orderService;

    @Autowired
    public OrderResource(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/{orderId}")
    @ResponseStatus(HttpStatus.OK)
    public EntityModel<Order> getOrderById(@PathVariable(name = "orderId") Long orderId) {
        return EntityModel
                .of(orderService
                                .getOrderById(orderId)
                        , WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(OrderResource.class).getOrderById(orderId)).withSelfRel()
                        , WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(OrderResource.class).getAllOrders()).withRel("orders"));
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public CollectionModel<EntityModel<Order>> getAllOrders() {
        List<EntityModel<Order>> orders = orderService
                .getAllOrders()
                .stream()
                .map(order -> EntityModel.of(order
                        , WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(OrderResource.class).getOrderById(order.getId())).withSelfRel()
                        , WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(OrderResource.class).getAllOrders()).withRel("orders"))).collect(Collectors.toList());
        return CollectionModel.of(orders, WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(OrderResource.class).getAllOrders()).withSelfRel());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EntityModel<Order> createOrder(@Valid @RequestBody Order order) {
        return EntityModel
                .of(orderService.createOrder(order)
                        , WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(OrderResource.class).getOrderById(order.getId())).withSelfRel()
                        , WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(OrderResource.class).getAllOrders()).withRel("orders"));
    }

    @DeleteMapping("/{orderId}")
    @ResponseStatus(HttpStatus.OK)
    public void removeOrder(@PathVariable("orderId") Long orderId) {
        orderService.removeOrder(orderId);
    }

    @PutMapping("/{orderId}")
    @ResponseStatus(HttpStatus.OK)
    public EntityModel<Order> updateOrder(@RequestBody Order order, @PathVariable("orderId") Long orderId) {
        return EntityModel
                .of(orderService.updateOrder(orderId, order)
                        , WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(OrderResource.class).getOrderById(orderId)).withSelfRel()
                        , WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(OrderResource.class).getAllOrders()).withRel("orders"));
    }

    @PutMapping("/{orderId}/confirmation")
    @ResponseStatus(HttpStatus.OK)
    public EntityModel<Order> confirmOrder(@PathVariable("orderId") Long orderId) {
        return EntityModel
                .of(orderService.confirmOrder(orderId)
                        , WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(OrderResource.class).getOrderById(orderId)).withSelfRel()
                        , WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(OrderResource.class).getAllOrders()).withRel("orders"));
    }

    @PutMapping("/{orderId}/cancellation")
    @ResponseStatus(HttpStatus.OK)
    public EntityModel<Order> cancelOrder(@PathVariable("orderId") Long orderId) {
        return EntityModel
                .of(orderService.cancelOrder(orderId)
                        , WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(OrderResource.class).getOrderById(orderId)).withSelfRel()
                        , WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(OrderResource.class).getAllOrders()).withRel("orders"));
    }
}

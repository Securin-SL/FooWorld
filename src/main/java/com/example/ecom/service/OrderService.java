package com.example.ecom.service;

import com.example.ecom.exception.InsufficientInventoryException;
import com.example.ecom.exception.NotFoundException;
import com.example.ecom.exception.OrderStatusCannotBeChangedException;
import com.example.ecom.model.OrderStatus;
import com.example.ecom.model.entity.Inventory;
import com.example.ecom.model.entity.Order;
import com.example.ecom.model.entity.OrderProduct;
import com.example.ecom.model.entity.Product;
import com.example.ecom.repo.InventoryRepository;
import com.example.ecom.repo.OrderRepository;
import com.example.ecom.repo.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final InventoryRepository inventoryRepository;

    @Autowired
    public OrderService(OrderRepository orderRepository, ProductRepository productRepository, InventoryRepository inventoryRepository) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
        this.inventoryRepository = inventoryRepository;
    }

    public Order getOrderById(Long orderId) {
        return orderRepository
                .findById(orderId)
                .orElseThrow(() -> new NotFoundException("Order with order id " + orderId + " can't be found"));
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Order createOrder(Order order) {
        List<Product> productList = productRepository
                .findAllById(order
                        .getOrderProducts()
                        .stream()
                        .map(orderProduct -> orderProduct.getProduct().getId())
                        .collect(Collectors.toList()));
        if (productList.isEmpty())
            throw new NotFoundException("Products can't be found");
        Map<Long, Product> products = productList
                .stream()
                .collect(Collectors.toMap(Product::getId, product -> product));
        List<Inventory> inventoryList = inventoryRepository
                .getInventoriesByProductId(order
                        .getOrderProducts()
                        .stream()
                        .map(orderProduct -> orderProduct.getProduct().getId())
                        .collect(Collectors.toList()));
        if (inventoryList.isEmpty())
            throw new NotFoundException("Inventory is empty");
        Map<Long, Inventory> inventories = inventoryList
                .stream()
                .collect(Collectors.toMap(inventory -> inventory.getProduct().getId(), inventory -> inventory));
        order
                .getOrderProducts()
                .forEach(orderProduct -> processOrder(order, orderProduct, products, inventories));
        order.setTotal(order
                .getOrderProducts()
                .stream()
                .map(OrderProduct::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add));
        order.setCreatedBy("ADMIN");
        order.setLastModifiedBy("ADMIN");
        inventoryRepository.saveAll(inventories.values());
        return orderRepository.save(order);
    }

    private void processOrder(Order order, OrderProduct orderProduct, Map<Long, Product> products, Map<Long, Inventory> inventories) {
        Product product = products.get(orderProduct.getProduct().getId());
        Inventory inventory = inventories.get(product.getId());
        if (inventory.getNoOfItems() < orderProduct.getQuantity())
            throw new InsufficientInventoryException(inventory.getNoOfItems(), orderProduct.getQuantity());
        inventory.setNoOfItems(inventory.getNoOfItems() - orderProduct.getQuantity());
        orderProduct.setProduct(product);
        orderProduct.setOrder(order);
        BigDecimal orderProductPrice = BigDecimal.valueOf(orderProduct.getQuantity()).multiply(product.getPrice());
        orderProduct.setPrice(orderProductPrice);
        orderProduct.setCreatedBy("ADMIN");
        orderProduct.setLastModifiedBy("ADMIN");
    }

    public void removeOrder(Long orderId) {
        orderRepository.deleteById(orderId);
    }

    public Order updateOrder(Long orderId, Order order) {
        Order orderToBeFound = getOrderById(orderId);
        orderToBeFound.setLastModifiedDate(LocalDateTime.now());
        orderToBeFound.setLastModifiedBy("NEW ADMIN");
        return orderRepository
                .save(orderToBeFound);
    }

    public Order confirmOrder(Long orderId) {
        Order orderToBeFound = getOrderById(orderId);
        if (!(orderToBeFound.getStatus() == OrderStatus.NEW))
            throw new OrderStatusCannotBeChangedException(orderToBeFound.getStatus(), OrderStatus.CONFIRMED);
        orderToBeFound.setStatus(OrderStatus.CONFIRMED);
        orderRepository.save(orderToBeFound);
        return orderToBeFound;
    }

    public Order cancelOrder(Long orderId) {
        Order orderToBeFound = getOrderById(orderId);
        if (!(orderToBeFound.getStatus() == OrderStatus.NEW))
            throw new OrderStatusCannotBeChangedException(orderToBeFound.getStatus(), OrderStatus.CANCELLED);
        orderToBeFound.setStatus(OrderStatus.CANCELLED);
        orderRepository.save(orderToBeFound);
        return orderToBeFound;
    }

}

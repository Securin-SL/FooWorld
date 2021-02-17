package com.example.ecom.model.entity;

import com.example.ecom.model.OrderStatus;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "orders")
@AllArgsConstructor
@NoArgsConstructor
public class Order extends Audit{

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "OrderIdGenerator")
    @SequenceGenerator(name = "OrderIdGenerator", sequenceName = "ORD_ID_SEQUENCE", allocationSize = 1)
    @Column(name = "ORDER_ID", unique = true, nullable = false)
    @JsonProperty("Id")
    private Long Id;

    @Column(name = "ORD_TOTAL", nullable = false)
    @JsonProperty("Total")
    private BigDecimal total;

    @Enumerated(EnumType.STRING)
    @Column(name = "ORD_STATUS", nullable = false)
    @JsonProperty("Status")
    private OrderStatus status = OrderStatus.NEW;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "order")
    @JsonProperty("OrderProducts")
    private List<OrderProduct> orderProducts = new ArrayList<>();
}

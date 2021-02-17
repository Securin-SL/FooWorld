package com.example.ecom.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@Entity
@Table(name = "ORDER_PRODUCTS")
@EqualsAndHashCode(callSuper = false)
@JsonPropertyOrder({"id", "quantity", "price", "product"})
public class OrderProduct extends Audit {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "OrderProductIdGenerator")
    @SequenceGenerator(name = "OrderProductIdGenerator", sequenceName = "OPS_ID_SEQUENCE", allocationSize = 1)
    @Column(name = "OPS_ID", unique = true, nullable = false)
    @JsonProperty("Id")
    private Long id;

    @ManyToOne
    @JsonIgnore
    private Order order;

    @OneToOne
    @JoinColumn(name = "OPS_PRD_ID", referencedColumnName = "PRD_ID")
    @JsonProperty("Product")
    private Product product;

    @Column(name = "OPS_QUANTITY", nullable = false)
    @JsonProperty("Quantity")
    private Integer quantity;

    @Column(name = "OPS_PRICE", nullable = false)
    @JsonProperty("Price")
    private BigDecimal price;
}

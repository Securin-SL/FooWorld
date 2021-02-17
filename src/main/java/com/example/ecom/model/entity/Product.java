package com.example.ecom.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@Entity
@Table(name = "PRODUCTS")
@EqualsAndHashCode(callSuper = false)
@JsonPropertyOrder({"id", "name", "price"})
public class Product extends Audit {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ProductIdGenerator")
    @SequenceGenerator(name = "ProductIdGenerator", sequenceName = "PRD_ID_SEQUENCE", allocationSize = 1)
    @Column(name = "PRD_ID", unique = true, nullable = false)
    @JsonProperty("Id")
    private Long id;

    @Column(name = "PRD_NAME", nullable = false)
    @JsonProperty(value = "Name", required = true)
    @NotBlank(message = "Name must not be blank")
    private String name;

    @Column(name = "PRD_PRICE", nullable = false)
    @JsonProperty(value = "Price", required = true)
    @NotNull(message = "Price must not be null")
    private BigDecimal price;
}

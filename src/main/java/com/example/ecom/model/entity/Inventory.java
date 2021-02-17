package com.example.ecom.model.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@Entity
@Table(name = "INVENTORY")
@EqualsAndHashCode(callSuper=false)
@JsonPropertyOrder({"id", "product", "noOfItems"})
public class Inventory extends Audit {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "InventoryIdGenerator")
    @SequenceGenerator(name = "InventoryIdGenerator", sequenceName = "INV_ID_SEQUENCE", allocationSize = 1)
    @Column(name = "INV_ID", unique = true, nullable = false)
    @JsonProperty("Id")
    private Long id;

    @OneToOne
    @JoinColumn(name = "INV_PRD_ID", referencedColumnName = "PRD_ID")
    @JsonProperty("Product")
    private Product product;

    @Column(name = "INV_NO_OF_ITEMS", nullable = false)
    @JsonProperty("NoOfItems")
    private Integer noOfItems;

}

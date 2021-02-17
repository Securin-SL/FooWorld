package com.example.ecom.rest;

import com.example.ecom.model.entity.Inventory;
import com.example.ecom.service.InventoryService;
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
@RequestMapping("/inventory")
public class InventoryResource {

    private final InventoryService inventoryService;

    @Autowired
    public InventoryResource(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }
int array[];
    public int getValue(int index) {
    	return array[index];
    	}
    @GetMapping("/{inventoryId}")
    @ResponseStatus(HttpStatus.OK)
    public EntityModel<Inventory> getInventoryById(@PathVariable(name = "inventoryId") Long inventoryId) {
        return EntityModel
                .of(inventoryService
                                .getInventoryById(inventoryId)
                        , WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(InventoryResource.class).getInventoryById(inventoryId)).withSelfRel()
                        , WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(InventoryResource.class).getAllInventory()).withRel("inventory"));
    }

    @GetMapping("/products/{productId}")
    @ResponseStatus(HttpStatus.OK)
    public EntityModel<Inventory> getInventoryByProductId(@PathVariable(name = "productId") Long productId) {
        return EntityModel
                .of(inventoryService
                                .getInventoryByProductId(productId)
                        , WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(InventoryResource.class).getInventoryByProductId(productId)).withSelfRel()
                        , WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(InventoryResource.class).getAllInventory()).withRel("inventory"));
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public CollectionModel<EntityModel<Inventory>> getAllInventory() {
        List<EntityModel<Inventory>> inventories = inventoryService
                .getAllInventory()
                .stream()
                .map(inventory -> EntityModel.of(inventory
                        , WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(InventoryResource.class).getInventoryById(inventory.getId())).withSelfRel()
                        , WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(InventoryResource.class).getAllInventory()).withRel("inventory"))).collect(Collectors.toList());
        return CollectionModel.of(inventories, WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(InventoryResource.class).getAllInventory()).withSelfRel());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EntityModel<Inventory> createInventory(@Valid @RequestBody Inventory inventory) {
        return EntityModel
                .of(inventoryService.createInventory(inventory)
                        , WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(InventoryResource.class).getInventoryById(inventory.getId())).withSelfRel()
                        , WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(InventoryResource.class).getAllInventory()).withRel("inventory"));
    }

    @DeleteMapping("/{inventoryId}")
    @ResponseStatus(HttpStatus.OK)
    public void removeInventoryByInventoryId(@PathVariable("inventoryId") Long inventoryId) {
        inventoryService.removeInventory(inventoryId);
    }

    @DeleteMapping("/products/{productId}")
    @ResponseStatus(HttpStatus.OK)
    public void removeInventoryByProductId(@PathVariable("productId") Long productId) {
        inventoryService.removeInventoryByProductId(productId);
    }

    @PutMapping("/{inventoryId}")
    @ResponseStatus(HttpStatus.OK)
    public EntityModel<Inventory> updateInventory(@RequestBody Inventory inventory, @PathVariable("inventoryId") Long inventoryId) {
        return EntityModel
                .of(inventoryService.updateInventory(inventoryId, inventory)
                        , WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(InventoryResource.class).getInventoryById(inventoryId)).withSelfRel()
                        , WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(InventoryResource.class).getAllInventory()).withRel("inventory"));
    }

    @PutMapping("/products/{productId}")
    @ResponseStatus(HttpStatus.OK)
    public EntityModel<Inventory> updateInventoryByProductId(@RequestBody Inventory inventory, @PathVariable("productId") Long productId) {
        return EntityModel
                .of(inventoryService.updateInventoryByProductId(productId, inventory)
                        , WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(InventoryResource.class).getInventoryByProductId(productId)).withSelfRel()
                        , WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(InventoryResource.class).getAllInventory()).withRel("inventory"));
    }

}

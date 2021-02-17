package com.example.ecom.service;

import com.example.ecom.exception.InventoryAlreadyFoundException;
import com.example.ecom.exception.NotFoundException;
import com.example.ecom.model.entity.Inventory;
import com.example.ecom.model.entity.Product;
import com.example.ecom.repo.InventoryRepository;
import com.example.ecom.repo.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@SuppressWarnings("Duplicates")
public class InventoryService {

    private final InventoryRepository inventoryRepository;
    private final ProductRepository productRepository;

    @Autowired
    public InventoryService(InventoryRepository inventoryRepository, ProductRepository productRepository) {
        this.inventoryRepository = inventoryRepository;
        this.productRepository = productRepository;
    }


    public Inventory getInventoryById(Long inventoryId) {
        return inventoryRepository
                .findById(inventoryId)
                .orElseThrow(() -> new NotFoundException("Inventory can't be found with inventory id " + inventoryId));
    }

    public Inventory getInventoryByProductId(Long productId) {
        return inventoryRepository
                .getInventoryByProductId(productId)
                .orElseThrow(() -> new NotFoundException("Inventory can't be found for the product with product id " + productId));
    }

    public List<Inventory> getAllInventory() {
        return inventoryRepository
                .findAll();
    }

    public Inventory createInventory(Inventory inventory) {
        Optional<Inventory> inventoryToFind = inventoryRepository
                .getInventoryByProductId(inventory.getProduct().getId());
        if (inventoryToFind.isPresent())
            throw new InventoryAlreadyFoundException("Inventory already found for the product with inventory id " + inventoryToFind.get().getId());
        Product product = getProductByProductId(inventory.getProduct().getId());
        inventory.setProduct(product);
        inventory.setCreatedBy("ADMIN");
        inventory.setLastModifiedBy("ADMIN");
        return inventoryRepository.save(inventory);
    }

    public void removeInventory(Long inventoryId) {
        inventoryRepository.deleteById(inventoryId);
    }


    public void removeInventoryByProductId(Long productId) {
        inventoryRepository.delete(getInventoryByProductId(productId));
    }

    public Inventory updateInventory(Long inventoryId, Inventory inventory) {
        Inventory inventoryToUpdate = getInventoryById(inventoryId);
        Product product = getProductByProductId(inventory.getProduct().getId());
        inventoryToUpdate.setNoOfItems(inventory.getNoOfItems());
        inventoryToUpdate.setProduct(product);
        inventoryToUpdate.setLastModifiedBy("NEW ADMIN");
        inventoryToUpdate.setLastModifiedDate(LocalDateTime.now());
        inventoryRepository.save(inventoryToUpdate);
        return inventoryToUpdate;
    }

    public Inventory updateInventoryByProductId(Long productId, Inventory inventory) {
        Inventory inventoryToUpdate = getInventoryByProductId(productId);
        Product product = getProductByProductId(inventory.getProduct().getId());
        inventoryToUpdate.setNoOfItems(inventory.getNoOfItems());
        inventoryToUpdate.setProduct(product);
        inventoryToUpdate.setLastModifiedBy("NEW ADMIN");
        inventoryToUpdate.setLastModifiedDate(LocalDateTime.now());
        inventoryRepository.save(inventoryToUpdate);
        return inventoryToUpdate;
    }

    private Product getProductByProductId(Long productId) {
        return productRepository
                .findById(productId)
                .orElseThrow(() -> new NotFoundException("Product with product id " + productId + " can't be found"));
    }

}

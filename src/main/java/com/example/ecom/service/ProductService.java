package com.example.ecom.service;

import com.example.ecom.exception.NotFoundException;
import com.example.ecom.model.entity.Product;
import com.example.ecom.repo.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product getProductById(Long productId) {
        return productRepository
                .findById(productId)
                .orElseThrow(() -> new NotFoundException("Product with product id " + productId + " can't be found"));
    }

    public List<Product> getAllProducts() {
        return productRepository
                .findAll();
    }

    public Product createProduct(Product product) {
        product.setCreatedBy("ADMIN");
        product.setLastModifiedBy("ADMIN");
        return productRepository
                .save(product);
    }

    public void removeProduct(Long productId) {
        productRepository
                .deleteById(productId);
    }

    public Product updateProduct(Long productId, Product product) {
        Product foundProduct = getProductById(productId);
        foundProduct.setName(product.getName());
        foundProduct.setPrice(product.getPrice());
        foundProduct.setLastModifiedDate(LocalDateTime.now());
        foundProduct.setLastModifiedBy("NEW ADMIN");
        return productRepository
                .save(foundProduct);
    }

}

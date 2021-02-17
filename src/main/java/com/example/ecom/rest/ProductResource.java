package com.example.ecom.rest;

import com.example.ecom.model.entity.Product;
import com.example.ecom.service.ProductService;
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
@RequestMapping("/products")
public class ProductResource {

    private final ProductService productService;

    @Autowired
    public ProductResource(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/{productId}")
    @ResponseStatus(HttpStatus.OK)
    public EntityModel<Product> getProductById(@PathVariable(name = "productId") Long productId) {
        return EntityModel
                .of(productService
                                .getProductById(productId)
                        , WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ProductResource.class).getProductById(productId)).withSelfRel()
                        , WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ProductResource.class).getAllProducts()).withRel("products"));
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public CollectionModel<EntityModel<Product>> getAllProducts() {
        List<EntityModel<Product>> products = productService
                .getAllProducts()
                .stream()
                .map(product -> EntityModel.of(product
                        , WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ProductResource.class).getProductById(product.getId())).withSelfRel()
                        , WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ProductResource.class).getAllProducts()).withRel("products"))).collect(Collectors.toList());
        return CollectionModel.of(products, WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ProductResource.class).getAllProducts()).withSelfRel());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EntityModel<Product> createProduct(@Valid @RequestBody Product product) {
        return EntityModel
                .of(productService.createProduct(product)
                        , WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ProductResource.class).getProductById(product.getId())).withSelfRel()
                        , WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ProductResource.class).getAllProducts()).withRel("products"));
    }

    @DeleteMapping("/{productId}")
    @ResponseStatus(HttpStatus.OK)
    public void removeProduct(@PathVariable("productId") Long productId) {
        productService.removeProduct(productId);
    }

    @PutMapping("/{productId}")
    @ResponseStatus(HttpStatus.OK)
    public EntityModel<Product> updateProduct(@RequestBody Product product, @PathVariable("productId") Long productId) {
        return EntityModel
                .of(productService.updateProduct(productId, product)
                        , WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ProductResource.class).getProductById(productId)).withSelfRel()
                        , WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ProductResource.class).getAllProducts()).withRel("products"));
    }

}

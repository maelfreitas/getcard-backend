package com.web.getcard.controllers;

import com.web.getcard.entities.Product;
import com.web.getcard.services.ProductService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/add/{profileId}")
    public Product addProduct(@PathVariable int profileId, @RequestBody Product product) {
        return productService.addProductToProfile(profileId, product);
    }

    @GetMapping("/list/{profileId}")
    public List<Product> getProducts(@PathVariable int profileId) {
        return productService.getProductsByProfile(profileId);
    }

    @DeleteMapping("/{productId}")
    public void deleteProduct(@PathVariable int productId) {
        productService.deleteProduct(productId);
    }
}


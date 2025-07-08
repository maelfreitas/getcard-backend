package com.web.getcard.controllers;

import com.web.getcard.entities.Product;
import com.web.getcard.entities.Profile;
import com.web.getcard.entities.User;
import com.web.getcard.repositories.ProductRepository;
import com.web.getcard.repositories.UserRepository;
import com.web.getcard.services.ProductService;
import com.web.getcard.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/product")
public class ProductController {


    private final ProductService productService;
    private final ProductRepository productRepository;

    public ProductController(ProductService productService, UserService userService, UserRepository userRepository, ProductRepository productRepository) {
        this.productService = productService;
        this.productRepository = productRepository;
    }


    @PostMapping("/add/{profileId}")
    public Product addProduct(@PathVariable int profileId, @RequestBody Product product) {
        return productService.addProductToProfile(profileId, product);
    }

    @GetMapping("/list/{profileId}")
    public List<Product> getProducts(@PathVariable int profileId) {
        return productService.getProductsByProfile(profileId);
    }

    @GetMapping("/{productId}")
    public Product getProductById(@PathVariable int productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));
    }

    @PutMapping("/update/{productId}")
    public Product updateProduct(@PathVariable int productId, @RequestBody Product product) {
        return productService.updateProduct(productId, product);
    }


    @DeleteMapping("/{productId}")
    public void deleteProduct(@PathVariable int productId) {
        productService.deleteProduct(productId);
    }
}


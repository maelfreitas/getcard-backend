package com.web.getcard.controllers;

import com.web.getcard.entities.Product;
import com.web.getcard.entities.Profile;
import com.web.getcard.entities.User;
import com.web.getcard.repositories.UserRepository;
import com.web.getcard.services.ProductService;
import com.web.getcard.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product")
public class ProductController {


    private final ProductService productService;

    public ProductController(ProductService productService, UserService userService, UserRepository userRepository) {
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


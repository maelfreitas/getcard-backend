package com.web.getcard.services;

import com.web.getcard.entities.Product;
import com.web.getcard.entities.Profile;
import com.web.getcard.repositories.ProductRepository;
import com.web.getcard.repositories.ProfileRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final ProfileRepository profileRepository;

    public ProductService(ProductRepository productRepository, ProfileRepository profileRepository) {
        this.productRepository = productRepository;
        this.profileRepository = profileRepository;
    }

    public Product addProductToProfile(int profileId, Product product) {
        Profile profile = profileRepository.findById(profileId)
                .orElseThrow(() -> new RuntimeException("Profile não encontrado"));

        product.setProfile(profile);
        return productRepository.save(product);
    }

    public List<Product> getProductsByProfile(int profileId) {
        return productRepository.findByProfileId(profileId);
    }


    public void deleteProduct(int productId) {
        productRepository.deleteById(productId);
    }
}


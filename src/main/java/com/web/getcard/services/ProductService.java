package com.web.getcard.services;

import com.web.getcard.entities.Product;
import com.web.getcard.entities.Profile;
import com.web.getcard.repositories.ProductRepository;
import com.web.getcard.repositories.ProfileRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    public Product getProductById(int productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));
    }

    // Atualizar produto
    public Product updateProduct(int productId, Product updatedProduct) {
        Product existing = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));

        existing.setName(updatedProduct.getName());
        existing.setDescription(updatedProduct.getDescription());
        existing.setImg(updatedProduct.getImg());
        existing.setLink(updatedProduct.getLink());

        return productRepository.save(existing);
    }



    public void deleteProduct(int productId) {
        productRepository.deleteById(productId);
    }
}


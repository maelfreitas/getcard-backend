package com.web.getcard.repositories;

import com.web.getcard.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Integer> {
    List<Product> findByProfileId(int profileId);
    Optional<Product> findById(int productId);
}

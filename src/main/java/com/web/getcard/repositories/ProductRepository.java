package com.web.getcard.repositories;

import com.web.getcard.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Integer> {
    List<Product> findByProfileId(int profileId);
}

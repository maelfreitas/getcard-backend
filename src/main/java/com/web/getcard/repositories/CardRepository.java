package com.web.getcard.repositories;

import com.web.getcard.entities.Card;
import com.web.getcard.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CardRepository extends JpaRepository<Card, Integer> {
    Optional<Card> findByCode(String code);
    Optional<Card> findByUser(User user);

}

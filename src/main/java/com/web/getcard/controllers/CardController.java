package com.web.getcard.controllers;

import com.web.getcard.entities.Card;
import com.web.getcard.repositories.CardRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api/cards")
public class CardController {

    private final CardRepository cardRepository;

    public CardController(CardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }

    @GetMapping("/{code}/check")
    public String checkCard(@PathVariable String code) {
        Optional<Card> cardOpt = cardRepository.findByCode(code);

        if (cardOpt.isEmpty()) {
            return "Card not found";
        }

        Card card = cardOpt.get();
        if (card.getUser() == null) {
            return "Card available for registration";
        } else {
            return "Card already linked to a user";
        }
    }
}
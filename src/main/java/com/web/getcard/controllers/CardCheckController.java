package com.web.getcard.controllers;

import com.web.getcard.entities.Card;
import com.web.getcard.repositories.CardRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/check")
public class CardCheckController {

    private final CardRepository cardRepository;

    public CardCheckController(CardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }

    @GetMapping("/{cardCode}")
    public Map<String, Object> checkCardCode(@PathVariable String cardCode) {
        Optional<Card> cardOpt = cardRepository.findByCode(cardCode);

        if (cardOpt.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cartão não encontrado");
        }

        Card card = cardOpt.get();

        boolean associated = card.getUser() != null;

        return Map.of(
                "associated", associated,
                "cardCode", cardCode
        );
    }
}


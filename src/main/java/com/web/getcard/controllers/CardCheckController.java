package com.web.getcard.controllers;

import com.web.getcard.entities.Card;
import com.web.getcard.repositories.CardRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
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

    @GetMapping("/check-valcode")
    public boolean checkValCode(@RequestParam String code, @RequestParam String valCode) {
        return cardRepository.findByCode(code)
                .map(card -> valCode.equals(card.getValCode()) && card.getUser() == null)
                .orElse(false);
    }
}


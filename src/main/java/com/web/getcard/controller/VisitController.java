package com.web.getcard.controller;

import com.web.getcard.model.Card;
import com.web.getcard.model.Profile;
import com.web.getcard.service.CardService;
import com.web.getcard.service.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/visit")
@RequiredArgsConstructor
public class VisitController {

    private final CardService cardService;
    private final ProfileService profileService;

    @GetMapping("/{cardId}")
    public Profile visitProfile(@PathVariable String cardId) throws ExecutionException, InterruptedException {
        Card card = cardService.getCardById(cardId);
        if (card != null && card.getUid() != null) {
            return profileService.getProfileByUid(card.getUid());
        }
        return null;
    }
}

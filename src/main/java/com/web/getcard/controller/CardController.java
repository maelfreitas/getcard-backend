package com.web.getcard.controller;

import com.google.firebase.auth.FirebaseToken;
import com.web.getcard.model.Card;
import com.web.getcard.service.CardService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/card")
@RequiredArgsConstructor
public class CardController {

    private final CardService service;

    // Verificar se card está vinculado
    @GetMapping("/{cardId}")
    public Card getCard(@PathVariable String cardId) throws ExecutionException, InterruptedException {
        return service.getCardById(cardId);
    }

    // Linkar card ao usuário autenticado
    @PostMapping("/link/{cardId}")
    public String linkCard(@PathVariable String cardId, HttpServletRequest request) throws ExecutionException, InterruptedException {
        String uid = ((FirebaseToken) request.getAttribute("firebaseUser")).getUid();
        return service.linkCardToUser(cardId, uid);
    }
}


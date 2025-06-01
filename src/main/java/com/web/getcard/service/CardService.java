package com.web.getcard.service;

import com.web.getcard.model.Card;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.concurrent.ExecutionException;

@Service
public class CardService {

    private static final String COLLECTION = "cards";

    public Card getCardById(String cardId) throws ExecutionException, InterruptedException {
        Firestore db = FirestoreClient.getFirestore();
        DocumentReference docRef = db.collection(COLLECTION).document(cardId);
        DocumentSnapshot snapshot = docRef.get().get();
        if (snapshot.exists()) {
            Card card = snapshot.toObject(Card.class);
            assert card != null;
            card.setId(snapshot.getId());
            return card;
        }
        return null;
    }

    public String linkCardToUser(String cardId, String uid) throws ExecutionException, InterruptedException {
        Firestore db = FirestoreClient.getFirestore();
        DocumentReference docRef = db.collection(COLLECTION).document(cardId);

        DocumentSnapshot snapshot = docRef.get().get();
        if (!snapshot.exists()) {
            return "Card not found";
        }

        Card card = snapshot.toObject(Card.class);
        if (card.getUid() != null) {
            return "Card already linked";
        }

        card.setUid(uid);
        card.setActivated(true);
        card.setCreatedAt(Instant.now());

        ApiFuture<WriteResult> writeResult = docRef.set(card);
        writeResult.get();
        return "Card linked successfully";
    }

    public boolean isCardLinked(String cardId) throws ExecutionException, InterruptedException {
        Card card = getCardById(cardId);
        return card != null && card.getUid() != null;
    }
}



package com.web.getcard.service;


import com.web.getcard.model.Profile;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.stereotype.Service;
import java.util.concurrent.ExecutionException;

@Service
public class ProfileService {

    private static final String COLLECTION = "profiles";

    public Profile getProfileByQrOrNfc(String id) throws ExecutionException, InterruptedException {
        Firestore db = FirestoreClient.getFirestore();

        Query query = db.collection(COLLECTION)
                .whereEqualTo("nfcId", id)
                .limit(1);
        ApiFuture<QuerySnapshot> future = query.get();
        QuerySnapshot snapshot = future.get();

        if (snapshot.isEmpty()) {
            query = db.collection(COLLECTION)
                    .whereEqualTo("qrCodeId", id)
                    .limit(1);
            snapshot = (QuerySnapshot) query.get();
        }

        if (!snapshot.isEmpty()) {
            DocumentSnapshot doc = snapshot.getDocuments().get(0);
            Profile profile = doc.toObject(Profile.class);
            assert profile != null;
            profile.setId(doc.getId());
            return profile;
        }
        return null;
    }

    public String saveProfile(Profile profile) throws ExecutionException, InterruptedException {
        Firestore db = FirestoreClient.getFirestore();
        DocumentReference addedDocRef = db.collection(COLLECTION).document();
        profile.setId(addedDocRef.getId());
        ApiFuture<WriteResult> future = addedDocRef.set(profile);
        future.get();
        return addedDocRef.getId();
    }

    public Profile getProfileById(String id) throws ExecutionException, InterruptedException {
        Firestore db = FirestoreClient.getFirestore();
        DocumentReference docRef = db.collection(COLLECTION).document(id);
        DocumentSnapshot document = docRef.get().get();
        if (document.exists()) {
            Profile profile = document.toObject(Profile.class);
            assert profile != null;
            profile.setId(document.getId());
            return profile;
        }
        return null;
    }

    public String updateProfile(Profile profile) throws ExecutionException, InterruptedException {
        Firestore db = FirestoreClient.getFirestore();
        DocumentReference docRef = db.collection(COLLECTION).document(profile.getId());
        ApiFuture<WriteResult> future = docRef.set(profile);
        future.get();
        return profile.getId();
    }

    public Profile getProfileByUid(String uid) throws ExecutionException, InterruptedException {
        Firestore db = FirestoreClient.getFirestore();
        Query query = db.collection(COLLECTION)
                .whereEqualTo("uid", uid)
                .limit(1);

        QuerySnapshot snapshot = query.get().get();

        if (!snapshot.isEmpty()) {
            DocumentSnapshot doc = snapshot.getDocuments().get(0);
            Profile profile = doc.toObject(Profile.class);
            assert profile != null;
            profile.setId(doc.getId());
            return profile;
        }
        return null;
    }

}


package com.web.getcard.service;


import com.google.cloud.firestore.Firestore;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.UserRecord;
import com.google.firebase.cloud.FirestoreClient;
import com.web.getcard.model.User;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class UserService {

    public String createUser(User user) throws Exception {
        // Criar usuário no Firebase Authentication
        UserRecord.CreateRequest request = new UserRecord.CreateRequest()
                .setEmail(user.getEmail())
                .setPassword(user.getPassword());

        UserRecord userRecord = FirebaseAuth.getInstance().createUser(request);

        // Salvar dados no Firestore
        Firestore db = FirestoreClient.getFirestore();
        Map<String, Object> userData = new HashMap<>();
        userData.put("name", user.getName());
        userData.put("email", user.getEmail());
        userData.put("uid", userRecord.getUid());

        db.collection("users").document(userRecord.getUid()).set(userData);

        return userRecord.getUid();
    }
}


package com.web.getcard.services;

import com.web.getcard.entities.Card;
import com.web.getcard.entities.User;
import com.web.getcard.repositories.CardRepository;
import com.web.getcard.repositories.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private UserRepository userRepository;
    private CardRepository cardRepository;
    private PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, CardRepository cardRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.cardRepository = cardRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUserById(int id) {
        return userRepository.findById(id);
    }

    public User createUser(User user, String cardCode) {
        Optional<Card> cardOpt = cardRepository.findByCode(cardCode);
        Optional<User> existingUser = userRepository.findByEmail(user.getEmail());

        if (cardOpt.isEmpty()) {
            throw new RuntimeException("Card with code '" + cardCode + "' not found.");
        }

        Card card = cardOpt.get();
        if (card.getUser() != null) {
            throw new RuntimeException("Card already associated with another user.");
        }

        if (existingUser.isPresent()) {
            throw new RuntimeException("User already exists");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User savedUser = userRepository.save(user);


        card.setUser(savedUser);
        card.setActive(true);
        cardRepository.save(card);

        return savedUser;
    }

    public User updateUser(int id, User updatedUser ) {
        return userRepository.findById(id).map(user -> {
            user.setUsername(updatedUser.getUsername());
            user.setPassword(updatedUser.getPassword());
            user.setEmail(updatedUser.getEmail());
            return userRepository.save(user);
        }).orElseThrow(() -> new RuntimeException("Usuário não encontrado com ID: " + id));

    }

    public void deleteUser(int id) {
        userRepository.deleteById(id);
    }
}

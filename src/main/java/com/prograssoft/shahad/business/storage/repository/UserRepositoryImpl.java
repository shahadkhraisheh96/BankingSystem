package com.prograssoft.shahad.business.storage.repository;

import com.prograssoft.shahad.business.model.User;
import com.prograssoft.shahad.business.storage.repository.interfaces.UserRepository;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Optional;
public class UserRepositoryImpl implements UserRepository {
    private final HashMap<String, User> users = new HashMap<>();

    public User save(User user) {
        users.put(user.getId(), user);
        return user;
    }

    public Optional<User> findByEmailAndPassword(String email, String password) {
        return users.values().stream()
                .filter(u -> u.getEmail().equals(email) && u.getPassword().equals(password))
                .findFirst();
    }

    public User findByEmail(String email) {
        return users.values().stream()
                .filter(u -> u.getEmail().equals(email))
                .findFirst().orElseThrow();
    }

    public boolean existsByUsername(String username) {
        return users.values().stream()
                .anyMatch(u -> u.getUserName().equals(username));
    }

    @Override
    public User findByUsername(String username) {
        return users.values().stream()
                .filter(u -> u.getUserName().equals(username))
                .findFirst().orElseThrow();
    }
}

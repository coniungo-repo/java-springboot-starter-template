package com.coniungo.template.service;

import com.coniungo.template.dto.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final List<User> users = new ArrayList<>();

    // CREATE
    public User addUser(User user) {
        users.add(user);
        return user;
    }

    // READ ALL
    public List<User> getAllUsers() {
        return users;
    }

    // READ BY ID
    public Optional<User> getUserById(int id) {
        return users.stream()
                .filter(user -> user.getId() == id)
                .findFirst();
    }

    // UPDATE
    public boolean updateUser(int id, User newUser) {
        return getUserById(id).map(existingUser -> {
            users.remove(existingUser);
            users.add(newUser);
            return true;
        }).orElse(false);
    }

    // DELETE
    public boolean deleteUser(int id) {
        return users.removeIf(user -> user.getId() == id);
    }
}

package ru.kata.spring.boot_security.demo.service;

import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;

public interface UserService {
    List<User> allUsers();

    User getById(long id);

    User getUserByEmail(String email);

    void save(User user);

    void updateUser(User newUser);

    void deleteUser(long id);
}

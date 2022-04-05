package ru.kata.spring.boot_security.demo.service;

import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;

public interface UserService {
    List<User> allUsers();
    User getUserByEmail(String email);
    void save(User user);
    void updateUser(User newUser);
    void deleteUser(long id);
    User getById(Long id);

//    User implEditUser(Long id, String firstName, String lastName, String password, String[] roles, String email);
}

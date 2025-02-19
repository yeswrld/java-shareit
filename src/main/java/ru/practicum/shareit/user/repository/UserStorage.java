package ru.practicum.shareit.user.repository;

import ru.practicum.shareit.user.User;

import java.util.List;

public interface UserStorage {
    User addUser(User user);

    User updUser(Integer id, User updUser);

    User getUserById(Integer id);

    void delete(Integer id);

    List<User> getAll();
}

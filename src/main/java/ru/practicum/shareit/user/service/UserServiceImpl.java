package ru.practicum.shareit.user.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exception.DuplicatedEmailExcep;
import ru.practicum.shareit.exception.NotFoundExcep;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.repository.UserStorage;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserStorage userStorage;

    @Override
    public User addUser(User user) {
        if (emailCheck(user)) throw new DuplicatedEmailExcep("Данная почта уже используется");
        userStorage.addUser(user);
        return user;
    }

    @Override
    public User updUser(Integer id, User updUser) {
        if (updUser.getEmail() != null && emailCheck(updUser))
            throw new DuplicatedEmailExcep("Данная почта уже используется");
        return userStorage.updUser(id, updUser);
    }

    @Override
    public User getUserById(Integer id) {
        if (userStorage.getUserById(id) == null) throw new NotFoundExcep("Пользователь с ID = " + id + " не найден");
        return userStorage.getUserById(id);
    }

    @Override
    public void delete(Integer id) {
        userStorage.delete(id);
    }

    @Override
    public List<User> getAll() {
        return userStorage.getAll();
    }

    private boolean emailCheck(User user) {
        return getAll().stream().anyMatch(i -> user.getEmail().equals(i.getEmail()));
    }
}

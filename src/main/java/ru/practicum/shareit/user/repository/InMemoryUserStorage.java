package ru.practicum.shareit.user.repository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import ru.practicum.shareit.user.User;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Repository
public class InMemoryUserStorage implements UserStorage {
    private final Map<Integer, User> users = new HashMap<>();
    private Integer id = 1;

    @Override
    public User addUser(User user) {
        user.setId(id++);
        users.put(user.getId(), user);
        return user;
    }

    @Override
    public User updUser(Integer id, User updUser) {
        User oldUser = users.get(id);
        if (updUser.getName() != null && !updUser.getName().isBlank()) oldUser.setName(updUser.getName());
        if (updUser.getEmail() != null && !updUser.getEmail().isBlank()) oldUser.setEmail(updUser.getEmail());
        return oldUser;
    }

    @Override
    public User getUserById(Integer id) {
        return users.get(id);
    }

    @Override
    public void delete(Integer id) {
        log.info("Пользователь {} удален", getUserById(id).getName());
        users.remove(id);
    }

    @Override
    public List<User> getAll() {
        return users.values().stream().toList();
    }
}

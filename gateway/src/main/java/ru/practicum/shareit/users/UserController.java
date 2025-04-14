package ru.practicum.shareit.users;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.users.dto.UCreateDto;
import ru.practicum.shareit.users.dto.UUpdateDto;

@Slf4j
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserClient userClient;

    @GetMapping("/{id}")
    public ResponseEntity<Object> getUser(@PathVariable Integer id) {
        log.info("Выводим пользователя с ИД = {} ", id);
        return userClient.findById(id);
    }

    @GetMapping
    public ResponseEntity<Object> getAll() {
        log.info("Выводим всех пользователей");
        return userClient.getAllUsers();
    }

    @PostMapping
    public ResponseEntity<Object> addUser(@Valid @RequestBody UCreateDto uCreateDto) {
        log.info("Добавляем пользователя {}", uCreateDto);
        return userClient.addUser(uCreateDto);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Object> updUser(@Valid @RequestBody UUpdateDto uUpdateDto, @PathVariable Integer id) {
        log.info("Обновляем пользователя с ИД = {}, данные для обновления - {}", id, uUpdateDto);
        return userClient.updUser(uUpdateDto, id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteUser(@PathVariable Integer id) {
        log.info("Удаляем пользователя с ИД = {}", id);
        return userClient.deleteUser(id);
    }
}

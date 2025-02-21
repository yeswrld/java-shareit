package ru.practicum.shareit.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.service.UserService;
import ru.practicum.shareit.validations.Create;
import ru.practicum.shareit.validations.Update;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(path = "/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto addUser(@RequestBody @Validated(Create.class) User user) {
        log.info("Пользователь {} добавлен", user.getName());
        return UserMapper.INSTANCE.toUserDto(userService.addUser(user));
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public UserDto update(@RequestBody @Validated(Update.class) User user, @PathVariable Integer id) {
        log.info("Данные пользователя с ID = {} обновлены", id);
        return UserMapper.INSTANCE.toUserDto(userService.updUser(id, user));
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<UserDto> getAll() {
        log.info("Выводим всех пользователей");
        List<UserDto> users = UserMapper.INSTANCE.toUserDtoList(userService.getAll());
        return users;
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public UserDto getById(@PathVariable Integer id) {
        log.info("Данные пользователя с ID = {} выведены", id);
        return UserMapper.INSTANCE.toUserDto(userService.getUserById(id));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        userService.delete(id);
    }

}

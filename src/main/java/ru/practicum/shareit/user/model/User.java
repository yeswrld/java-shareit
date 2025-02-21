package ru.practicum.shareit.user.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.practicum.shareit.validations.Create;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private Integer id;

    @NotNull(groups = Create.class, message = "Имя не может быть null")
    private String name;

    @Email(groups = Create.class, message = "Не верный формат почты")
    @NotNull(groups = Create.class, message = "Почта не может быть null")
    private String email;
}

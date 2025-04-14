package ru.practicum.shareit.users.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UCreateDto {
    @NotNull(message = "Имя не может быть null")
    String name;
    @Email(message = "Не верный формат почты")
    @NotNull(message = "Почта не может быть null")
    String email;
}

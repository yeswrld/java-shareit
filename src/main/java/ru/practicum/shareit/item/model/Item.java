package ru.practicum.shareit.item.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.validations.Create;

@Getter
@Setter
@RequiredArgsConstructor
public class Item {
    private Integer id;
    @NotBlank(groups = Create.class, message = "Имя не может быть пустым")
    private String name;
    @NotNull(groups = Create.class, message = "Описание не может быть null")
    private String description;
    @NotNull(groups = Create.class, message = "Available не может быть null")
    private Boolean available;
    private User owner;
}

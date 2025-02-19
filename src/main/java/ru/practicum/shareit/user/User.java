package ru.practicum.shareit.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import lombok.AllArgsConstructor;
import lombok.Data;
import ru.practicum.shareit.validations.Create;
import ru.practicum.shareit.validations.Update;

@Data
@AllArgsConstructor
public class User {
    private Integer id;
    private String name;
    @NotNull(groups = {Create.class})
    @Email(groups = {Create.class})
    private String email;
}

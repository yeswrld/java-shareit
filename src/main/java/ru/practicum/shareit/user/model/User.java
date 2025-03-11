package ru.practicum.shareit.user.model;

import jakarta.persistence.*;
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
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Integer id;

    @NotNull(groups = Create.class, message = "Имя не может быть null")
    @Column(name = "user_name")
    private String name;

    @Email(groups = Create.class, message = "Не верный формат почты")
    @NotNull(groups = Create.class, message = "Почта не может быть null")
    @Column(name = "email")
    private String email;

}

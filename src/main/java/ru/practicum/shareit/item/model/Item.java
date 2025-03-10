package ru.practicum.shareit.item.model;

import jakarta.persistence.*;
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
@Entity
@Table(name = "items")
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_id")
    private Integer id;
    @Column(name = "item_name")
    @NotBlank(groups = Create.class, message = "Имя не может быть пустым")
    private String name;
    @Column(name = "item_description")
    @NotNull(groups = Create.class, message = "Описание не может быть null")
    private String description;
    @Column(name = "item_available")
    @NotNull(groups = Create.class, message = "Available не может быть null")
    private Boolean available;
    @ManyToOne
    @JoinColumn(name = "owner_id")
    private User owner;
}

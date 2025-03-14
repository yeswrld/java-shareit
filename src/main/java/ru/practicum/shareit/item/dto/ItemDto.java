package ru.practicum.shareit.item.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.practicum.shareit.booking.dto.BookingLastNextDto;
import ru.practicum.shareit.user.UserDto;
import ru.practicum.shareit.validations.Create;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ItemDto {
    private Integer id;
    @NotBlank(groups = Create.class, message = "Имя не может быть пустым")
    private String name;
    @NotNull(groups = Create.class, message = "Описание не может быть null")
    private String description;
    @NotNull(groups = Create.class, message = "Available не может быть null")
    private Boolean available;
    private UserDto owner;
    private List<CommentDto> comments;
    private BookingLastNextDto lastBooking;
    private BookingLastNextDto nextBooking;
}

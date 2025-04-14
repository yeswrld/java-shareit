package ru.practicum.shareit.items.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CCreateDto {
    @NotBlank(message = "Коммент не должен быть пустым")
    private String text;
}

package ru.practicum.shareit.items.dto;

import lombok.Data;

@Data
public class IUpdateDto {
    private String name;
    private String description;
    private Boolean available;
}

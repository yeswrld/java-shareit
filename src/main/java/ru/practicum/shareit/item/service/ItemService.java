package ru.practicum.shareit.item.service;

import ru.practicum.shareit.item.dto.ItemDto;

import java.util.List;

public interface ItemService {
    ItemDto addNewItem(Integer userId, ItemDto itemDto);
    ItemDto updateItem (Integer userId, Integer itemId, ItemDto itemDto);
    ItemDto getByUserId (Integer userId, Integer itemId);
    ItemDto getByItemId (Integer itemId);
    List<ItemDto> getAllByUserId (Integer userId);
    List<ItemDto> searchByText (String text);
}

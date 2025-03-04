package ru.practicum.shareit.item.repository;

import ru.practicum.shareit.item.model.Item;

import java.util.List;
import java.util.Optional;

public interface ItemStorage {
    Item addNewItem(Item item);

    Item updateItem(Integer itemId, Item item);

    Item getByUserId(Integer userId, Integer itemId);

    Optional<Item> getByItemId(Integer itemId);

    List<Item> getAllByUserId(Integer userId);

    List<Item> searchByText(String text);
}

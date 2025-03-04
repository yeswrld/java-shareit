package ru.practicum.shareit.item.repository;

import org.springframework.stereotype.Repository;
import ru.practicum.shareit.item.model.Item;

import java.util.*;

@Repository
public class InMemoryItemStorage implements ItemStorage {
    private final Map<Integer, Item> items = new HashMap<>();
    private final Map<Integer, List<Item>> userItems = new HashMap<>();
    private Integer itemId = 1;

    @Override
    public Item addNewItem(Item item) {
        item.setId(itemId++);
        items.put(item.getId(), item);
        Integer userId = item.getOwner().getId();
        if (userItems.containsKey(userId)) {
            userItems.get(userId).add(item);
        } else {
            List<Item> uItems = new ArrayList<>();
            uItems.add(item);
            userItems.put(userId, uItems);
        }
        return item;
    }

    @Override
    public Item updateItem(Integer itemId, Item item) {
        Item oldItem = items.get(itemId);
        oldItem.setName(item.getName());
        oldItem.setDescription(item.getDescription());
        oldItem.setAvailable(item.getAvailable());
        return oldItem;
    }

    @Override
    public Item getByUserId(Integer userId, Integer itemId) {
        if (userItems.get(userId) == null) return null;
        return userItems.get(userId)
                .stream()
                .filter(item -> itemId.equals(item.getId()))
                .findFirst().get();
    }

    @Override
    public Optional<Item> getByItemId(Integer itemId) {
        return Optional.of(items.get(itemId));
    }

    @Override
    public List<Item> getAllByUserId(Integer userId) {
        return userItems.get(userId);
    }

    @Override
    public List<Item> searchByText(String text) {
        if (!text.isEmpty()) {
            return items.values().stream()
                    .filter(item -> item.getName() != null || item.getDescription() != null)
                    .filter(item -> (item.getName() != null && item.getName().toLowerCase().contains(text.toLowerCase())) ||
                            (item.getDescription() != null && item.getDescription().toLowerCase().contains(text.toLowerCase())))
                    .filter(Item::getAvailable)
                    .toList();
        }
        return Collections.emptyList();
    }
}


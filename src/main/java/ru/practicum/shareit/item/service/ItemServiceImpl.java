package ru.practicum.shareit.item.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exception.NotFoundExcep;
import ru.practicum.shareit.item.ItemMapper;
import ru.practicum.shareit.item.ItemDto;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.repository.ItemStorage;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.repository.UserStorage;
import ru.practicum.shareit.user.service.UserService;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {
    private final ItemStorage itemStorage;
    private final ItemMapper itemMapper;
    private final UserStorage userStorage;
    private final UserService userService;

    @Override
    public ItemDto addNewItem(Integer userId, ItemDto itemDto) {
        User user = userStorage.getUserById(userId);
        Item item = itemMapper.toItem(itemDto);
        item.setOwner(user);
        return itemMapper.toItemDto(itemStorage.addNewItem(item));
    }

    @Override
    public ItemDto updateItem(Integer userId, Integer itemId, ItemDto itemDto) {
        User user = userService.getUserById(userId);
        Item item = itemMapper.toItem(itemDto);
        return itemMapper.toItemDto(itemStorage.updateItem(itemId, item));
    }

    @Override
    public ItemDto getByUserId(Integer userId, Integer itemId) {
        return null;
    }

    @Override
    public ItemDto getByItemId(Integer itemId) {
        return itemMapper.toItemDto(itemStorage.getByItemId(itemId).orElseThrow(() -> new NotFoundExcep("Вещь не найдена")));
    }

    @Override
    public List<ItemDto> getAllByUserId(Integer userId) {
        return itemMapper.toItemDtoList(itemStorage.getAllByUserId(userId));
    }

    @Override
    public List<ItemDto> searchByText(String text) {
        return itemMapper.toItemDtoList(itemStorage.searchByText(text));
    }
}

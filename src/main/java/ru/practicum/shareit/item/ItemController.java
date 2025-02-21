package ru.practicum.shareit.item;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.item.service.ItemService;
import ru.practicum.shareit.validations.Create;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/items")
@RequiredArgsConstructor
public class ItemController {
    private final ItemService itemService;
    private final ItemMapper itemMapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ItemDto addItem(@RequestHeader("X-Sharer-User-Id") Integer userId, @RequestBody @Validated(Create.class) ItemDto itemDto) {
        log.info("Предмет c именем {} добавлен", itemDto.getName());
        return itemService.addNewItem(userId, itemDto);
    }

    @PatchMapping("/{itemDtoId}")
    @ResponseStatus(HttpStatus.OK)
    public ItemDto updateItem(@RequestHeader("X-Sharer-User-Id") Integer userId,
                              @PathVariable Integer itemDtoId,
                              @RequestBody ItemDto itemDto) {
        log.info("Предмет c именем {} обновлен", itemDto.getName());
        return itemService.updateItem(userId, itemDtoId, itemDto);
    }

    @GetMapping("/{itemDtoId}")
    @ResponseStatus(HttpStatus.OK)
    public ItemDto getItemByItemId(@PathVariable Integer itemDtoId) {
        log.info("Выводим предмет c ИД = {} ", itemDtoId);
        return itemService.getByItemId(itemDtoId);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ItemDto> getItemsByUserId(@RequestHeader("X-Sharer-User-Id") Integer userId) {
        log.info("Выводим предметы пользователя c ИД = {} ", userId);
        return itemService.getAllByUserId(userId);
    }

    @GetMapping("/search")
    @ResponseStatus(HttpStatus.OK)
    public List<ItemDto> searchItemByText(@RequestParam String text) {
        log.info("Ищем предмет с {} в описании", text);
        return itemService.searchByText(text);
    }
}

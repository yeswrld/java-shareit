package ru.practicum.shareit.item;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.service.ItemService;
import ru.practicum.shareit.validations.Create;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/items")
@RequiredArgsConstructor
public class ItemController {
    private final ItemService itemService;
    private static final String USERID_HEADER = "X-Sharer-User-Id";

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ItemDto addItem(@RequestHeader(USERID_HEADER) Integer userId, @RequestBody @Validated(Create.class) ItemDto itemDto) {
        log.info("Предмет c именем {} добавлен", itemDto.getName());
        return itemService.addNewItem(userId, itemDto);
    }

    @PatchMapping("/{itemDtoId}")
    public ItemDto updateItem(@RequestHeader(USERID_HEADER) Integer userId,
                              @PathVariable Integer itemDtoId,
                              @RequestBody ItemDto itemDto) {
        log.info("Предмет c именем {} обновлен", itemDto.getName());
        return itemService.updateItem(userId, itemDtoId, itemDto);
    }

    @GetMapping("/{itemDtoId}")
    public ItemDto getItemByItemId(@RequestHeader(USERID_HEADER) Integer userId,
                                   @PathVariable Integer itemDtoId) {
        log.info("Выводим предмет c ИД = {} ", itemDtoId);
        return itemService.getByItemId(userId, itemDtoId);
    }

    @GetMapping
    public List<ItemDto> getItemsByUserId(@RequestHeader(USERID_HEADER) Integer userId) {
        log.info("Выводим предметы пользователя c ИД = {} ", userId);
        return itemService.findAllByOwnerId(userId);
    }

    @GetMapping("/search")
    public List<ItemDto> searchItemByText(@RequestParam String text) {
        log.info("Ищем предмет с {} в описании", text);
        return itemService.searchByText(text);
    }

    @PostMapping("/{itemId}/comment")
    @ResponseStatus(HttpStatus.CREATED)
    public CommentDto addComment(@RequestHeader(USERID_HEADER) Integer userId, @PathVariable Integer itemId, @RequestBody CommentDto commentDto) {
        log.info("Добавляем коммент к предмету с ИД = {} от пользователя c ИД = {}", itemId, userId);
        return itemService.addComment(commentDto, userId, itemId);
    }
}

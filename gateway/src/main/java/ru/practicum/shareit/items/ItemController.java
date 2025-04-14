package ru.practicum.shareit.items;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.items.dto.CCreateDto;
import ru.practicum.shareit.items.dto.ICreateDto;
import ru.practicum.shareit.items.dto.IUpdateDto;

@Slf4j
@Controller
@RequestMapping("/items")
@RequiredArgsConstructor
public class ItemController {
    private final ItemClient itemClient;
    private static final String USERID_HEADER = "X-Sharer-User-Id";

    @GetMapping("/{itemId}")
    public ResponseEntity<Object> getItem(@RequestHeader(USERID_HEADER) Integer userId, @PathVariable(required = false) Integer itemId) {
        log.info("Выводим предмет с ИД = {}", itemId);
        return itemClient.findById(itemId, userId);
    }

    @GetMapping
    public ResponseEntity<Object> getItemByUserId(@RequestHeader(USERID_HEADER) Integer userId) {
        log.info("Выводим предмет пользователя с ИД = {}", userId);
        return itemClient.findByUserId(userId);
    }

    @GetMapping("/search")
    public ResponseEntity<Object> searchByText(@RequestParam String text,
                                               @RequestHeader(USERID_HEADER) Long userId) {
        log.info("Ищем предмет с описанием {}", text);
        return itemClient.searchByText(text, userId);
    }

    @PostMapping
    public ResponseEntity<Object> addItem(@RequestHeader(USERID_HEADER) Integer userId, @Valid @RequestBody ICreateDto itemDto) {
        log.info("Добавляем предмет {} от пользователя c ИД = {}", itemDto, userId);
        return itemClient.addItem(userId, itemDto);
    }

    @PostMapping("/{itemId}/comment")
    public ResponseEntity<Object> addComment(@RequestHeader(USERID_HEADER) Integer userId, @Valid @RequestBody CCreateDto cCreateDto,
                                             @PathVariable Integer itemId) {
        log.info("Добавляем комментарий {} от пользователя с ИД = {} к предмету с ИД = {}", cCreateDto, userId, itemId);
        return itemClient.addComment(userId, cCreateDto, itemId);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Object> updItem(@RequestHeader(USERID_HEADER) Integer userId, @Valid @RequestBody IUpdateDto iUpdateDto,
                                          @PathVariable Integer id) {
        log.info("Обновляем предмет с ИД = {} пользователя с ИД = {}", id, userId);
        return itemClient.updItem(userId, iUpdateDto, id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteItem(@PathVariable Integer id) {
        log.info("Удаляем пользователя с ИД = {}", id);
        return itemClient.deleteItem(id);
    }
}

package ru.practicum.shareit.item;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/items")
@RequiredArgsConstructor
public class ItemController {

//    @PostMapping
//    @ResponseStatus(HttpStatus.CREATED)
//    public ItemDto addItem(@RequestHeader("X-Sharer-User-Id") Integer userId, @RequestBody ItemDto itemDto) {
//        return new ItemDto();
//    }
}

package ru.practicum.shareit.items;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.util.DefaultUriBuilderFactory;
import ru.practicum.shareit.client.BaseClient;
import ru.practicum.shareit.items.dto.CCreateDto;
import ru.practicum.shareit.items.dto.ICreateDto;
import ru.practicum.shareit.items.dto.IUpdateDto;

import java.util.Map;

@Service
public class ItemClient extends BaseClient {
    private static final String API_PREFIX = "/items";

    @Autowired
    public ItemClient(@Value("${shareit-server.url}") String url, RestTemplateBuilder builder) {
        super(
                builder
                        .uriTemplateHandler(new DefaultUriBuilderFactory(url + API_PREFIX))
                        .requestFactory(() -> new HttpComponentsClientHttpRequestFactory())
                        .build()
        );
    }

    public ResponseEntity<Object> findById(Integer itemId, Integer userId) {
        return get("/" + itemId, userId);
    }

    public ResponseEntity<Object> findByUserId(Integer userId) {
        return get("", userId);
    }

    public ResponseEntity<Object> getAllItems() {
        return getAll("");
    }

    public ResponseEntity<Object> searchByText(String text, Long userId) {
        Map<String, Object> param = Map.of("text", text);
        return get("/search?text={text}", userId, param);
    }

    public ResponseEntity<Object> addItem(Integer userId, ICreateDto iCreateDto) {
        return post("", userId, iCreateDto);
    }

    public ResponseEntity<Object> addComment(Integer userId, CCreateDto cCreateDto, Integer itemId) {
        return post("/" + itemId + "/comment", userId, cCreateDto);
    }

    public ResponseEntity<Object> updItem(Integer userId, IUpdateDto iUpdateDto, Integer id) {
        return patch("/" + id, userId, iUpdateDto);
    }

    public ResponseEntity<Object> deleteItem(Integer id) {
        return delete("/" + id, id);
    }
}

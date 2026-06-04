package com.example.aibackend.controller;

import com.example.aibackend.domain.Item;
import com.example.aibackend.dto.ItemRequest;
import com.example.aibackend.dto.ItemResponse;
import com.example.aibackend.error.NotFoundException;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@RestController
@RequestMapping("/legacy/items")
public class ItemController {
    private final Map<Long, Item> storage = new ConcurrentHashMap<>();
    private final AtomicLong sequence = new AtomicLong(1);

    @GetMapping
    public List<ItemResponse> list() {
        return storage.values().stream().map(ItemResponse::from).toList();
    }

    @GetMapping("/{id}")
    public ItemResponse get(@PathVariable Long id) {
        Item item = storage.get(id);
        if (item == null) {
                throw NotFoundException.of("item", id);
        }
        return ItemResponse.from(item);
    }

    @PostMapping
    public ResponseEntity<ItemResponse> create(@Valid @RequestBody ItemRequest req) {
        long id = sequence.getAndIncrement();
        Item saved = Item.builder().id(id).name(req.name()).price(req.price()).build();
        storage.put(id, saved);
        return ResponseEntity.created(URI.create("/legacy/items/" + id)).body(ItemResponse.from(saved));
    }

    @PutMapping("/{id}")
    public ItemResponse update(@PathVariable Long id, @Valid @RequestBody ItemRequest req) {
        // bring existing id item
        Item existing = storage.get(id);
        if (existing == null) {
            throw NotFoundException.of("item", id);
        }
        // edit the id item's info
        existing.setName(req.name());
        existing.setPrice(req.price());
        return ItemResponse.from(existing);
    }



}

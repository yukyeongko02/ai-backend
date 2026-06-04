package com.example.aibackend.controller;

import com.example.aibackend.domain.todoList;
import com.example.aibackend.dto.todoRequest;
import com.example.aibackend.dto.todoResponse;
import com.example.aibackend.dto.todoUpdateRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@RestController
@RequestMapping("/legacy/todos")
public class todoController {
    private final Map<Long, todoList> storage = new ConcurrentHashMap<>();
    private final AtomicLong sequence = new AtomicLong(1);

    @GetMapping
    public List<todoResponse> list(){
        return storage.values()
                .stream()
                .map(todoResponse::from)
                .toList();
    }

    @GetMapping("/{id}")
    public todoResponse get(@PathVariable Long id){
        todoList todo = storage.get(id);

        return todoResponse.from(todo);
    }

    @PostMapping
    public ResponseEntity<todoResponse> create(
            @Valid @RequestBody todoRequest req
    ){
        long id = sequence.getAndIncrement();

        todoList saved = todoList.builder()
                .id(id)
                .title((req.title()))
                .description((req.description()))
                .complete(false)
                .createdAt(LocalDateTime.now())
                .build();
        storage.put(id, saved);
        return ResponseEntity
                .created(URI.create("/legacy/todos" + id))
                .body(todoResponse.from(saved));
    }

    @PutMapping("/{id}")
    public todoResponse update(
            @PathVariable Long id,
            @Valid @RequestBody todoUpdateRequest req
    ){
        todoList existing = storage.get(id);

        existing.setTitle(req.title());
        existing.setDescription(req.description());
        existing.setComplete(req.complete());

        return todoResponse.from(existing);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        return ResponseEntity.noContent().build();
    }

}

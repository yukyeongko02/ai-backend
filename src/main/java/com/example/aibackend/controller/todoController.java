package com.example.aibackend.controller;


import com.example.aibackend.dto.todoRequest;
import com.example.aibackend.dto.todoResponse;
import com.example.aibackend.dto.todoUpdateRequest;
import com.example.aibackend.service.todoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/legacy/todos")
public class todoController {

    private final todoService todoService;

    @GetMapping
    public List<todoResponse> list() {
        return todoService.list();
    }

    @GetMapping("/{id}")
    public todoResponse get(@PathVariable Long id) {
        return todoService.get(id);
    }

    @PostMapping
    public ResponseEntity<todoResponse> create(@Valid @RequestBody todoRequest req) {
        todoResponse response = todoService.create(req);

        return ResponseEntity
                .created(URI.create("/legacy/todos/" + response.id()))
                .body(response);
    }

    @PutMapping("/{id}")
    public todoResponse update(
            @PathVariable Long id,
            @Valid @RequestBody todoUpdateRequest req
    ) {
        return todoService.update(id, req);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        todoService.delete(id);

        return ResponseEntity.noContent().build();
    }

}

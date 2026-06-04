package com.example.aibackend.service;

import com.example.aibackend.domain.todoList;
import com.example.aibackend.dto.todoRequest;
import com.example.aibackend.dto.todoResponse;
import com.example.aibackend.dto.todoUpdateRequest;
import com.example.aibackend.error.NotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class todoService {

    private final Map<Long, todoList> storage = new ConcurrentHashMap<>();
    private final AtomicLong sequence = new AtomicLong(1);

    public List<todoResponse> list(){
        return storage.values()
                .stream()
                .map(todoResponse::from)
                .toList();
    }

    //GET full list
    public todoResponse get(Long id){
        todoList todo = storage.get(id);

        if(todo == null){
            throw NotFoundException.of("todo", id);
        }

        return todoResponse.from(todo);
    }

    // POST todo_list
    public todoResponse create(todoRequest req){
        long id = sequence.getAndIncrement();

        todoList saved = todoList.builder()
                .id(id)
                .title(req.title())
                .description(req.description())
                .complete(false)
                .createdAt(LocalDateTime.now())
                .build();
        storage.put(id, saved);
        return todoResponse.from(saved);
    }

    // PUT TODO_LIST UPDATE
    public todoResponse update(Long id, todoUpdateRequest req){
        todoList existing = storage.get(id);

        if(existing == null){
            throw NotFoundException.of("todo", id);
        }

        existing.setTitle(req.title());
        existing.setDescription(req.description());
        existing.setComplete(req.complete());
        return todoResponse.from(existing);
    }

    //DELETE
    public void delete(Long id){
        if(storage.remove(id) == null){
            throw NotFoundException.of("todo", id);
        }
    }
}

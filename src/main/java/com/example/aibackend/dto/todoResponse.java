package com.example.aibackend.dto;

import com.example.aibackend.domain.todoList;

import java.time.LocalDateTime;

public record todoResponse(
        Long id,
        String title,
        String description,
        boolean complete,
        LocalDateTime createAt
) {
    public static todoResponse from(todoList todo){
        return new todoResponse(
                todo.getId(),
                todo.getTitle(),
                todo.getDescription(),
                todo.getComplete(),
                todo.getCreatedAt()
        );
    }
}

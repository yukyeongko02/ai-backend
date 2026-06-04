package com.example.aibackend.dto;

import jakarta.validation.constraints.NotBlank;

public record todoRequest(
        @NotBlank String title,
        String description
        ) {
}

/* 요청 예시*/
//{
//        "title": "Spring CRUD 연습",
//        "description": "TodoList API 만들기"
//        }
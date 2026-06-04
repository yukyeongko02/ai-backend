package com.example.aibackend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record todoUpdateRequest(
        @NotBlank String title,
        String description,
        @NotNull Boolean complete
        ) {
}
/*요청 예시*/

/*
* {
  "title": "Spring CRUD 복습",
  "description": "Controller, Service, DTO 구조 복습",
  "completed": true
}
* */
package com.example.aibackend.domain;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class todoList {
    private Long id;
    private String title;
    private String description;
    private Boolean complete;
    private LocalDateTime createdAt;

}

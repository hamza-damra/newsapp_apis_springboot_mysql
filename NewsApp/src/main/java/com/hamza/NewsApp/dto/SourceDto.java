package com.hamza.NewsApp.dto;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SourceDto {
    private Long id;
    private String name;
    private String description;
    private String url;
    private String category;
    private String author;
    private LocalDateTime publishDate = LocalDateTime.now();
}

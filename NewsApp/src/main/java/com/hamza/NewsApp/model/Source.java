package com.hamza.NewsApp.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "source")
public class Source {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "url")
    private String url;
    @Column(name = "category")
    private String category;

    @Column(name = "author")
    private String author;

    @Column(name = "publish_date")
    private LocalDateTime publishDate = LocalDateTime.now();

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;



}

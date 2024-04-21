package com.hamza.NewsApp.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
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

    private String name;

    private String description;

    private String url;

    private String category;

    private String author;

    @Column(name = "publish_date")
    private LocalDate publishDate;

    @PrePersist
    protected void onCreate() {
        publishDate = LocalDate.now();
    }

}

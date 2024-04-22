package com.hamza.NewsApp.controller;

import com.hamza.NewsApp.dto.SourceDto;
import com.hamza.NewsApp.service.NewsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sources")
public class NewsController {

    private final NewsService newsService;

    public NewsController(NewsService newsService) {
        this.newsService = newsService;
    }

    @GetMapping("/getAll")
    public List<SourceDto> getAllSources() {
        return newsService.getAllSources();
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<List<SourceDto>> getSourcesByCategory(@PathVariable String category) {
        List<SourceDto> sources = newsService.getSourcesByCategory(category);
        if (sources.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(sources);
    }

    @GetMapping("/search")
    public ResponseEntity<List<SourceDto>> searchSources(@RequestParam String query) {
        List<SourceDto> sources = newsService.searchSources(query);
        if (sources.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(sources);
    }

    @PostMapping("/addSource")
    public ResponseEntity<SourceDto> addSource(@RequestBody SourceDto sourceDTO) {
        SourceDto savedSource = newsService.saveSource(sourceDTO);
        return ResponseEntity.status(201).body(savedSource);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SourceDto> getSourceById(@PathVariable Long id) {
        SourceDto sourceDTO = newsService.getSourceById(id);
        if (sourceDTO == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(sourceDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteSource(@PathVariable Long id) {
        if (!newsService.sourceExists(id)) {
            return ResponseEntity.notFound().build();
        }
        newsService.deleteSource(id);
        return ResponseEntity.ok().build();
    }
}

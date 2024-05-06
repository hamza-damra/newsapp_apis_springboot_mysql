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
        return newsService.getAllNews();
    }

    @GetMapping("/get-by-category/{category}")
    public ResponseEntity<List<SourceDto>> getNewsByCategory(@PathVariable String category) {
        List<SourceDto> sources = newsService.getNewsByCategory(category);
        if (sources.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(sources);
    }

    @GetMapping("/search")
    public ResponseEntity<List<SourceDto>> searchNews(@RequestParam String query) {
        List<SourceDto> sources = newsService.searchNews(query);
        if (sources.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(sources);
    }

    @PostMapping("/addSource")
    public ResponseEntity<SourceDto> addNews(@RequestBody SourceDto sourceDTO) {
        SourceDto savedSource = newsService.addNews(sourceDTO);
        return ResponseEntity.status(201).body(savedSource);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<SourceDto> getNewsById(@PathVariable Long id) {
        SourceDto sourceDTO = newsService.getNewsById(id);
        if (sourceDTO == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(sourceDTO);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteNews(@PathVariable Long id) {
        newsService.deleteNews(id);
        return ResponseEntity.ok("News deleted successfully");
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<SourceDto> updateNews(@PathVariable Long id, @RequestBody SourceDto sourceDTO) {
        SourceDto updatedSource = newsService.updateNews(id, sourceDTO);
        return ResponseEntity.ok(updatedSource);
    }
}

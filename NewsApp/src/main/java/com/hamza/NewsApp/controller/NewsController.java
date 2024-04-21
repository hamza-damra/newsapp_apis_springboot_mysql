package com.hamza.NewsApp.controller;

import com.hamza.NewsApp.dto.SourceDTO;
import com.hamza.NewsApp.service.NewsService;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/sources")
public class NewsController {

    private final NewsService newsService;
    private final ModelMapper modelMapper;

    public NewsController(NewsService newsService, ModelMapper modelMapper) {
        this.newsService = newsService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/getAll")
    public List<SourceDTO> getAllSources() {
        return newsService.getAllSources().stream()
                .map(source -> modelMapper.map(source, SourceDTO.class))
                .collect(Collectors.toList());
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<List<SourceDTO>> getSourcesByCategory(@PathVariable String category) {
        List<SourceDTO> sources = newsService.getSourcesByCategory(category);
        if (sources.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(sources);
    }

    @GetMapping("/search")
    public ResponseEntity<List<SourceDTO>> searchSources(@RequestParam String query) {
        List<SourceDTO> sources = newsService.searchSources(query);
        if (sources.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(sources);
    }

    @PostMapping("/addSource")
    public ResponseEntity<SourceDTO> addSource(@RequestBody SourceDTO sourceDTO) {
        SourceDTO savedSource = newsService.saveSource(sourceDTO);
        return ResponseEntity.status(201).body(savedSource);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SourceDTO> getSourceById(@PathVariable Long id) {
        SourceDTO sourceDTO = newsService.getSourceById(id);
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

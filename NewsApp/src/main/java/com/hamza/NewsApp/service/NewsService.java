package com.hamza.NewsApp.service;

import com.hamza.NewsApp.dto.SourceDto;
import com.hamza.NewsApp.model.Source;
import com.hamza.NewsApp.repository.SourceRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class NewsService {

    private final SourceRepository sourceRepository;
    private final ModelMapper modelMapper;

    public NewsService(SourceRepository sourceRepository, ModelMapper modelMapper) {
        this.sourceRepository = sourceRepository;
        this.modelMapper = modelMapper;
    }

    public List<SourceDto> getAllSources() {
        return sourceRepository.findAll().stream()
                .map(source -> modelMapper.map(source, SourceDto.class))
                .collect(Collectors.toList());
    }

    public List<SourceDto> getSourcesByCategory(String category) {
        return sourceRepository.findByCategory(category).stream()
                .map(source -> modelMapper.map(source, SourceDto.class))
                .collect(Collectors.toList());
    }

    public List<SourceDto> searchSources(String query) {
        return sourceRepository.findByTitleOrDescription(query).stream()
                .map(source -> modelMapper.map(source, SourceDto.class))
                .collect(Collectors.toList());
    }

    public SourceDto saveSource(SourceDto sourceDTO) {
        Source source = modelMapper.map(sourceDTO, Source.class);
        source = sourceRepository.save(source);
        return modelMapper.map(source, SourceDto.class);
    }

    public SourceDto getSourceById(Long id) {
        return sourceRepository.findById(id).map(source -> modelMapper.map(source, SourceDto.class)).orElse(null);
    }

    public boolean sourceExists(Long id) {
        return sourceRepository.existsById(id);
    }

    public void deleteSource(Long id) {
        sourceRepository.deleteById(id);
    }
}

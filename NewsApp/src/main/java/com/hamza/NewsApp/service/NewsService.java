package com.hamza.NewsApp.service;

import com.hamza.NewsApp.dto.SourceDTO;
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

    public List<SourceDTO> getAllSources() {
        return sourceRepository.findAll().stream()
                .map(source -> modelMapper.map(source, SourceDTO.class))
                .collect(Collectors.toList());
    }

    public List<SourceDTO> getSourcesByCategory(String category) {
        return sourceRepository.findByCategory(category).stream()
                .map(source -> modelMapper.map(source, SourceDTO.class))
                .collect(Collectors.toList());
    }

    public List<SourceDTO> searchSources(String query) {
        return sourceRepository.findByDescriptionOrNameContainingIgnoreCase(query).stream()
                .map(source -> modelMapper.map(source, SourceDTO.class))
                .collect(Collectors.toList());
    }

    public SourceDTO saveSource(SourceDTO sourceDTO) {
        Source source = modelMapper.map(sourceDTO, Source.class);
        source = sourceRepository.save(source);
        return modelMapper.map(source, SourceDTO.class);
    }

    public SourceDTO getSourceById(Long id) {
        Source source = sourceRepository.findById(id).orElse(null);
        if (source != null) {
            return modelMapper.map(source, SourceDTO.class);
        }
        return null;
    }

    public boolean sourceExists(Long id) {
        return sourceRepository.existsById(id);
    }

    public void deleteSource(Long id) {
        sourceRepository.deleteById(id);
    }
}

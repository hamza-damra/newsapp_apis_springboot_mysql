package com.hamza.NewsApp.service;

import com.hamza.NewsApp.dto.SourceDto;
import com.hamza.NewsApp.exception.CategoryNotFoundException;
import com.hamza.NewsApp.exception.NewsNotFoundException;
import com.hamza.NewsApp.model.Source;
import com.hamza.NewsApp.repository.SourceRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class NewsService {

    private final SourceRepository sourceRepository;
    private final ModelMapper modelMapper;

    public NewsService(SourceRepository sourceRepository, ModelMapper modelMapper) {
        this.sourceRepository = sourceRepository;
        this.modelMapper = modelMapper;
    }

    public List<SourceDto> getAllNews() {
        return sourceRepository.findAll().stream()
                .map(source -> modelMapper.map(source, SourceDto.class))
                .collect(Collectors.toList());
    }

    public List<SourceDto> getNewsByCategory(String category) {

        if(!sourceRepository.existsByCategory(category)) {
            throw new CategoryNotFoundException(category);
        }
        return sourceRepository.findByCategory(category).stream()
                .map(source -> modelMapper.map(source, SourceDto.class))
                .collect(Collectors.toList());
    }

    public List<SourceDto> searchNews(String query) {
        return sourceRepository.findByTitleOrDescription(query).stream()
                .map(source -> modelMapper.map(source, SourceDto.class))
                .collect(Collectors.toList());
    }

    public SourceDto addNews(SourceDto sourceDTO) {
        Source source = modelMapper.map(sourceDTO, Source.class);
        source = sourceRepository.save(source);
        return modelMapper.map(source, SourceDto.class);
    }

    public SourceDto getNewsById(Long id) {
        if(!sourceRepository.existsById(id)) {
            throw new NewsNotFoundException(String.valueOf(id));
        }
        return sourceRepository.findById(id).map(source -> modelMapper.map(source, SourceDto.class)).orElse(null);
    }

    public boolean newsExist(Long id) {
        return sourceRepository.existsById(id);
    }

    public void deleteNews(Long id) {
        if(!newsExist(id)) {
            throw new NewsNotFoundException(String.valueOf(id));
        }
        sourceRepository.deleteById(id);
    }

    public SourceDto updateNews(Long id, SourceDto sourceDTO) {
        Optional<Source> existingSourceOptional = sourceRepository.findById(id);
        if (existingSourceOptional.isPresent()) {
            Source existingSource = existingSourceOptional.get();
            existingSource.setName(sourceDTO.getName());
            existingSource.setDescription(sourceDTO.getDescription());
            existingSource.setUrl(sourceDTO.getUrl());
            existingSource.setCategory(sourceDTO.getCategory());
            existingSource.setAuthor(sourceDTO.getAuthor());
            existingSource.setUpdatedAt(LocalDateTime.now());
            existingSource = sourceRepository.save(existingSource);
            return modelMapper.map(existingSource, SourceDto.class);
        } else {
            throw new NewsNotFoundException(String.valueOf(id));
        }
    }


}

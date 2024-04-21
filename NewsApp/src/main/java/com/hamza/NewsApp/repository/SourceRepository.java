package com.hamza.NewsApp.repository;

import com.hamza.NewsApp.model.Source;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface SourceRepository extends JpaRepository<Source, Long> {
    List<Source> findByCategory(String category);

    @Query("SELECT s FROM Source s WHERE LOWER(s.description) LIKE LOWER(CONCAT('%', :search, '%')) OR LOWER(s.name) LIKE LOWER(CONCAT('%', :search, '%'))")
    List<Source> findByDescriptionOrNameContainingIgnoreCase(String search);
}

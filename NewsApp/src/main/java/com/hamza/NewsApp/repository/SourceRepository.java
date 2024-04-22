package com.hamza.NewsApp.repository;

import com.hamza.NewsApp.model.Source;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface SourceRepository extends JpaRepository<Source, Long> {
    List<Source> findByCategory(String category);

    @Query("SELECT source FROM Source source WHERE LOWER(source.description) LIKE LOWER(CONCAT('%', :search, '%')) OR LOWER(source.name) LIKE LOWER(CONCAT('%', :search, '%'))")
    List<Source> findByTitleOrDescription(String search);
}

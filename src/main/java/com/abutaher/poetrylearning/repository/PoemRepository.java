package com.abutaher.poetrylearning.repository;

import com.abutaher.poetrylearning.model.Poem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PoemRepository extends JpaRepository<Poem, Long> {

    List<Poem> findByTitleContainingIgnoreCaseOrAuthorContainingIgnoreCase(
            String title,
            String author
    );

    boolean existsByTitle(String title);
}
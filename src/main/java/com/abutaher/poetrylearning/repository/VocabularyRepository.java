package com.abutaher.poetrylearning.repository;

import com.abutaher.poetrylearning.model.Vocabulary;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VocabularyRepository
        extends JpaRepository<Vocabulary, Long> {

    List<Vocabulary> findByPoemId(Long poemId);
}
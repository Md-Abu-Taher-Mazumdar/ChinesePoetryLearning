package com.abutaher.poetrylearning.repository;

import com.abutaher.poetrylearning.model.QuizQuestion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuizQuestionRepository
        extends JpaRepository<QuizQuestion, Long> {

    List<QuizQuestion> findByPoemId(Long poemId);
}
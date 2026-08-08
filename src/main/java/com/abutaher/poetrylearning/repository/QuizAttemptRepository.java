package com.abutaher.poetrylearning.repository;

import com.abutaher.poetrylearning.model.QuizAttempt;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuizAttemptRepository
        extends JpaRepository<QuizAttempt, Long> {

    List<QuizAttempt> findByUserIdOrderByCompletedAtDesc(Long userId);
}
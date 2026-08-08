package com.abutaher.poetrylearning.controller;

import com.abutaher.poetrylearning.repository.AppUserRepository;
import com.abutaher.poetrylearning.repository.PoemRepository;
import com.abutaher.poetrylearning.repository.QuizAttemptRepository;
import com.abutaher.poetrylearning.repository.QuizQuestionRepository;
import com.abutaher.poetrylearning.repository.VocabularyRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final AppUserRepository userRepository;
    private final PoemRepository poemRepository;
    private final VocabularyRepository vocabularyRepository;
    private final QuizQuestionRepository questionRepository;
    private final QuizAttemptRepository attemptRepository;

    public AdminController(
            AppUserRepository userRepository,
            PoemRepository poemRepository,
            VocabularyRepository vocabularyRepository,
            QuizQuestionRepository questionRepository,
            QuizAttemptRepository attemptRepository
    ) {
        this.userRepository = userRepository;
        this.poemRepository = poemRepository;
        this.vocabularyRepository = vocabularyRepository;
        this.questionRepository = questionRepository;
        this.attemptRepository = attemptRepository;
    }

    @GetMapping
    public String showDashboard(Model model) {

        model.addAttribute(
                "userCount",
                userRepository.count()
        );

        model.addAttribute(
                "poemCount",
                poemRepository.count()
        );

        model.addAttribute(
                "vocabularyCount",
                vocabularyRepository.count()
        );

        model.addAttribute(
                "questionCount",
                questionRepository.count()
        );

        model.addAttribute(
                "attemptCount",
                attemptRepository.count()
        );

        model.addAttribute(
                "users",
                userRepository.findAll()
        );

        return "admin-dashboard";
    }
}
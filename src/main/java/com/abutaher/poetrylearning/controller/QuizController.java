package com.abutaher.poetrylearning.controller;

import com.abutaher.poetrylearning.model.AppUser;
import com.abutaher.poetrylearning.model.Poem;
import com.abutaher.poetrylearning.model.QuizAttempt;
import com.abutaher.poetrylearning.model.QuizQuestion;
import com.abutaher.poetrylearning.repository.AppUserRepository;
import com.abutaher.poetrylearning.repository.PoemRepository;
import com.abutaher.poetrylearning.repository.QuizAttemptRepository;
import com.abutaher.poetrylearning.repository.QuizQuestionRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;
import java.util.List;
import java.util.Map;

@Controller
public class QuizController {

    private final PoemRepository poemRepository;
    private final QuizQuestionRepository questionRepository;
    private final QuizAttemptRepository attemptRepository;
    private final AppUserRepository userRepository;

    public QuizController(
            PoemRepository poemRepository,
            QuizQuestionRepository questionRepository,
            QuizAttemptRepository attemptRepository,
            AppUserRepository userRepository
    ) {
        this.poemRepository = poemRepository;
        this.questionRepository = questionRepository;
        this.attemptRepository = attemptRepository;
        this.userRepository = userRepository;
    }

    @GetMapping("/quiz/{poemId}")
    public String showQuiz(
            @PathVariable Long poemId,
            Model model
    ) {
        Poem poem = findPoem(poemId);

        model.addAttribute("poem", poem);
        model.addAttribute(
                "questions",
                questionRepository.findByPoemId(poemId)
        );

        return "quiz";
    }

    @PostMapping("/quiz/{poemId}")
    public String submitQuiz(
            @PathVariable Long poemId,
            @RequestParam Map<String, String> answers,
            Principal principal,
            Model model
    ) {
        Poem poem = findPoem(poemId);

        List<QuizQuestion> questions =
                questionRepository.findByPoemId(poemId);

        int score = 0;

        for (QuizQuestion question : questions) {
            String selectedAnswer =
                    answers.get("question_" + question.getId());

            if (question.getCorrectOption()
                    .equalsIgnoreCase(selectedAnswer)) {
                score++;
            }
        }

        AppUser user = userRepository
                .findByUsername(principal.getName())
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "User not found"
                        ));

        QuizAttempt attempt = new QuizAttempt();

        attempt.setUser(user);
        attempt.setPoem(poem);
        attempt.setScore(score);
        attempt.setTotalQuestions(questions.size());

        attemptRepository.save(attempt);

        model.addAttribute("poem", poem);
        model.addAttribute("score", score);
        model.addAttribute("total", questions.size());
        model.addAttribute("percentage", attempt.getPercentage());

        return "quiz-result";
    }

    private Poem findPoem(Long poemId) {
        return poemRepository.findById(poemId)
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Poem not found"
                        ));
    }
}
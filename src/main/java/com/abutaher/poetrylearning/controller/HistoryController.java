package com.abutaher.poetrylearning.controller;

import com.abutaher.poetrylearning.model.AppUser;
import com.abutaher.poetrylearning.repository.AppUserRepository;
import com.abutaher.poetrylearning.repository.QuizAttemptRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;

@Controller
public class HistoryController {

    private final AppUserRepository userRepository;
    private final QuizAttemptRepository attemptRepository;

    public HistoryController(
            AppUserRepository userRepository,
            QuizAttemptRepository attemptRepository
    ) {
        this.userRepository = userRepository;
        this.attemptRepository = attemptRepository;
    }

    @GetMapping("/history")
    public String showHistory(
            Principal principal,
            Model model
    ) {
        AppUser user = userRepository
                .findByUsername(principal.getName())
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "User not found"
                        ));

        model.addAttribute("user", user);

        model.addAttribute(
                "attempts",
                attemptRepository
                        .findByUserIdOrderByCompletedAtDesc(user.getId())
        );

        return "history";
    }
}
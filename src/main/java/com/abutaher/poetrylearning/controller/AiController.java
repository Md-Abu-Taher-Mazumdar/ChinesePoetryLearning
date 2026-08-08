package com.abutaher.poetrylearning.controller;

import com.abutaher.poetrylearning.model.Poem;
import com.abutaher.poetrylearning.repository.PoemRepository;
import com.abutaher.poetrylearning.service.AiService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

@Controller
public class AiController {

    private final AiService aiService;
    private final PoemRepository poemRepository;

    public AiController(
            AiService aiService,
            PoemRepository poemRepository
    ) {
        this.aiService = aiService;
        this.poemRepository = poemRepository;
    }

    @GetMapping("/ai-chat")
    public String showChatPage() {
        return "ai-chat";
    }

    @PostMapping("/ai-chat")
    public String askAi(
            @RequestParam String message,
            Model model
    ) {
        model.addAttribute("message", message);
        model.addAttribute("answer", aiService.ask(message));

        return "ai-chat";
    }

    @GetMapping("/ai-analysis/{poemId}")
    public String showAnalysisPage(
            @PathVariable Long poemId,
            Model model
    ) {
        model.addAttribute("poem", findPoem(poemId));
        return "ai-analysis";
    }

    @PostMapping("/ai-analysis/{poemId}")
    public String analysePoem(
            @PathVariable Long poemId,
            Model model
    ) {
        Poem poem = findPoem(poemId);

        String prompt = """
                Analyse this classical Chinese poem.

                Title: %s
                Author: %s
                Dynasty: %s
                Chinese text:
                %s

                Explain:
                1. Main meaning
                2. Important imagery
                3. Emotions and themes
                4. Cultural background
                5. Difficult Chinese words
                6. A modern lesson from the poem
                """.formatted(
                poem.getTitle(),
                poem.getAuthor(),
                poem.getDynasty(),
                poem.getChineseText()
        );

        model.addAttribute("poem", poem);
        model.addAttribute("analysis", aiService.ask(prompt));

        return "ai-analysis";
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
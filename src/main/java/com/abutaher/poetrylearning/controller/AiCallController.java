package com.abutaher.poetrylearning.controller;

import com.abutaher.poetrylearning.service.AiService;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/ai-call")
public class AiCallController {

    private final AiService aiService;

    public AiCallController(AiService aiService) {
        this.aiService = aiService;
    }

    @GetMapping
    public String showCallPage() {
        return "ai-call";
    }

    @PostMapping(
            value = "/ask",
            produces = MediaType.TEXT_PLAIN_VALUE
    )
    @ResponseBody
    public String askTutor(
            @RequestParam("message") String message
    ) {
        if (message == null || message.isBlank()) {
            return "Please ask me a question.";
        }

        return aiService.ask(
                """
                This is an AI tutor call.

                The learner said:
                %s

                Reply naturally as a professional Chinese poetry teacher.
                Use the learner's requested language.
                Give a short answer of no more than 60 words.
                """.formatted(message)
        );
    }
}
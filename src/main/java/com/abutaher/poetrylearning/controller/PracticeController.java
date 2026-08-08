package com.abutaher.poetrylearning.controller;

import com.abutaher.poetrylearning.model.Poem;
import com.abutaher.poetrylearning.repository.PoemRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.server.ResponseStatusException;

@Controller
public class PracticeController {

    private final PoemRepository poemRepository;

    public PracticeController(PoemRepository poemRepository) {
        this.poemRepository = poemRepository;
    }

    @GetMapping("/pronunciation/{poemId}")
    public String showPronunciationPractice(
            @PathVariable Long poemId,
            Model model
    ) {
        model.addAttribute("poem", findPoem(poemId));
        return "pronunciation";
    }

    @GetMapping("/handwriting/{poemId}")
    public String showHandwritingPractice(
            @PathVariable Long poemId,
            Model model
    ) {
        model.addAttribute("poem", findPoem(poemId));
        return "handwriting";
    }

    @GetMapping("/writing")
    public String showWritingPractice(Model model) {
        model.addAttribute("poems", poemRepository.findAll());
        return "writing";
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
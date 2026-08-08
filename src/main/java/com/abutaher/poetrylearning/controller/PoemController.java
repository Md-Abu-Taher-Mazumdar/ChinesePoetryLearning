package com.abutaher.poetrylearning.controller;

import com.abutaher.poetrylearning.model.Poem;
import com.abutaher.poetrylearning.repository.PoemRepository;
import com.abutaher.poetrylearning.repository.VocabularyRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Controller
public class PoemController {

    private final PoemRepository poemRepository;
    private final VocabularyRepository vocabularyRepository;

    public PoemController(
            PoemRepository poemRepository,
            VocabularyRepository vocabularyRepository
    ) {
        this.poemRepository = poemRepository;
        this.vocabularyRepository = vocabularyRepository;
    }

    @GetMapping("/poems")
    public String showPoems(
            @RequestParam(defaultValue = "") String search,
            Model model
    ) {
        List<Poem> poems;

        if (search.isBlank()) {
            poems = poemRepository.findAll();
        } else {
            poems =
                    poemRepository
                            .findByTitleContainingIgnoreCaseOrAuthorContainingIgnoreCase(
                                    search,
                                    search
                            );
        }

        model.addAttribute("poems", poems);
        model.addAttribute("search", search);

        return "poems";
    }

    @GetMapping("/poems/{id}")
    public String showPoemDetails(
            @PathVariable Long id,
            Model model
    ) {
        Poem poem = poemRepository.findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Poem not found"
                        ));

        model.addAttribute("poem", poem);
        model.addAttribute(
                "vocabulary",
                vocabularyRepository.findByPoemId(id)
        );

        return "poem-detail";
    }
}
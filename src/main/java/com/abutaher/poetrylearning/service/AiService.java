package com.abutaher.poetrylearning.service;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.HashMap;
import java.util.Map;

@Service
public class AiService {

    private final RestClient restClient =
            RestClient.create("http://localhost:11434");

    public String ask(String userPrompt) {
        try {
            Map<String, Object> request = new HashMap<>();

            request.put("model", "qwen3.5:2b");
            request.put("stream", false);
            request.put("think", false);
            request.put("keep_alive", "10m");

            request.put(
                    "system",
                    """
                    You are a careful Chinese-language and poetry teacher.

                    Rules:
                    1. Answer in the language requested by the user.
                    2. For a Chinese word, provide the correct tone-mark pinyin.
                    3. Provide the correct meaning in the requested language.
                    4. Never invent poem lines, people, places or historical facts.
                    5. If uncertain, clearly say that you are uncertain.
                    6. Use simple language and no more than 120 words.
                    7. Do not use Markdown symbols such as ** or ##.
                    8. Do not display internal reasoning.
                    """
            );

            request.put(
                    "prompt",
                    userPrompt + """

                    Give only a direct, accurate and concise answer.
                    """
            );

            request.put(
                    "options",
                    Map.of(
                            "num_predict", 160,
                            "temperature", 0.1
                    )
            );

            Map<?, ?> response = restClient
                    .post()
                    .uri("/api/generate")
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(request)
                    .retrieve()
                    .body(Map.class);

            if (response == null
                    || response.get("response") == null
                    || response.get("response").toString().isBlank()) {

                return "The local AI returned an empty response.";
            }

            return response.get("response")
                    .toString()
                    .trim();

        } catch (Exception exception) {
            exception.printStackTrace();

            return """
                    The local AI could not answer.
                    Please make sure Ollama is running and try again.
                    """;
        }
    }
}

package com.skillgap.skill_ai.Controller;

import com.skillgap.skill_ai.dto.AIChatRequest;
import com.skillgap.skill_ai.Service.OpenAIService;
import com.skillgap.skill_ai.Service.MessageService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api/ai")
public class AIChatController {

    private final OpenAIService openAIService;
    private final MessageService messageService;

    public AIChatController(OpenAIService openAIService,
                            MessageService messageService) {
        this.openAIService = openAIService;
        this.messageService = messageService;
    }

    @PostMapping("/chat")
    public Map<String, String> chatWithAI(
            @RequestParam Long chatId,
            @Valid @RequestBody AIChatRequest request) {

        // Save the user's message
        messageService.saveMessage(
                chatId,
                request.getMessage(),
                "USER"
        );

        // Generate AI response
        String answer = openAIService.generateCareerResponse(
                request.getMessage()
        );

        // Save the AI response
        messageService.saveMessage(
                chatId,
                answer,
                "AI"
        );

        return Map.of("response", answer);
    }
}

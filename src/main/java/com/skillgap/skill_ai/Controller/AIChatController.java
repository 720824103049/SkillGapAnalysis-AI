
package com.skillgap.skill_ai.Controller;

import com.skillgap.skill_ai.dto.AIChatRequest;
import com.skillgap.skill_ai.Service.OpenAIService;
import com.skillgap.skill_ai.Service.MessageService;
import com.skillgap.skill_ai.Service.ChatService;
import com.skillgap.skill_ai.entity.Chat;
import com.skillgap.skill_ai.entity.Message;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/ai")
public class AIChatController {

    private final OpenAIService openAIService;
    private final MessageService messageService;
    private final ChatService chatService;

    public AIChatController(
            OpenAIService openAIService,
            MessageService messageService,
            ChatService chatService) {

        this.openAIService = openAIService;
        this.messageService = messageService;
        this.chatService = chatService;
    }

    @PostMapping("/chat")
    public Map<String, String> chatWithAI(
            @RequestParam Long chatId,
            @Valid @RequestBody AIChatRequest request) {

        Chat chat = chatService.getChatById(chatId);

        Long userId = chat.getUser().getId();

        // Step 1: Retrieve previous conversation messages
        List<Message> chatHistory =
                messageService.getChatMessages(chatId);

        // Step 2: Save the current user message
        messageService.saveMessage(
                chatId,
                request.getMessage(),
                "USER"
        );

        // Step 3: Generate AI response using previous history
        String answer = openAIService.generateCareerResponse(
                userId,
                request.getMessage(),
                chatHistory
        );

        // Step 4: Save AI response
        messageService.saveMessage(
                chatId,
                answer,
                "AI"
        );

        return Map.of("response", answer);
    }
}

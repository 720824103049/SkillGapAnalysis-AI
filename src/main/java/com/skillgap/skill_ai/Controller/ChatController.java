
package com.skillgap.skill_ai.Controller;

import com.skillgap.skill_ai.dto.ChatRequest;
import com.skillgap.skill_ai.entity.Chat;
import com.skillgap.skill_ai.Service.ChatService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/chats")
public class ChatController {

    private final ChatService chatService;

    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    // Create a new chat
   @PostMapping
public Chat createChat(@RequestParam Long userId,
                       @Valid @RequestBody ChatRequest request) {

    return chatService.createChat(
            userId,
            request.getTitle()
    );
}
    // Get all chats of a user
    @GetMapping("/user/{userId}")
    public List<Chat> getUserChats(@PathVariable Long userId) {

        return chatService.getUserChats(userId);
    }

    // Get a particular chat
    @GetMapping("/{chatId}")
    public Chat getChatById(@PathVariable Long chatId) {

        return chatService.getChatById(chatId);
    }
}

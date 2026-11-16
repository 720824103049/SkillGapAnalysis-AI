
package com.skillgap.skill_ai.Controller;

import com.skillgap.skill_ai.dto.MessageRequest;
import com.skillgap.skill_ai.entity.Message;
import com.skillgap.skill_ai.Service.MessageService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/chats/{chatId}/messages")
public class MessageController {

    private final MessageService messageService;

    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    // Send and save a message
    @PostMapping
    public Message sendMessage(@PathVariable Long chatId,
                              @Valid @RequestBody MessageRequest request) {

        return messageService.saveMessage(
                chatId,
                request.getContent(),
                request.getSender()
        );
    }

    // Get all messages of a chat
    @GetMapping
    public List<Message> getChatMessages(@PathVariable Long chatId) {

        return messageService.getChatMessages(chatId);
    }
}

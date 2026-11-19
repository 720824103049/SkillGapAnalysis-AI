
package com.skillgap.skill_ai.Controller;

import com.skillgap.skill_ai.entity.Message;
import com.skillgap.skill_ai.Service.MessageService;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/chats")
public class ChatMessageController {

    private final MessageService messageService;

    public ChatMessageController(MessageService messageService) {
        this.messageService = messageService;
    }

}

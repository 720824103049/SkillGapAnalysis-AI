
package com.skillgap.skill_ai.Service;

import com.skillgap.skill_ai.entity.Chat;
import com.skillgap.skill_ai.entity.Message;
import com.skillgap.skill_ai.Repository.ChatRepository;
import com.skillgap.skill_ai.Repository.MessageRepository;
import com.skillgap.skill_ai.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class MessageService {

    private final MessageRepository messageRepository;
    private final ChatRepository chatRepository;

    public MessageService(MessageRepository messageRepository,
                          ChatRepository chatRepository) {
        this.messageRepository = messageRepository;
        this.chatRepository = chatRepository;
    }

    // Save a message in a chat
   public Message saveMessage(Long chatId, String content, String sender) {

    Chat chat = chatRepository.findById(chatId)
            .orElseThrow(() -> new ResourceNotFoundException("Chat not found with ID: " + chatId)
            );

    Message message = new Message(content, sender, chat);

    return messageRepository.save(message);
}

    // Get all messages belonging to a chat
    public List<Message> getChatMessages(Long chatId) {

    chatRepository.findById(chatId)
        .orElseThrow(() ->
            new ResourceNotFoundException(
                "Chat not found with ID: " + chatId
            )
        );

    return messageRepository.findByChatId(chatId);
}
}

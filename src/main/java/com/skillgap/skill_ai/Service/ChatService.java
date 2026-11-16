
package com.skillgap.skill_ai.Service;

import com.skillgap.skill_ai.entity.Chat;
import com.skillgap.skill_ai.entity.User;
import com.skillgap.skill_ai.exception.ResourceNotFoundException;
import com.skillgap.skill_ai.Repository.ChatRepository;
import com.skillgap.skill_ai.Repository.UserRepository;

import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ChatService {

    private final ChatRepository chatRepository;
    private final UserRepository userRepository;

    public ChatService(ChatRepository chatRepository,
                       UserRepository userRepository) {
        this.chatRepository = chatRepository;
        this.userRepository = userRepository;
    }

    // Create a new chat
    public Chat createChat(Long userId, String title) {

    User user = userRepository.findById(userId)
            .orElseThrow(() ->
                new ResourceNotFoundException("User not found with ID: " + userId)
            );

    Chat chat = new Chat(title, user);

    return chatRepository.save(chat);
}

public Chat getChatById(Long chatId) {

    return chatRepository.findById(chatId)
            .orElseThrow(() ->
                new ResourceNotFoundException("Chat not found with ID: " + chatId)
            );
}

    // Get all chats of a user
    public List<Chat> getUserChats(Long userId) {
        return chatRepository.findByUserId(userId);
    }

}

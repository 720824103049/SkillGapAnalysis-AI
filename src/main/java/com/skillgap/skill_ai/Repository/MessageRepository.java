
package com.skillgap.skill_ai.Repository;

import com.skillgap.skill_ai.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {

    // Retrieve messages in chronological order
    List<Message> findByChatIdOrderByIdAsc(Long chatId);

}

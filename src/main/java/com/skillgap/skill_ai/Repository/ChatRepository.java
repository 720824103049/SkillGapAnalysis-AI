
package com.skillgap.skill_ai.Repository;

import com.skillgap.skill_ai.entity.Chat;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ChatRepository extends JpaRepository<Chat, Long> {

    List<Chat> findByUserId(Long userId);

}

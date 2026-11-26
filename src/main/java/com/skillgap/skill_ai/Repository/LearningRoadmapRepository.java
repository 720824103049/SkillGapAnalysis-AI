
package com.skillgap.skill_ai.Repository;

import com.skillgap.skill_ai.entity.LearningRoadmap;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface LearningRoadmapRepository
        extends JpaRepository<LearningRoadmap, Long> {

    List<LearningRoadmap> findByUserIdOrderBySequenceOrderAsc(Long userId);

    long countByUserId(Long userId);

    long countByUserIdAndStatus(Long userId, String status);

    long deleteByUserId(Long userId);
}


package com.skillgap.skill_ai.Repository;

import com.skillgap.skill_ai.entity.CareerRequiredSkill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CareerRequiredSkillRepository
        extends JpaRepository<CareerRequiredSkill, Long> {

    List<CareerRequiredSkill> findByCareerGoalId(Long careerGoalId);
}

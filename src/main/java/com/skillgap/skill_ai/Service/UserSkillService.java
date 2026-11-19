
package com.skillgap.skill_ai.Service;

import com.skillgap.skill_ai.entity.User;
import com.skillgap.skill_ai.entity.Skill;
import com.skillgap.skill_ai.entity.UserSkill;
import com.skillgap.skill_ai.Repository.UserRepository;
import com.skillgap.skill_ai.Repository.SkillRepository;
import com.skillgap.skill_ai.Repository.UserSkillRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserSkillService {

    private final UserSkillRepository userSkillRepository;
    private final UserRepository userRepository;
    private final SkillRepository skillRepository;

    public UserSkillService(
            UserSkillRepository userSkillRepository,
            UserRepository userRepository,
            SkillRepository skillRepository) {

        this.userSkillRepository = userSkillRepository;
        this.userRepository = userRepository;
        this.skillRepository = skillRepository;
    }

    public UserSkill addUserSkill(Long userId, Long skillId, String proficiency) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Skill skill = skillRepository.findById(skillId)
                .orElseThrow(() -> new RuntimeException("Skill not found"));

        UserSkill userSkill = new UserSkill(user, skill, proficiency);

        return userSkillRepository.save(userSkill);
    }

    public List<UserSkill> getUserSkills(Long userId) {
        return userSkillRepository.findByUserId(userId);
    }
}

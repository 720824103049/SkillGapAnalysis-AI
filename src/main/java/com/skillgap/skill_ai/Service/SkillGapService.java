
package com.skillgap.skill_ai.Service;

import com.skillgap.skill_ai.entity.User;
import com.skillgap.skill_ai.entity.UserSkill;
import com.skillgap.skill_ai.entity.CareerRequiredSkill;

import com.skillgap.skill_ai.dto.SkillGapResponse;

import com.skillgap.skill_ai.Repository.UserRepository;
import com.skillgap.skill_ai.Repository.UserSkillRepository;
import com.skillgap.skill_ai.Repository.CareerRequiredSkillRepository;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.ArrayList;

@Service
public class SkillGapService {

    private final UserRepository userRepository;
    private final UserSkillRepository userSkillRepository;
    private final CareerRequiredSkillRepository careerRequiredSkillRepository;

    public SkillGapService(
            UserRepository userRepository,
            UserSkillRepository userSkillRepository,
            CareerRequiredSkillRepository careerRequiredSkillRepository) {

        this.userRepository = userRepository;
        this.userSkillRepository = userSkillRepository;
        this.careerRequiredSkillRepository = careerRequiredSkillRepository;
    }

    public List<SkillGapResponse> analyzeSkillGap(Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (user.getCareerGoal() == null) {
            throw new RuntimeException("User has not selected a career goal");
        }

        Long careerGoalId = user.getCareerGoal().getId();

        List<UserSkill> userSkills =
                userSkillRepository.findByUserId(userId);

        List<CareerRequiredSkill> requiredSkills =
                careerRequiredSkillRepository.findByCareerGoalId(careerGoalId);

        List<SkillGapResponse> results = new ArrayList<>();

        for (CareerRequiredSkill requiredSkill : requiredSkills) {

            String skillName = requiredSkill.getSkill().getSkillName();

            String requiredLevel = requiredSkill.getRequiredProficiency();

            String currentLevel = null;

            for (UserSkill userSkill : userSkills) {

                if (userSkill.getSkill().getId()
                        .equals(requiredSkill.getSkill().getId())) {

                    currentLevel = userSkill.getProficiency();
                    break;
                }
            }

            String status;

            if (currentLevel == null) {
                status = "Missing";
            } else if (getLevelValue(currentLevel)
                    >= getLevelValue(requiredLevel)) {
                status = "Matched";
            } else {
                status = "Improve";
            }

            SkillGapResponse response = new SkillGapResponse(
                    skillName,
                    currentLevel == null ? "Not Available" : currentLevel,
                    requiredLevel,
                    status
            );

            results.add(response);
        }

        return results;
    }

    private int getLevelValue(String level) {

        if (level == null) {
            return 0;
        }

        switch (level.trim().toLowerCase()) {

            case "beginner":
                return 1;

            case "intermediate":
                return 2;

            case "advanced":
                return 3;

            default:
                throw new IllegalArgumentException(
                        "Invalid proficiency level: " + level);
        }
    }
}

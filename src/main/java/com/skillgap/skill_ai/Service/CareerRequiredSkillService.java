
package com.skillgap.skill_ai.Service;

import com.skillgap.skill_ai.entity.CareerGoal;
import com.skillgap.skill_ai.entity.Skill;
import com.skillgap.skill_ai.entity.CareerRequiredSkill;

import com.skillgap.skill_ai.Repository.CareerGoalRepository;
import com.skillgap.skill_ai.Repository.SkillRepository;
import com.skillgap.skill_ai.Repository.CareerRequiredSkillRepository;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CareerRequiredSkillService {

    private final CareerRequiredSkillRepository careerRequiredSkillRepository;
    private final CareerGoalRepository careerGoalRepository;
    private final SkillRepository skillRepository;

    public CareerRequiredSkillService(
            CareerRequiredSkillRepository careerRequiredSkillRepository,
            CareerGoalRepository careerGoalRepository,
            SkillRepository skillRepository) {

        this.careerRequiredSkillRepository = careerRequiredSkillRepository;
        this.careerGoalRepository = careerGoalRepository;
        this.skillRepository = skillRepository;
    }

    public CareerRequiredSkill addRequiredSkill(
            Long careerGoalId,
            Long skillId,
            String requiredProficiency) {

        CareerGoal careerGoal = careerGoalRepository.findById(careerGoalId)
                .orElseThrow(() -> new RuntimeException("Career goal not found"));

        Skill skill = skillRepository.findById(skillId)
                .orElseThrow(() -> new RuntimeException("Skill not found"));

        CareerRequiredSkill requiredSkill = new CareerRequiredSkill(
                careerGoal, skill, requiredProficiency);

        return careerRequiredSkillRepository.save(requiredSkill);
    }

    public List<CareerRequiredSkill> getRequiredSkills(Long careerGoalId) {
        return careerRequiredSkillRepository.findByCareerGoalId(careerGoalId);
    }
}

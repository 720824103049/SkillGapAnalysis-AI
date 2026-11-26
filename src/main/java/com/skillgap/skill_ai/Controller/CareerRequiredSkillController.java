
package com.skillgap.skill_ai.Controller;

import com.skillgap.skill_ai.entity.CareerRequiredSkill;
import com.skillgap.skill_ai.Service.CareerRequiredSkillService;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/career-goals")
public class CareerRequiredSkillController {

    private final CareerRequiredSkillService careerRequiredSkillService;

    public CareerRequiredSkillController(
            CareerRequiredSkillService careerRequiredSkillService) {
        this.careerRequiredSkillService = careerRequiredSkillService;
    }

    @PostMapping("/{careerGoalId}/required-skills")
    public CareerRequiredSkill addRequiredSkill(
            @PathVariable Long careerGoalId,
            @RequestParam Long skillId,
            @RequestParam String requiredProficiency) {

        return careerRequiredSkillService.addRequiredSkill(
                careerGoalId, skillId, requiredProficiency);
    }

    @GetMapping("/{careerGoalId}/required-skills")
    public List<CareerRequiredSkill> getRequiredSkills(
            @PathVariable Long careerGoalId) {

        return careerRequiredSkillService.getRequiredSkills(careerGoalId);
    }
}

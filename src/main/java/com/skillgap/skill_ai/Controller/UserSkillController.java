
package com.skillgap.skill_ai.Controller;

import com.skillgap.skill_ai.entity.UserSkill;
import com.skillgap.skill_ai.Service.UserSkillService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users/{userId}/skills")
public class UserSkillController {

    private final UserSkillService userSkillService;

    public UserSkillController(UserSkillService userSkillService) {
        this.userSkillService = userSkillService;
    }

    @PostMapping("/{skillId}")
    public UserSkill addUserSkill(
            @PathVariable Long userId,
            @PathVariable Long skillId,
            @RequestParam String proficiency) {

        return userSkillService.addUserSkill(userId, skillId, proficiency);
    }

    @GetMapping
    public List<UserSkill> getUserSkills(@PathVariable Long userId) {
        return userSkillService.getUserSkills(userId);
    }
}

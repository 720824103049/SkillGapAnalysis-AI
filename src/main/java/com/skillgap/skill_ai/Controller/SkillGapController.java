
package com.skillgap.skill_ai.Controller;

import com.skillgap.skill_ai.dto.SkillGapResponse;
import com.skillgap.skill_ai.Service.SkillGapService;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/skill-gap")
public class SkillGapController {

    private final SkillGapService skillGapService;

    public SkillGapController(SkillGapService skillGapService) {
        this.skillGapService = skillGapService;
    }

    @GetMapping("/{userId}")
    public List<SkillGapResponse> analyzeSkillGap(
            @PathVariable Long userId) {

        return skillGapService.analyzeSkillGap(userId);
    }
}

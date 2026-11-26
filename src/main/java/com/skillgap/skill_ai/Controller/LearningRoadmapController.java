
package com.skillgap.skill_ai.Controller;

import com.skillgap.skill_ai.entity.LearningRoadmap;
import com.skillgap.skill_ai.Service.LearningRoadmapService;
import com.skillgap.skill_ai.dto.RoadmapProgressResponse;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/learning-roadmaps")
public class LearningRoadmapController {

    private final LearningRoadmapService roadmapService;

    public LearningRoadmapController(
            LearningRoadmapService roadmapService) {
        this.roadmapService = roadmapService;
    }

    @PostMapping("/generate/{userId}")
    public List<LearningRoadmap> generateRoadmap(
            @PathVariable Long userId) {

        return roadmapService.generateRoadmap(userId);
    }

    @PostMapping("/regenerate/{userId}")
    public List<LearningRoadmap> regenerateRoadmap(
            @PathVariable Long userId) {

        return roadmapService.regenerateRoadmap(userId);
    }

    @GetMapping("/user/{userId}")
    public List<LearningRoadmap> getRoadmap(
            @PathVariable Long userId) {

        return roadmapService.getRoadmap(userId);
    }

    @PutMapping("/{roadmapId}/progress")
    public LearningRoadmap updateProgress(
            @PathVariable Long roadmapId,
            @RequestParam String status) {

        return roadmapService.updateProgress(roadmapId, status);
    }

    @GetMapping("/progress/{userId}")
    public RoadmapProgressResponse getRoadmapProgress(
            @PathVariable Long userId) {

        return roadmapService.getRoadmapProgress(userId);
    }
}

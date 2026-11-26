package com.skillgap.skill_ai.Controller;

import com.skillgap.skill_ai.Service.ProjectRecommendationService;
import com.skillgap.skill_ai.dto.ProjectRecommendationResponse;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/project-recommendations")
public class ProjectRecommendationController {

    private final ProjectRecommendationService projectRecommendationService;

    public ProjectRecommendationController(
            ProjectRecommendationService projectRecommendationService) {

        this.projectRecommendationService = projectRecommendationService;
    }

    @GetMapping("/{userId}")
    public List<ProjectRecommendationResponse> getProjectRecommendations(
            @PathVariable Long userId) {

        return projectRecommendationService
                .getProjectRecommendations(userId);
    }
}
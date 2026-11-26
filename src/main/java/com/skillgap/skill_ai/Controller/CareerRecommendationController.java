package com.skillgap.skill_ai.Controller;

import com.skillgap.skill_ai.Service.CareerRecommendationService;
import com.skillgap.skill_ai.dto.CareerRecommendationResponse;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/career-recommendations")
public class CareerRecommendationController {

    private final CareerRecommendationService recommendationService;

    public CareerRecommendationController(
            CareerRecommendationService recommendationService) {

        this.recommendationService = recommendationService;
    }

    @GetMapping("/{userId}")
    public List<CareerRecommendationResponse> getRecommendations(
            @PathVariable Long userId) {

        return recommendationService.getRecommendations(userId);
    }
}
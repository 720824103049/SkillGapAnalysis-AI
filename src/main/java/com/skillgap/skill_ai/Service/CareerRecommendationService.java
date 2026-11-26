package com.skillgap.skill_ai.Service;

import com.skillgap.skill_ai.dto.SkillGapResponse;
import com.skillgap.skill_ai.dto.CareerRecommendationResponse;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.ArrayList;

@Service
public class CareerRecommendationService {

    private final SkillGapService skillGapService;

    public CareerRecommendationService(SkillGapService skillGapService) {
        this.skillGapService = skillGapService;
    }

    public List<CareerRecommendationResponse> getRecommendations(Long userId) {

        List<SkillGapResponse> skillGaps =
                skillGapService.analyzeSkillGap(userId);

        List<CareerRecommendationResponse> recommendations =
                new ArrayList<>();

        int matched = 0;
        int improve = 0;
        int missing = 0;

        for (SkillGapResponse gap : skillGaps) {

            if (gap.getStatus().equals("Matched")) {
                matched++;
            } else if (gap.getStatus().equals("Improve")) {
                improve++;
            } else if (gap.getStatus().equals("Missing")) {
                missing++;
            }
        }

        int total = skillGaps.size();

        double matchPercentage = total == 0
                ? 0
                : (matched * 100.0) / total;

        String matchLevel;

        if (matchPercentage >= 75) {
            matchLevel = "High";
        } else if (matchPercentage >= 40) {
            matchLevel = "Medium";
        } else {
            matchLevel = "Beginner";
        }

        StringBuilder skills = new StringBuilder();

        for (SkillGapResponse gap : skillGaps) {

            if (!gap.getStatus().equals("Matched")) {
                skills.append(gap.getSkillName()).append(", ");
            }
        }

        recommendations.add(
                new CareerRecommendationResponse(
                        "Full Stack Developer",
                        "Develop frontend and backend applications using modern technologies.",
                        matchLevel,
                        skills.length() == 0
                                ? "All required skills matched"
                                : skills.substring(0, skills.length() - 2)
                )
        );

        return recommendations;
    }
}
package com.skillgap.skill_ai.dto;

public class CareerRecommendationResponse {

    private String careerName;
    private String description;
    private String matchLevel;
    private String recommendedSkills;

    public CareerRecommendationResponse(
            String careerName,
            String description,
            String matchLevel,
            String recommendedSkills) {

        this.careerName = careerName;
        this.description = description;
        this.matchLevel = matchLevel;
        this.recommendedSkills = recommendedSkills;
    }

    public String getCareerName() {
        return careerName;
    }

    public String getDescription() {
        return description;
    }

    public String getMatchLevel() {
        return matchLevel;
    }

    public String getRecommendedSkills() {
        return recommendedSkills;
    }
}
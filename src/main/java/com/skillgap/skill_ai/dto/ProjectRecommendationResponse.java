package com.skillgap.skill_ai.dto;

public class ProjectRecommendationResponse {

    private String projectName;
    private String description;
    private String requiredSkills;
    private String difficultyLevel;

    public ProjectRecommendationResponse(
            String projectName,
            String description,
            String requiredSkills,
            String difficultyLevel) {

        this.projectName = projectName;
        this.description = description;
        this.requiredSkills = requiredSkills;
        this.difficultyLevel = difficultyLevel;
    }

    public String getProjectName() {
        return projectName;
    }

    public String getDescription() {
        return description;
    }

    public String getRequiredSkills() {
        return requiredSkills;
    }

    public String getDifficultyLevel() {
        return difficultyLevel;
    }
}
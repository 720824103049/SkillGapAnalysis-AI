package com.skillgap.skill_ai.Service;

import com.skillgap.skill_ai.dto.SkillGapResponse;
import com.skillgap.skill_ai.dto.ProjectRecommendationResponse;

import org.springframework.stereotype.Service;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;

@Service
public class ProjectRecommendationService {

    private final SkillGapService skillGapService;

    public ProjectRecommendationService(SkillGapService skillGapService) {
        this.skillGapService = skillGapService;
    }

    public List<ProjectRecommendationResponse> getProjectRecommendations(
            Long userId) {

        List<SkillGapResponse> skillGaps =
                skillGapService.analyzeSkillGap(userId);

        List<ProjectRecommendationResponse> projects =
                new ArrayList<>();

        for (SkillGapResponse gap : skillGaps) {

            if (gap.getStatus().equals("Missing")
                    || gap.getStatus().equals("Improve")) {

                String skill = gap.getSkillName();

                switch (skill.toLowerCase()) {

                    case "java":
                        projects.add(new ProjectRecommendationResponse(
                                "Library Management System",
                                "Build an application to manage books, users and borrowing records.",
                                "Java, OOP, Collections",
                                "Beginner"
                        ));
                        break;

                    case "spring boot":
                        projects.add(new ProjectRecommendationResponse(
                                "Employee Management REST API",
                                "Develop CRUD APIs for managing employee information.",
                                "Java, Spring Boot, REST API, JPA",
                                "Intermediate"
                        ));
                        break;

                    case "mysql":
                        projects.add(new ProjectRecommendationResponse(
                                "Student Database Management",
                                "Create a database to manage student records and academic information.",
                                "MySQL, SQL, Joins",
                                "Beginner"
                        ));
                        break;

                    case "html":
                    case "css":
                    case "javascript":
                        projects.add(new ProjectRecommendationResponse(
                                "Personal Portfolio Website",
                                "Develop a responsive portfolio website to showcase your skills and projects.",
                                "HTML, CSS, JavaScript",
                                "Beginner"
                        ));
                        break;

                    case "react":
                        projects.add(new ProjectRecommendationResponse(
                                "Task Management Dashboard",
                                "Build a dashboard to create, update and track daily tasks.",
                                "React, Components, Hooks",
                                "Intermediate"
                        ));
                        break;

                    case "python":
                        projects.add(new ProjectRecommendationResponse(
                                "Data Analysis Application",
                                "Analyze datasets and generate useful visual reports.",
                                "Python, Pandas, Matplotlib",
                                "Intermediate"
                        ));
                        break;
                }
            }
        }

Map<String, ProjectRecommendationResponse> uniqueProjects =
        new LinkedHashMap<>();

for (ProjectRecommendationResponse project : projects) {
    uniqueProjects.putIfAbsent(
            project.getProjectName(),
            project
    );
}

return new ArrayList<>(uniqueProjects.values());
    }
}
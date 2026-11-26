
package com.skillgap.skill_ai.Service;

import com.skillgap.skill_ai.dto.SkillGapResponse;
import com.skillgap.skill_ai.dto.RoadmapProgressResponse;
import com.skillgap.skill_ai.entity.*;
import com.skillgap.skill_ai.Repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class LearningRoadmapService {

    private final LearningRoadmapRepository roadmapRepository;
    private final UserRepository userRepository;
    private final SkillGapService skillGapService;

    public LearningRoadmapService(
            LearningRoadmapRepository roadmapRepository,
            UserRepository userRepository,
            SkillGapService skillGapService) {

        this.roadmapRepository = roadmapRepository;
        this.userRepository = userRepository;
        this.skillGapService = skillGapService;
    }

    public RoadmapProgressResponse getRoadmapProgress(Long userId) {

        long totalTasks = roadmapRepository.countByUserId(userId);

        long completedTasks =
                roadmapRepository.countByUserIdAndStatus(userId, "Completed");

        long pendingTasks =
                roadmapRepository.countByUserIdAndStatus(userId, "Pending");

        double progressPercentage = 0.0;

        if (totalTasks > 0) {
            progressPercentage =
                    (completedTasks * 100.0) / totalTasks;
        }

        return new RoadmapProgressResponse(
                totalTasks,
                completedTasks,
                pendingTasks,
                progressPercentage
        );
    }

    public List<LearningRoadmap> generateRoadmap(Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        List<LearningRoadmap> existingRoadmap =
                roadmapRepository.findByUserIdOrderBySequenceOrderAsc(userId);

        if (!existingRoadmap.isEmpty()) {
            return existingRoadmap;
        }

        return createRoadmap(user);
    }
@Transactional
public List<LearningRoadmap> regenerateRoadmap(Long userId)  {

        User user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        roadmapRepository.deleteByUserId(userId);

        return createRoadmap(user);
    }

    private List<LearningRoadmap> createRoadmap(User user) {

        Long userId = user.getId();

        List<SkillGapResponse> gaps =
                skillGapService.analyzeSkillGap(userId);

        List<LearningRoadmap> roadmapList = new ArrayList<>();

        int sequence = 1;

        for (SkillGapResponse gap : gaps) {

            if (!gap.getStatus().equals("Improve")
                    && !gap.getStatus().equals("Missing")) {
                continue;
            }

            LearningRoadmap roadmap = new LearningRoadmap();

            roadmap.setUser(user);
            roadmap.setSkillName(gap.getSkillName());
            roadmap.setCurrentProficiency(
                    gap.getCurrentProficiency());
            roadmap.setRequiredProficiency(
                    gap.getRequiredProficiency());
roadmap.setPriority(
        gap.getCurrentProficiency() == null
                || gap.getCurrentProficiency().equalsIgnoreCase("Beginner")
                && gap.getRequiredProficiency().equalsIgnoreCase("Advanced")
                ? "High" : "Medium");

            roadmap.setRecommendedTopics(
                    getRecommendedTopics(gap.getSkillName()));

            roadmap.setSequenceOrder(sequence++);

            roadmap.setStatus("Pending");

            roadmapList.add(roadmap);
        }

        return roadmapRepository.saveAll(roadmapList);
    }

    public List<LearningRoadmap> getRoadmap(Long userId) {

        userRepository.findById(userId)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        return roadmapRepository
                .findByUserIdOrderBySequenceOrderAsc(userId);
    }

    public LearningRoadmap updateProgress(Long roadmapId, String status) {

        LearningRoadmap roadmap = roadmapRepository.findById(roadmapId)
                .orElseThrow(() ->
                        new RuntimeException("Roadmap not found"));

        if (!status.equals("Pending") && !status.equals("Completed")) {
            throw new RuntimeException("Invalid status");
        }

        roadmap.setStatus(status);

        return roadmapRepository.save(roadmap);
    }

    private String getRecommendedTopics(String skillName) {

        switch (skillName.toLowerCase()) {

            case "java":
                return "OOP, Collections, Exception Handling, Streams";

            case "spring boot":
                return "REST API, Spring Data JPA, Dependency Injection, Security";

            case "mysql":
                return "Joins, Subqueries, Indexing, Transactions";

            case "html":
                return "Forms, Semantic HTML, Accessibility";

            case "css":
                return "Flexbox, Grid, Responsive Design";

            case "javascript":
                return "DOM, ES6, Promises, Async/Await";

            case "react":
                return "Components, Props, State, Hooks";

            case "python":
                return "Data Structures, Functions, OOP, Modules";

            default:
                return "Fundamentals, Practical Exercises, Mini Projects";
        }
    }
}

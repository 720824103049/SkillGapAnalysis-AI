
package com.skillgap.skill_ai.Service;

import org.springframework.stereotype.Service;

@Service
public class ProjectCodeContextService {

    public String getUserManagementCode() {

        return """

                ===== USER MANAGEMENT SOURCE CODE =====

                FILE 1: User.java

                package com.skillgap.skill_ai.entity;

                import jakarta.persistence.*;
                import jakarta.validation.constraints.Email;
                import jakarta.validation.constraints.NotBlank;
                import jakarta.validation.constraints.Size;

                @Entity
                @Table(name = "users")
                public class User {

                    @Id
                    @GeneratedValue(strategy = GenerationType.IDENTITY)
                    private Long id;

                    @NotBlank(message = "Name cannot be empty")
                    @Size(max = 100, message = "Name cannot exceed 100 characters")
                    @Column(nullable = false)
                    private String name;

                    @NotBlank(message = "Email cannot be empty")
                    @Email(message = "Enter a valid email address")
                    @Column(nullable = false, unique = true)
                    private String email;

                    @ManyToOne
                    @JoinColumn(name = "career_goal_id")
                    private CareerGoal careerGoal;

                    public User() {
                    }

                    public User(String name, String email) {
                        this.name = name;
                        this.email = email;
                    }

                    public Long getId() {
                        return id;
                    }

                    public String getName() {
                        return name;
                    }

                    public void setName(String name) {
                        this.name = name;
                    }

                    public String getEmail() {
                        return email;
                    }

                    public void setEmail(String email) {
                        this.email = email;
                    }

                    public CareerGoal getCareerGoal() {
                        return careerGoal;
                    }

                    public void setCareerGoal(CareerGoal careerGoal) {
                        this.careerGoal = careerGoal;
                    }
                }

                ===== END FILE 1 =====


                FILE 2: UserRepository.java

                package com.skillgap.skill_ai.Repository;

                import com.skillgap.skill_ai.entity.User;
                import org.springframework.data.jpa.repository.JpaRepository;

                public interface UserRepository
                        extends JpaRepository<User, Long> {
                }

                ===== END FILE 2 =====


                FILE 3: CareerGoalRepository.java

                package com.skillgap.skill_ai.Repository;

                import com.skillgap.skill_ai.entity.CareerGoal;
                import org.springframework.data.jpa.repository.JpaRepository;
                import org.springframework.stereotype.Repository;

                @Repository
                public interface CareerGoalRepository
                        extends JpaRepository<CareerGoal, Long> {
                }

                ===== END FILE 3 =====


                FILE 4: UserService.java

                package com.skillgap.skill_ai.Service;

                import com.skillgap.skill_ai.entity.User;
                import com.skillgap.skill_ai.entity.CareerGoal;

                import com.skillgap.skill_ai.Repository.UserRepository;
                import com.skillgap.skill_ai.Repository.CareerGoalRepository;

                import com.skillgap.skill_ai.exception.ResourceNotFoundException;

                import org.springframework.stereotype.Service;
                import java.util.List;

                @Service
                public class UserService {

                    private final UserRepository userRepository;
                    private final CareerGoalRepository careerGoalRepository;

                    public UserService(
                            UserRepository userRepository,
                            CareerGoalRepository careerGoalRepository) {

                        this.userRepository = userRepository;
                        this.careerGoalRepository = careerGoalRepository;
                    }

                    public User registerUser(User user) {
                        return userRepository.save(user);
                    }

                    public List<User> getAllUsers() {
                        return userRepository.findAll();
                    }

                    public User getUserById(Long id) {
                        return userRepository.findById(id)
                                .orElseThrow(() ->
                                        new ResourceNotFoundException(
                                                "User not found with ID: " + id));
                    }

                    public User assignCareerGoal(Long userId, Long careerGoalId) {

                        User user = userRepository.findById(userId)
                                .orElseThrow(() ->
                                        new ResourceNotFoundException(
                                                "User not found with ID: " + userId));

                        CareerGoal careerGoal = careerGoalRepository.findById(careerGoalId)
                                .orElseThrow(() ->
                                        new ResourceNotFoundException(
                                                "Career goal not found with ID: " + careerGoalId));

                        user.setCareerGoal(careerGoal);

                        return userRepository.save(user);
                    }
                }

                ===== END FILE 4 =====

                ===== END USER MANAGEMENT SOURCE CODE =====

                """;
    }

public String getSkillAssessmentCode() {

    return """

            ===== SKILL ASSESSMENT SOURCE CODE =====

            FILE 1: Skill.java

            package com.skillgap.skill_ai.entity;

            import jakarta.persistence.*;
            import jakarta.validation.constraints.NotBlank;

            @Entity
            @Table(name = "skills")
            public class Skill {

                @Id
                @GeneratedValue(strategy = GenerationType.IDENTITY)
                private Long id;

                @NotBlank(message = "Skill name cannot be empty")
                @Column(nullable = false, unique = true)
                private String skillName;

                private String category;

                public Skill() {}

                public Skill(String skillName, String category) {
                    this.skillName = skillName;
                    this.category = category;
                }

                public Long getId() { return id; }
                public void setId(Long id) { this.id = id; }
                public String getSkillName() { return skillName; }
                public void setSkillName(String skillName) {
                    this.skillName = skillName;
                }
                public String getCategory() { return category; }
                public void setCategory(String category) {
                    this.category = category;
                }
            }

            ===== END FILE 1 =====

            FILE 2: UserSkill.java

            package com.skillgap.skill_ai.entity;

            import jakarta.persistence.*;
            import jakarta.validation.constraints.NotBlank;

            @Entity
            @Table(name = "user_skills")
            public class UserSkill {

                @Id
                @GeneratedValue(strategy = GenerationType.IDENTITY)
                private Long id;

                @ManyToOne
                @JoinColumn(name = "user_id", nullable = false)
                private User user;

                @ManyToOne
                @JoinColumn(name = "skill_id", nullable = false)
                private Skill skill;

                @NotBlank(message = "Proficiency cannot be empty")
                @Column(nullable = false)
                private String proficiency;

                public UserSkill() {}

                public UserSkill(User user, Skill skill, String proficiency) {
                    this.user = user;
                    this.skill = skill;
                    this.proficiency = proficiency;
                }

                public Long getId() { return id; }
                public void setId(Long id) { this.id = id; }
                public User getUser() { return user; }
                public void setUser(User user) { this.user = user; }
                public Skill getSkill() { return skill; }
                public void setSkill(Skill skill) { this.skill = skill; }
                public String getProficiency() { return proficiency; }
                public void setProficiency(String proficiency) {
                    this.proficiency = proficiency;
                }
            }

            ===== END FILE 2 =====

            FILE 3: SkillRepository.java

            package com.skillgap.skill_ai.Repository;

            import com.skillgap.skill_ai.entity.Skill;
            import org.springframework.data.jpa.repository.JpaRepository;
            import org.springframework.stereotype.Repository;

            @Repository
            public interface SkillRepository
                    extends JpaRepository<Skill, Long> {
            }

            ===== END FILE 3 =====

            FILE 4: UserSkillRepository.java

            package com.skillgap.skill_ai.Repository;

            import com.skillgap.skill_ai.entity.UserSkill;
            import org.springframework.data.jpa.repository.JpaRepository;
            import org.springframework.stereotype.Repository;
            import java.util.List;

            @Repository
            public interface UserSkillRepository
                    extends JpaRepository<UserSkill, Long> {

                List<UserSkill> findByUserId(Long userId);
            }

            ===== END FILE 4 =====

            FILE 5: SkillService.java

            package com.skillgap.skill_ai.Service;

            import com.skillgap.skill_ai.entity.Skill;
            import com.skillgap.skill_ai.Repository.SkillRepository;
            import org.springframework.stereotype.Service;
            import java.util.List;

            @Service
            public class SkillService {

                private final SkillRepository skillRepository;

                public SkillService(SkillRepository skillRepository) {
                    this.skillRepository = skillRepository;
                }

                public Skill addSkill(Skill skill) {
                    return skillRepository.save(skill);
                }

                public List<Skill> getAllSkills() {
                    return skillRepository.findAll();
                }

                public Skill getSkillById(Long id) {
                    return skillRepository.findById(id)
                        .orElseThrow(() -> new RuntimeException(
                            "Skill not found with ID: " + id));
                }
            }

            ===== END FILE 5 =====

            FILE 6: UserSkillService.java

            package com.skillgap.skill_ai.Service;

            import com.skillgap.skill_ai.entity.User;
            import com.skillgap.skill_ai.entity.Skill;
            import com.skillgap.skill_ai.entity.UserSkill;
            import com.skillgap.skill_ai.Repository.UserRepository;
            import com.skillgap.skill_ai.Repository.SkillRepository;
            import com.skillgap.skill_ai.Repository.UserSkillRepository;
            import org.springframework.stereotype.Service;
            import java.util.List;

            @Service
            public class UserSkillService {

                private final UserSkillRepository userSkillRepository;
                private final UserRepository userRepository;
                private final SkillRepository skillRepository;

                public UserSkillService(
                    UserSkillRepository userSkillRepository,
                    UserRepository userRepository,
                    SkillRepository skillRepository) {

                    this.userSkillRepository = userSkillRepository;
                    this.userRepository = userRepository;
                    this.skillRepository = skillRepository;
                }

                public UserSkill addUserSkill(
                    Long userId, Long skillId, String proficiency) {

                    User user = userRepository.findById(userId)
                        .orElseThrow(() ->
                            new RuntimeException("User not found"));

                    Skill skill = skillRepository.findById(skillId)
                        .orElseThrow(() ->
                            new RuntimeException("Skill not found"));

                    UserSkill userSkill =
                        new UserSkill(user, skill, proficiency);

                    return userSkillRepository.save(userSkill);
                }

                public List<UserSkill> getUserSkills(Long userId) {
                    return userSkillRepository.findByUserId(userId);
                }
            }

            ===== END FILE 6 =====

            FILE 7: SkillController.java

            package com.skillgap.skill_ai.Controller;

            import com.skillgap.skill_ai.entity.Skill;
            import com.skillgap.skill_ai.Service.SkillService;
            import org.springframework.web.bind.annotation.*;
            import java.util.List;

            @RestController
            @RequestMapping("/api/skills")
            public class SkillController {

                private final SkillService skillService;

                public SkillController(SkillService skillService) {
                    this.skillService = skillService;
                }

                @PostMapping
                public Skill addSkill(@RequestBody Skill skill) {
                    return skillService.addSkill(skill);
                }

                @GetMapping
                public List<Skill> getAllSkills() {
                    return skillService.getAllSkills();
                }

                @GetMapping("/{id}")
                public Skill getSkillById(@PathVariable Long id) {
                    return skillService.getSkillById(id);
                }
            }

            ===== END FILE 7 =====

            FILE 8: UserSkillController.java

            package com.skillgap.skill_ai.Controller;

            import com.skillgap.skill_ai.entity.UserSkill;
            import com.skillgap.skill_ai.Service.UserSkillService;
            import org.springframework.web.bind.annotation.*;
            import java.util.List;

            @RestController
            @RequestMapping("/api/users/{userId}/skills")
            public class UserSkillController {

                private final UserSkillService userSkillService;

                public UserSkillController(
                    UserSkillService userSkillService) {
                    this.userSkillService = userSkillService;
                }

                @PostMapping("/{skillId}")
                public UserSkill addUserSkill(
                    @PathVariable Long userId,
                    @PathVariable Long skillId,
                    @RequestParam String proficiency) {

                    return userSkillService.addUserSkill(
                        userId, skillId, proficiency);
                }

                @GetMapping
                public List<UserSkill> getUserSkills(
                    @PathVariable Long userId) {
                    return userSkillService.getUserSkills(userId);
                }
            }

            ===== END FILE 8 =====

            ===== END SKILL ASSESSMENT SOURCE CODE =====

            """;
}

public String getSkillGapAnalysisCode() {

    return """

            ===== SKILL GAP ANALYSIS SOURCE CODE =====

            FILE 1: SkillGapService.java

            package com.skillgap.skill_ai.Service;

            import com.skillgap.skill_ai.entity.User;
            import com.skillgap.skill_ai.entity.UserSkill;
            import com.skillgap.skill_ai.entity.CareerRequiredSkill;
            import com.skillgap.skill_ai.dto.SkillGapResponse;
            import com.skillgap.skill_ai.Repository.UserRepository;
            import com.skillgap.skill_ai.Repository.UserSkillRepository;
            import com.skillgap.skill_ai.Repository.CareerRequiredSkillRepository;
            import org.springframework.stereotype.Service;
            import java.util.List;
            import java.util.ArrayList;

            @Service
            public class SkillGapService {

                private final UserRepository userRepository;
                private final UserSkillRepository userSkillRepository;
                private final CareerRequiredSkillRepository careerRequiredSkillRepository;

                public SkillGapService(
                        UserRepository userRepository,
                        UserSkillRepository userSkillRepository,
                        CareerRequiredSkillRepository careerRequiredSkillRepository) {

                    this.userRepository = userRepository;
                    this.userSkillRepository = userSkillRepository;
                    this.careerRequiredSkillRepository = careerRequiredSkillRepository;
                }

                public List<SkillGapResponse> analyzeSkillGap(Long userId) {

                    User user = userRepository.findById(userId)
                            .orElseThrow(() -> new RuntimeException("User not found"));

                    if (user.getCareerGoal() == null) {
                        throw new RuntimeException("User has not selected a career goal");
                    }

                    Long careerGoalId = user.getCareerGoal().getId();

                    List<UserSkill> userSkills =
                            userSkillRepository.findByUserId(userId);

                    List<CareerRequiredSkill> requiredSkills =
                            careerRequiredSkillRepository.findByCareerGoalId(careerGoalId);

                    List<SkillGapResponse> results = new ArrayList<>();

                    for (CareerRequiredSkill requiredSkill : requiredSkills) {

                        String skillName = requiredSkill.getSkill().getSkillName();
                        String requiredLevel = requiredSkill.getRequiredProficiency();
                        String currentLevel = null;

                        for (UserSkill userSkill : userSkills) {

                            if (userSkill.getSkill().getId()
                                    .equals(requiredSkill.getSkill().getId())) {

                                currentLevel = userSkill.getProficiency();
                                break;
                            }
                        }

                        String status;

                        if (currentLevel == null) {
                            status = "Missing";
                        } else if (getLevelValue(currentLevel)
                                >= getLevelValue(requiredLevel)) {
                            status = "Matched";
                        } else {
                            status = "Improve";
                        }

                        SkillGapResponse response = new SkillGapResponse(
                                skillName,
                                currentLevel == null ? "Not Available" : currentLevel,
                                requiredLevel,
                                status
                        );

                        results.add(response);
                    }

                    return results;
                }

                private int getLevelValue(String level) {

                    if (level == null) {
                        return 0;
                    }

                    switch (level.trim().toLowerCase()) {
                        case "beginner":
                            return 1;
                        case "intermediate":
                            return 2;
                        case "advanced":
                            return 3;
                        default:
                            throw new IllegalArgumentException(
                                    "Invalid proficiency level: " + level);
                    }
                }
            }

            ===== END FILE 1 =====


            FILE 2: SkillGapResponse.java

            package com.skillgap.skill_ai.dto;

            public class SkillGapResponse {

                private String skillName;
                private String currentProficiency;
                private String requiredProficiency;
                private String status;

                public SkillGapResponse() {
                }

                public SkillGapResponse(
                        String skillName,
                        String currentProficiency,
                        String requiredProficiency,
                        String status) {

                    this.skillName = skillName;
                    this.currentProficiency = currentProficiency;
                    this.requiredProficiency = requiredProficiency;
                    this.status = status;
                }

                public String getSkillName() {
                    return skillName;
                }

                public void setSkillName(String skillName) {
                    this.skillName = skillName;
                }

                public String getCurrentProficiency() {
                    return currentProficiency;
                }

                public void setCurrentProficiency(String currentProficiency) {
                    this.currentProficiency = currentProficiency;
                }

                public String getRequiredProficiency() {
                    return requiredProficiency;
                }

                public void setRequiredProficiency(String requiredProficiency) {
                    this.requiredProficiency = requiredProficiency;
                }

                public String getStatus() {
                    return status;
                }

                public void setStatus(String status) {
                    this.status = status;
                }
            }

            ===== END FILE 2 =====


            FILE 3: SkillGapController.java

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

            ===== END FILE 3 =====


            FILE 4: CareerRequiredSkill.java

            package com.skillgap.skill_ai.entity;

            import jakarta.persistence.*;
            import jakarta.validation.constraints.NotBlank;

            @Entity
            @Table(name = "career_required_skills")
            public class CareerRequiredSkill {

                @Id
                @GeneratedValue(strategy = GenerationType.IDENTITY)
                private Long id;

                @ManyToOne
                @JoinColumn(name = "career_goal_id", nullable = false)
                private CareerGoal careerGoal;

                @ManyToOne
                @JoinColumn(name = "skill_id", nullable = false)
                private Skill skill;

                @NotBlank(message = "Required proficiency cannot be empty")
                @Column(nullable = false)
                private String requiredProficiency;

                public CareerRequiredSkill() {
                }

                public CareerRequiredSkill(
                        CareerGoal careerGoal,
                        Skill skill,
                        String requiredProficiency) {
                    this.careerGoal = careerGoal;
                    this.skill = skill;
                    this.requiredProficiency = requiredProficiency;
                }

                public Long getId() {
                    return id;
                }

                public CareerGoal getCareerGoal() {
                    return careerGoal;
                }

                public void setCareerGoal(CareerGoal careerGoal) {
                    this.careerGoal = careerGoal;
                }

                public Skill getSkill() {
                    return skill;
                }

                public void setSkill(Skill skill) {
                    this.skill = skill;
                }

                public String getRequiredProficiency() {
                    return requiredProficiency;
                }

                public void setRequiredProficiency(String requiredProficiency) {
                    this.requiredProficiency = requiredProficiency;
                }
            }

            ===== END FILE 4 =====


            FILE 5: CareerRequiredSkillRepository.java

            package com.skillgap.skill_ai.Repository;

            import com.skillgap.skill_ai.entity.CareerRequiredSkill;
            import org.springframework.data.jpa.repository.JpaRepository;
            import org.springframework.stereotype.Repository;
            import java.util.List;

            @Repository
            public interface CareerRequiredSkillRepository
                    extends JpaRepository<CareerRequiredSkill, Long> {

                List<CareerRequiredSkill> findByCareerGoalId(Long careerGoalId);
            }

            ===== END FILE 5 =====


            FILE 6: CareerRequiredSkillService.java

            package com.skillgap.skill_ai.Service;

            import com.skillgap.skill_ai.entity.CareerGoal;
            import com.skillgap.skill_ai.entity.Skill;
            import com.skillgap.skill_ai.entity.CareerRequiredSkill;
            import com.skillgap.skill_ai.Repository.CareerGoalRepository;
            import com.skillgap.skill_ai.Repository.SkillRepository;
            import com.skillgap.skill_ai.Repository.CareerRequiredSkillRepository;
            import org.springframework.stereotype.Service;
            import java.util.List;

            @Service
            public class CareerRequiredSkillService {

                private final CareerRequiredSkillRepository careerRequiredSkillRepository;
                private final CareerGoalRepository careerGoalRepository;
                private final SkillRepository skillRepository;

                public CareerRequiredSkillService(
                        CareerRequiredSkillRepository careerRequiredSkillRepository,
                        CareerGoalRepository careerGoalRepository,
                        SkillRepository skillRepository) {

                    this.careerRequiredSkillRepository = careerRequiredSkillRepository;
                    this.careerGoalRepository = careerGoalRepository;
                    this.skillRepository = skillRepository;
                }

                public CareerRequiredSkill addRequiredSkill(
                        Long careerGoalId,
                        Long skillId,
                        String requiredProficiency) {

                    CareerGoal careerGoal = careerGoalRepository.findById(careerGoalId)
                            .orElseThrow(() -> new RuntimeException("Career goal not found"));

                    Skill skill = skillRepository.findById(skillId)
                            .orElseThrow(() -> new RuntimeException("Skill not found"));

                    CareerRequiredSkill requiredSkill = new CareerRequiredSkill(
                            careerGoal, skill, requiredProficiency);

                    return careerRequiredSkillRepository.save(requiredSkill);
                }

                public List<CareerRequiredSkill> getRequiredSkills(Long careerGoalId) {
                    return careerRequiredSkillRepository.findByCareerGoalId(careerGoalId);
                }
            }

            ===== END FILE 6 =====

            ===== END SKILL GAP ANALYSIS SOURCE CODE =====

            """;
}

public String getLearningRoadmapCode() {

    return """

    ====================================
    FILE: LearningRoadmap.java
    ====================================

    package com.skillgap.skill_ai.entity;

    import jakarta.persistence.*;
    import java.time.LocalDateTime;

    @Entity
    @Table(name = "learning_roadmaps")
    public class LearningRoadmap {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        private String skillName;
        private String currentProficiency;
        private String requiredProficiency;
        private String priority;

        @Column(length = 2000)
        private String recommendedTopics;

        private Integer sequenceOrder;
        private String status = "Pending";
        private LocalDateTime createdAt = LocalDateTime.now();

        @ManyToOne
        @JoinColumn(name = "user_id")
        private User user;

        public LearningRoadmap() {
        }

        public Long getId() {
            return id;
        }

        public String getSkillName() {
            return skillName;
        }

        public void setSkillName(String skillName) {
            this.skillName = skillName;
        }

        public String getCurrentProficiency() {
            return currentProficiency;
        }

        public void setCurrentProficiency(String currentProficiency) {
            this.currentProficiency = currentProficiency;
        }

        public String getRequiredProficiency() {
            return requiredProficiency;
        }

        public void setRequiredProficiency(String requiredProficiency) {
            this.requiredProficiency = requiredProficiency;
        }

        public String getPriority() {
            return priority;
        }

        public void setPriority(String priority) {
            this.priority = priority;
        }

        public String getRecommendedTopics() {
            return recommendedTopics;
        }

        public void setRecommendedTopics(String recommendedTopics) {
            this.recommendedTopics = recommendedTopics;
        }

        public Integer getSequenceOrder() {
            return sequenceOrder;
        }

        public void setSequenceOrder(Integer sequenceOrder) {
            this.sequenceOrder = sequenceOrder;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public LocalDateTime getCreatedAt() {
            return createdAt;
        }

        public User getUser() {
            return user;
        }

        public void setUser(User user) {
            this.user = user;
        }
    }

    ====================================
    FILE: LearningRoadmapRepository.java
    ====================================

    package com.skillgap.skill_ai.Repository;

    import com.skillgap.skill_ai.entity.LearningRoadmap;
    import org.springframework.data.jpa.repository.JpaRepository;
    import java.util.List;

    public interface LearningRoadmapRepository
            extends JpaRepository<LearningRoadmap, Long> {

        List<LearningRoadmap> findByUserIdOrderBySequenceOrderAsc(Long userId);

        long countByUserId(Long userId);

        long countByUserIdAndStatus(Long userId, String status);
    }

    ====================================
    FILE: LearningRoadmapService.java
    ====================================

    package com.skillgap.skill_ai.Service;

    import com.skillgap.skill_ai.dto.SkillGapResponse;
    import com.skillgap.skill_ai.entity.*;
    import com.skillgap.skill_ai.Repository.*;
    import org.springframework.stereotype.Service;
    import com.skillgap.skill_ai.dto.RoadmapProgressResponse;
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
                        gap.getStatus().equals("Missing")
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

    ====================================
    FILE: LearningRoadmapController.java
    ====================================

    package com.skillgap.skill_ai.Controller;

    import com.skillgap.skill_ai.entity.LearningRoadmap;
    import com.skillgap.skill_ai.Service.LearningRoadmapService;
    import org.springframework.web.bind.annotation.*;
    import java.util.List;
    import com.skillgap.skill_ai.dto.RoadmapProgressResponse;

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

    ====================================
    FILE: RoadmapProgressResponse.java
    ====================================

    package com.skillgap.skill_ai.dto;

    public class RoadmapProgressResponse {

        private long totalTasks;
        private long completedTasks;
        private long pendingTasks;
        private double progressPercentage;

        public RoadmapProgressResponse(
                long totalTasks,
                long completedTasks,
                long pendingTasks,
                double progressPercentage) {

            this.totalTasks = totalTasks;
            this.completedTasks = completedTasks;
            this.pendingTasks = pendingTasks;
            this.progressPercentage = progressPercentage;
        }

        public long getTotalTasks() {
            return totalTasks;
        }

        public long getCompletedTasks() {
            return completedTasks;
        }

        public long getPendingTasks() {
            return pendingTasks;
        }

        public double getProgressPercentage() {
            return progressPercentage;
        }
    }

    """;
}



}

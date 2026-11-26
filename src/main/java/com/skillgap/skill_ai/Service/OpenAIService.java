
package com.skillgap.skill_ai.Service;

import com.openai.client.OpenAIClient;
import com.openai.models.ChatModel;
import com.openai.models.chat.completions.ChatCompletion;
import com.openai.models.chat.completions.ChatCompletionCreateParams;

import com.skillgap.skill_ai.dto.SkillGapResponse;
import com.skillgap.skill_ai.entity.Message;
import com.skillgap.skill_ai.entity.User;
import com.skillgap.skill_ai.entity.CareerGoal;
import com.skillgap.skill_ai.entity.LearningRoadmap;

import com.skillgap.skill_ai.Repository.UserRepository;
import com.skillgap.skill_ai.Repository.LearningRoadmapRepository;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OpenAIService {

    private final OpenAIClient openAIClient;
    private final SkillGapService skillGapService;
    private final UserRepository userRepository;
    private final LearningRoadmapRepository learningRoadmapRepository;
    private final ProjectCodeContextService projectCodeContextService;

    public OpenAIService(
            OpenAIClient openAIClient,
            SkillGapService skillGapService,
            UserRepository userRepository,
            LearningRoadmapRepository learningRoadmapRepository,
            ProjectCodeContextService projectCodeContextService) {

        this.openAIClient = openAIClient;
        this.skillGapService = skillGapService;
        this.userRepository = userRepository;
        this.learningRoadmapRepository = learningRoadmapRepository;
        this.projectCodeContextService = projectCodeContextService;
    }

    public String generateCareerResponse(
            Long userId,
            String userMessage,
            List<Message> chatHistory) {

        // STEP 1: Retrieve student information

        User user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "User not found: " + userId));

        String studentName = user.getName();

        // STEP 2: Retrieve career goal

        CareerGoal careerGoal = user.getCareerGoal();

        String careerName = "Not selected";
        String careerDescription = "No career goal available.";

        if (careerGoal != null) {

            careerName = careerGoal.getCareerName();

            careerDescription = careerGoal.getDescription() != null
                    ? careerGoal.getDescription()
                    : "No description available.";
        }

        // STEP 3: Retrieve skill-gap information

        List<SkillGapResponse> skillGaps =
                skillGapService.analyzeSkillGap(userId);

        StringBuilder skillInformation = new StringBuilder();

        if (skillGaps.isEmpty()) {

            skillInformation.append(
                    "No skill-gap information available.");

        } else {

            int skillLimit = Math.min(skillGaps.size(), 8);

            for (int i = 0; i < skillLimit; i++) {

                SkillGapResponse gap = skillGaps.get(i);

                skillInformation.append("\nSkill: ")
                        .append(gap.getSkillName());

                skillInformation.append("\nCurrent Proficiency: ")
                        .append(gap.getCurrentProficiency());

                skillInformation.append("\nRequired Proficiency: ")
                        .append(gap.getRequiredProficiency());

                skillInformation.append("\nStatus: ")
                        .append(gap.getStatus());

                skillInformation.append("\n--------------------\n");
            }
        }

        // STEP 4: Retrieve learning roadmap information

        List<LearningRoadmap> roadmap =
                learningRoadmapRepository
                        .findByUserIdOrderBySequenceOrderAsc(userId);

        StringBuilder roadmapInformation = new StringBuilder();

        if (roadmap.isEmpty()) {

            roadmapInformation.append(
                    "No learning roadmap has been generated yet.");

        } else {

            int taskLimit = Math.min(roadmap.size(), 5);

            for (int i = 0; i < taskLimit; i++) {

                LearningRoadmap task = roadmap.get(i);

                roadmapInformation.append("\nSequence: ")
                        .append(task.getSequenceOrder());

                roadmapInformation.append("\nSkill: ")
                        .append(task.getSkillName());

                roadmapInformation.append("\nCurrent Level: ")
                        .append(task.getCurrentProficiency());

                roadmapInformation.append("\nRequired Level: ")
                        .append(task.getRequiredProficiency());

                roadmapInformation.append("\nPriority: ")
                        .append(task.getPriority());

                roadmapInformation.append("\nRecommended Topics: ")
                        .append(task.getRecommendedTopics());

                roadmapInformation.append("\nProgress Status: ")
                        .append(task.getStatus());

                roadmapInformation.append("\n--------------------\n");
            }
        }

        // STEP 5: Select only the relevant project source code

        String routingText = userMessage.toLowerCase();

        // For follow-up questions, use the latest previous user question
        // only when the current question does not identify a module.

        if (!containsModuleKeyword(routingText)
                && chatHistory != null) {

            for (int i = chatHistory.size() - 1; i >= 0; i--) {

                Message message = chatHistory.get(i);

                if ("USER".equalsIgnoreCase(
                        String.valueOf(message.getSender()))
                        && message.getContent() != null
                        && containsModuleKeyword(
                                message.getContent().toLowerCase())) {

                    routingText = message.getContent().toLowerCase();
                    break;
                }
            }
        }

        String relevantProjectCode = "";

        // Learning Roadmap

        if (routingText.contains("roadmap")
                || routingText.contains("learningroadmap")
                || routingText.contains("generateroadmap")
                || routingText.contains("updateprogress")
                || routingText.contains("sequenceorder")
                || routingText.contains("recommendedtopics")) {

            relevantProjectCode =
                    "LEARNING ROADMAP SOURCE CODE:\n"
                    + projectCodeContextService
                            .getLearningRoadmapCode();

        }

        // Skill Gap Analysis

        else if (routingText.contains("skill gap")
                || routingText.contains("skillgap")
                || routingText.contains("analyzeskillgap")
                || routingText.contains("careerrequiredskill")) {

            relevantProjectCode =
                    "SKILL GAP ANALYSIS SOURCE CODE:\n"
                    + projectCodeContextService
                            .getSkillGapAnalysisCode();

        }

        // Skill Assessment

        else if (routingText.contains("adduserskill")
                || routingText.contains("userskill")
                || routingText.contains("skill assessment")
                || routingText.contains("skillassessment")
                || routingText.contains("proficiency")
                || routingText.contains("skillcontroller")) {

            relevantProjectCode =
                    "SKILL ASSESSMENT SOURCE CODE:\n"
                    + projectCodeContextService
                            .getSkillAssessmentCode();

        }

        // User Management

        else if (routingText.contains("usermanagement")
                || routingText.contains("user management")
                || routingText.contains("assigncareergoal")
                || routingText.contains("careergoal")
                || routingText.contains("career goal")
                || routingText.contains("userservice")
                || routingText.contains("userrepository")) {

            relevantProjectCode =
                    "USER MANAGEMENT SOURCE CODE:\n"
                    + projectCodeContextService
                            .getUserManagementCode();

        }

        if (relevantProjectCode.isEmpty()) {

            relevantProjectCode =
                    "No specific module source code was selected. "
                    + "Do not invent actual implementation details. "
                    + "Ask for the relevant source code if necessary.";
        }

        // STEP 6: Prepare conversation history

        StringBuilder conversationHistory = new StringBuilder();

        if (chatHistory != null && !chatHistory.isEmpty()) {

            int startIndex = Math.max(0, chatHistory.size() - 4);

            for (int i = startIndex; i < chatHistory.size(); i++) {

                Message message = chatHistory.get(i);

                String content = message.getContent();

                if (content != null && content.length() > 200) {
                    content = content.substring(0, 200) + "...";
                }

                conversationHistory.append("\n")
                        .append(message.getSender())
                        .append(": ")
                        .append(content)
                        .append("\n");
            }

        } else {

            conversationHistory.append(
                    "This is the beginning of the conversation.");
        }

        // STEP 7: Build personalized AI prompt

        String prompt = """

                You are SkillSpring AI, a friendly career and skill mentor.

                Help the student with:
                - Career guidance
                - Programming concepts
                - Skill-gap analysis
                - Learning roadmaps
                - Project recommendations
                - Technical interview preparation

                ====================================
                EXISTING PROJECT
                ====================================

                Project: SkillSpring AI

                Technology stack:
                - Java
                - Spring Boot
                - MySQL
                - Postman
                - Groq API
                - OpenAI-compatible Java SDK

                Existing modules:
                1. User Management
                2. Chat Management
                3. AI Chatbot
                4. Career Goals
                5. Skill Assessment
                6. Skill Gap Analysis
                7. Personalized Learning Roadmap
                8. Career Recommendations
                9. Project Recommendations

                ====================================
                STRICT ACCURACY RULES
                ====================================

                1. Preserve the existing Java, Spring Boot and MySQL stack.
                2. Never suggest replacing the stack with unrelated technologies.
                3. Do not rebuild existing features.
                4. Never invent actual class names, fields, methods or endpoints.
                5. Use supplied source code when explaining actual implementation.
                6. If source code is unavailable, clearly say so.
                7. Distinguish simplified examples from actual project code.
                8. Never invent student details, skills or proficiency levels.
                9. Never address the student using an unverified name.
                10. Use the existing Groq integration for AI chatbot questions.

                ====================================
                STUDENT PROFILE
                ====================================

                Student name:
                """ + studentName + """

                Career goal:
                """ + careerName + """

                Career description:
                """ + careerDescription + """

                ====================================
                STUDENT SKILL GAP
                ====================================

                """ + skillInformation + """

                ====================================
                EXISTING LEARNING ROADMAP
                ====================================

                """ + roadmapInformation + """

                ====================================
                PREVIOUS CONVERSATION
                ====================================

                """ + conversationHistory + """

                ====================================
                RELEVANT ACTUAL PROJECT SOURCE CODE
                ====================================

                The following source code was selected for the
                student's current question.

                Rules:
                - Read the supplied source code before answering.
                - Use actual class names and method names.
                - Identify only actual constructor dependencies.
                - Do not invent fields, methods or endpoints.
                - Explain actual implementation accurately.
                - If source code is not supplied, say so.

                ACTUAL SOURCE CODE:

                """ + relevantProjectCode + """

                ====================================
                PERSONALIZATION INSTRUCTIONS
                ====================================

                1. Consider the student's selected career goal.
                2. Use actual skill-gap information.
                3. Matched skills have achieved the required level.
                4. Missing means no proficiency is recorded,
                   not necessarily that the student knows nothing.
                5. Improve means further learning is required.
                6. Consider completed and pending roadmap tasks.
                7. Never claim a task is completed unless its
                   actual status is Completed.
                8. Understand follow-up questions using conversation history.
                9. Do not repeat the entire previous answer unnecessarily.
                10. Never claim an unimplemented feature exists.

                ====================================
                RESPONSE FORMAT
                ====================================

                - Use clear headings and bullet points.
                - Keep explanations beginner-friendly.
                - Explain technical terms simply.
                - Give practical examples.
                - Provide step-by-step instructions when needed.
                - Use Java and Spring Boot for backend examples.
                - Answer the actual question directly.

                ====================================
                CURRENT STUDENT QUESTION
                ====================================

                """ + userMessage;

        // STEP 8: Send prompt to Groq AI

        ChatCompletionCreateParams params =
                ChatCompletionCreateParams.builder()
                        .model(ChatModel.of("openai/gpt-oss-20b"))
                        .addUserMessage(prompt)
                        .build();

        ChatCompletion response =
                openAIClient.chat()
                        .completions()
                        .create(params);

        // STEP 9: Return AI response

        return response.choices()
                .get(0)
                .message()
                .content()
                .orElse("Sorry, I couldn't generate a response.");
    }

    // Checks whether a message identifies a known project module

    private boolean containsModuleKeyword(String message) {

        return message.contains("roadmap")
                || message.contains("learningroadmap")
                || message.contains("generateroadmap")
                || message.contains("updateprogress")
                || message.contains("sequenceorder")
                || message.contains("recommendedtopics")
                || message.contains("skill gap")
                || message.contains("skillgap")
                || message.contains("analyzeskillgap")
                || message.contains("careerrequiredskill")
                || message.contains("adduserskill")
                || message.contains("userskill")
                || message.contains("skill assessment")
                || message.contains("skillassessment")
                || message.contains("proficiency")
                || message.contains("skillcontroller")
                || message.contains("usermanagement")
                || message.contains("user management")
                || message.contains("assigncareergoal")
                || message.contains("careergoal")
                || message.contains("career goal")
                || message.contains("userservice")
                || message.contains("userrepository");
    }
}

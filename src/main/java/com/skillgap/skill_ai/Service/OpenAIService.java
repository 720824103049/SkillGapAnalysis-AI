
package com.skillgap.skill_ai.Service;

import org.springframework.stereotype.Service;

@Service
public class OpenAIService {

    public String generateCareerResponse(String userMessage) {

        String message = userMessage.toLowerCase();

        if (message.contains("java")) {
            return """
                    Great! Java is an excellent choice for backend development.

                    Here is your learning roadmap:

                    1. Learn Java fundamentals.
                    2. Practice OOP concepts.
                    3. Learn Collections and Exception Handling.
                    4. Study SQL and MySQL.
                    5. Learn Spring Boot.
                    6. Build REST APIs.
                    7. Practice DSA.
                    8. Build a backend project.

                    Recommended project:
                    Employee Management System using Spring Boot and MySQL.

                    Start by practicing Java programs every day!
                    """;
        }

        if (message.contains("python")) {
            return """
                    Python is a great programming language!

                    Your learning roadmap:

                    1. Python fundamentals.
                    2. Functions and OOP.
                    3. Lists, Tuples and Dictionaries.
                    4. File Handling.
                    5. SQL and Database Connectivity.
                    6. Learn Flask or Django.
                    7. Build practical projects.

                    Recommended project:
                    Student Performance Analysis.
                    """;
        }

        if (message.contains("career") || message.contains("job")) {
            return """
                    Let's plan your career!

                    1. Identify your area of interest.
                    2. Choose a career path.
                    3. Learn the required technical skills.
                    4. Practice coding and problem solving.
                    5. Build 2 or 3 projects.
                    6. Prepare your resume.
                    7. Practice technical and HR interviews.

                    Consistency is the key to career growth!
                    """;
        }

        return """
                Welcome to SkillSpring AI!

                I am your Career and Skill Mentor.

                I can help you with:

                1. Career guidance.
                2. Programming languages.
                3. Technical skill roadmaps.
                4. Project recommendations.
                5. Placement preparation.

                Ask me something about your career or skills!
                """;
    }
}

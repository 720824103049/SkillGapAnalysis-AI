
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

    // Register a new user
    public User registerUser(User user) {
        return userRepository.save(user);
    }

    // Get all users
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // Get user by ID
    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "User not found with ID: " + id));
    }

    // Assign career goal to user
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

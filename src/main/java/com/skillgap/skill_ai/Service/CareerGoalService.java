
package com.skillgap.skill_ai.Service;

import com.skillgap.skill_ai.entity.CareerGoal;
import com.skillgap.skill_ai.Repository.CareerGoalRepository;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CareerGoalService {

    private final CareerGoalRepository careerGoalRepository;

    public CareerGoalService(CareerGoalRepository careerGoalRepository) {
        this.careerGoalRepository = careerGoalRepository;
    }

    // Create a new career goal
    public CareerGoal createCareerGoal(CareerGoal careerGoal) {
        return careerGoalRepository.save(careerGoal);
    }

    // Get all career goals
    public List<CareerGoal> getAllCareerGoals() {
        return careerGoalRepository.findAll();
    }

    // Get career goal by ID
    public CareerGoal getCareerGoalById(Long id) {
        return careerGoalRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Career goal not found with ID: " + id));
    }
}

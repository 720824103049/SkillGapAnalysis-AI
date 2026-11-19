
package com.skillgap.skill_ai.Controller;

import com.skillgap.skill_ai.entity.CareerGoal;
import com.skillgap.skill_ai.Service.CareerGoalService;

import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/api/career-goals")
public class CareerGoalController {

    private final CareerGoalService careerGoalService;

    public CareerGoalController(CareerGoalService careerGoalService) {
        this.careerGoalService = careerGoalService;
    }

    // Create a career goal
    @PostMapping
    public CareerGoal createCareerGoal(
            @Valid @RequestBody CareerGoal careerGoal) {

        return careerGoalService.createCareerGoal(careerGoal);
    }

    // Get all career goals
    @GetMapping
    public List<CareerGoal> getAllCareerGoals() {

        return careerGoalService.getAllCareerGoals();
    }

    // Get career goal by ID
    @GetMapping("/{id}")
    public CareerGoal getCareerGoalById(@PathVariable Long id) {

        return careerGoalService.getCareerGoalById(id);
    }
}

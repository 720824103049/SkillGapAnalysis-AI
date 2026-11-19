
package com.skillgap.skill_ai.Controller;

import com.skillgap.skill_ai.entity.User;
import com.skillgap.skill_ai.Service.UserService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // Register a new user
    @PostMapping
    public User registerUser(@Valid @RequestBody User user) {
        return userService.registerUser(user);
    }

    // Get all users
    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    // Get user by ID
    @GetMapping("/{id}")
    public User getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    // Assign career goal to user
    @PutMapping("/{userId}/career-goal/{careerGoalId}")
    public User assignCareerGoal(
            @PathVariable Long userId,
            @PathVariable Long careerGoalId) {

        return userService.assignCareerGoal(userId, careerGoalId);
    }

}

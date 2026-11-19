
package com.skillgap.skill_ai.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "career_goals")
public class CareerGoal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Career name cannot be empty")
    @Column(nullable = false, unique = true)
    private String careerName;

    private String description;

    public CareerGoal() {
    }

    public CareerGoal(String careerName, String description) {
        this.careerName = careerName;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public String getCareerName() {
        return careerName;
    }

    public void setCareerName(String careerName) {
        this.careerName = careerName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

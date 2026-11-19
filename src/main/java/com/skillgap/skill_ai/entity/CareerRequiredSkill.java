
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

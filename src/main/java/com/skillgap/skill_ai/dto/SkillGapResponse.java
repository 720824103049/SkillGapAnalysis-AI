
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

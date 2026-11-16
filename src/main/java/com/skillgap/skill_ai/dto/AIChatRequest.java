
package com.skillgap.skill_ai.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class AIChatRequest {

    @NotBlank(message = "Message cannot be empty")
    @Size(max = 5000, message = "Message cannot exceed 5000 characters")
    private String message;

    public AIChatRequest() {
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

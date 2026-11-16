
package com.skillgap.skill_ai.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class ChatRequest {

    @NotBlank(message = "Chat title cannot be empty")
    @Size(max = 100, message = "Chat title cannot exceed 100 characters")
    private String title;

    public ChatRequest() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}


package com.skillgap.skill_ai.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class MessageRequest {

    @NotBlank(message = "Message content cannot be empty")
    @Size(max = 5000, message = "Message cannot exceed 5000 characters")
    private String content;

    @NotBlank(message = "Sender cannot be empty")
    @Pattern(regexp = "USER|AI", message = "Sender must be USER or AI")
    private String sender;

    public MessageRequest() {
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }
}

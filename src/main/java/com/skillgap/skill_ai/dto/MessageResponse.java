
package com.skillgap.skill_ai.dto;

public class MessageResponse {

    private Long id;
    private String content;
    private String sender;

    public MessageResponse(Long id, String content, String sender) {
        this.id = id;
        this.content = content;
        this.sender = sender;
    }

    public Long getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public String getSender() {
        return sender;
    }
}

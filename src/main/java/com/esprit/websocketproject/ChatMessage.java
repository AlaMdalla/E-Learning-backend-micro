package com.esprit.websocketproject;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDateTime;

@Entity
public class ChatMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String senderId;
    private String recipientId;
    private String content;
    private String voiceNoteUrl; // New field for voice note URL
    private LocalDateTime timestamp;

    public ChatMessage() {
    }

    public ChatMessage(String senderId, String recipientId, String content, String voiceNoteUrl, LocalDateTime timestamp) {
        this.senderId = senderId;
        this.recipientId = recipientId;
        this.content = content;
        this.voiceNoteUrl = voiceNoteUrl;
        this.timestamp = timestamp;
    }

    // Getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getSenderId() { return senderId; }
    public void setSenderId(String senderId) { this.senderId = senderId; }
    public String getRecipientId() { return recipientId; }
    public void setRecipientId(String recipientId) { this.recipientId = recipientId; }
    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
    public String getVoiceNoteUrl() { return voiceNoteUrl; }
    public void setVoiceNoteUrl(String voiceNoteUrl) { this.voiceNoteUrl = voiceNoteUrl; }
    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }
}
package com.esprit.websocketproject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("chat/api/messages")
public class ChatMessageController {

    @Autowired
    private ChatMessageRepository messageRepository;

    private final String uploadDir = "uploads/voice-notes/";
    private final String baseUrl = "http://localhost:8090";
    private final long MAX_FILE_SIZE = 5 * 1024 * 1024; // 5MB in bytes

    @PostMapping("/upload-voice-note")
    public ResponseEntity<ChatMessage> uploadVoiceNote(
            @RequestParam("file") MultipartFile file,
            @RequestParam("senderId") String senderId,
            @RequestParam("recipientId") String recipientId
    ) {
        if (file.isEmpty() || senderId == null || recipientId == null) {
            return ResponseEntity.badRequest().body(null);
        }

        // Validate file size
        if (file.getSize() > MAX_FILE_SIZE) {
            return ResponseEntity.status(413).body(null); // 413 Payload Too Large
        }

        try {
            // Create upload directory if it doesn't exist
            File directory = new File(uploadDir);
            if (!directory.exists()) {
                directory.mkdirs();
            }

            // Generate unique filename
            String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
            Path filePath = Paths.get(uploadDir + fileName);
            Files.write(filePath, file.getBytes());

            // Save message with absolute voice note URL
            ChatMessage message = new ChatMessage();
            message.setSenderId(senderId);
            message.setRecipientId(recipientId);
            message.setVoiceNoteUrl(baseUrl + "/voice-notes/" + fileName);
            message.setTimestamp(LocalDateTime.now());
            message = messageRepository.save(message);

            return ResponseEntity.ok(message);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(500).build();
        }
    }

    @GetMapping("/history")
    public ResponseEntity<List<ChatMessage>> getMessages(
            @RequestParam String userId,
            @RequestParam String recipientId
    ) {
        if (userId == null || userId.trim().isEmpty() || recipientId == null || recipientId.trim().isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        List<ChatMessage> messages = messageRepository.findMessages(userId, recipientId);
        messages.forEach(msg -> {
            if (msg.getVoiceNoteUrl() != null && msg.getVoiceNoteUrl().startsWith("/voice-notes/")) {
                msg.setVoiceNoteUrl(baseUrl + msg.getVoiceNoteUrl());
            }
        });
        return ResponseEntity.ok(messages);
    }
}
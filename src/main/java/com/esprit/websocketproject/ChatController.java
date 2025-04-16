    package com.esprit.websocketproject;

    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.messaging.handler.annotation.MessageMapping;
    import org.springframework.messaging.simp.SimpMessagingTemplate;
    import org.springframework.stereotype.Controller;
    import java.time.LocalDateTime;

    @Controller
    public class ChatController {

        @Autowired
        private SimpMessagingTemplate messagingTemplate;

        @Autowired
        private ChatMessageRepository messageRepository;

        @MessageMapping("/private")
        public void handlePrivateMessage(ChatMessage message) {
            if (message.getSenderId() == null || message.getRecipientId() == null) {
                System.err.println("Invalid message: senderId or recipientId is missing");
                return;
            }
            if ((message.getContent() == null || message.getContent().trim().isEmpty()) && message.getVoiceNoteUrl() == null) {
                System.err.println("Invalid message: both content and voiceNoteUrl are missing or empty");
                return;
            }
            try {
                message.setTimestamp(LocalDateTime.now());
                System.out.println("Received message from " + message.getSenderId() + " to " + message.getRecipientId());
                ChatMessage savedMessage = messageRepository.save(message);
                messagingTemplate.convertAndSendToUser(
                        message.getRecipientId(), "/private", savedMessage);
                messagingTemplate.convertAndSendToUser(
                        message.getSenderId(), "/private", savedMessage);
            } catch (Exception e) {
                System.err.println("Error processing message: " + e.getMessage());
                e.printStackTrace();
            }
        }}
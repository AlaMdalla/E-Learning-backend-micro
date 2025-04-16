package com.esprit.websocketproject;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {
    @Query("SELECT m FROM ChatMessage m WHERE " +
            "(m.senderId = :userId AND m.recipientId = :recipientId) OR " +
            "(m.senderId = :recipientId AND m.recipientId = :userId) " +
            "ORDER BY m.timestamp ASC")
    List<ChatMessage> findMessages(
            @Param("userId") String userId,
            @Param("recipientId") String recipientId
    );
}
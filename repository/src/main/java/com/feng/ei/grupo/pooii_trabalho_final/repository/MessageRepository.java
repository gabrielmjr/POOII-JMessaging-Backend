package com.feng.ei.grupo.pooii_trabalho_final.repository;

import com.feng.ei.grupo.pooii_trabalho_final.domain.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Repository
public interface MessageRepository extends JpaRepository<Message, UUID> {

    @Query("SELECT m FROM message m WHERE m.toId = :userId AND m.delivered = false")
    List<Message> getAllNotReceivedByUser(UUID userId);

    @Modifying
    @Transactional
    @Query("UPDATE message m SET m.delivered = true WHERE m.id = :messageId")
    void markAsRead(UUID messageId);
}

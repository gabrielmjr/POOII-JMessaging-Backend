package com.feng.ei.grupo.pooii_trabalho_final.repository;

import com.feng.ei.grupo.pooii_trabalho_final.domain.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface MessageRepository extends JpaRepository<Message, UUID> {
}

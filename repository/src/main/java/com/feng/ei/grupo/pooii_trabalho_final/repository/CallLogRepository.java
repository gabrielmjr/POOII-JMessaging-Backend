package com.feng.ei.grupo.pooii_trabalho_final.repository;

import com.feng.ei.grupo.pooii_trabalho_final.domain.CallLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CallLogRepository extends JpaRepository<CallLog, UUID> {
}

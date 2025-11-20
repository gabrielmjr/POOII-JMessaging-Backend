package com.feng.ei.grupo.pooii_trabalho_final.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "call_logs")
public class CallLog {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(name = "_timestamp")
    private OffsetDateTime timestamp;
    private String status;
    private String duration;

    @ManyToOne
    @JoinColumn(name = "from_id")
    private User from;

    @ManyToOne
    @JoinColumn(name = "to_id")
    private User to;
}

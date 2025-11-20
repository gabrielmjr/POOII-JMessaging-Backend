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
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "messages")
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "from_id")
    private User from;

    @ManyToOne
    @JoinColumn(name = "to_id")
    private User to;

    @Column(name = "_timestamp")
    private OffsetDateTime timestamp;
    private String content;
    private boolean delivered;
}

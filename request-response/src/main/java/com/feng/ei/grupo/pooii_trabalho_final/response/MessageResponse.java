package com.feng.ei.grupo.pooii_trabalho_final.response;

import lombok.Builder;
import lombok.Data;

import java.time.OffsetDateTime;

@Data
@Builder
public class MessageResponse {
    private String fromEmail;
    private OffsetDateTime timestamp;
    private String content;
}

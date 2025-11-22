package com.feng.ei.grupo.pooii_trabalho_final.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserResponse {
    private String username;
    private String email;
    private String lastSeen;
}

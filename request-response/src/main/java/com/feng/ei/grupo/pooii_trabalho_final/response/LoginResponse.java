package com.feng.ei.grupo.pooii_trabalho_final.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginResponse {
    private String username;
    private String email;
    private boolean verified;
    private String imageUrl;
}

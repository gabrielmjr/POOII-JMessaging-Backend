package com.feng.ei.grupo.pooii_trabalho_final.controller;

import com.feng.ei.grupo.pooii_trabalho_final.AuthenticationService;
import com.feng.ei.grupo.pooii_trabalho_final.request.LoginRequest;
import com.feng.ei.grupo.pooii_trabalho_final.request.SignupRequest;
import com.feng.ei.grupo.pooii_trabalho_final.response.LoginResponse;
import com.feng.ei.grupo.pooii_trabalho_final.response.SignupResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/v1/auth")
public class AuthController {
    @Autowired
    private AuthenticationService service;

    @PostMapping("/signup")
    public ResponseEntity<SignupResponse> signup(@RequestBody @Validated SignupRequest request) {
        return service.signup(request);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody @Validated LoginRequest request) {
        return service.login(request);
    }
}

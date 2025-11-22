package com.feng.ei.grupo.pooii_trabalho_final.controller;

import com.feng.ei.grupo.pooii_trabalho_final.UserService;
import com.feng.ei.grupo.pooii_trabalho_final.response.UserResponse;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/v1/user")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/all")
    public ResponseEntity<@NotNull List<UserResponse>> getAll() {
        return userService.getAll();
    }
}

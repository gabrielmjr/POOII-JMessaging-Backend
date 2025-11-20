package com.feng.ei.grupo.pooii_trabalho_final.controller;

import com.feng.ei.grupo.pooii_trabalho_final.MessageService;
import com.feng.ei.grupo.pooii_trabalho_final.request.SendMessageRequest;
import com.feng.ei.grupo.pooii_trabalho_final.response.MessageResponse;
import jakarta.validation.Valid;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/v1/message")
public class MessageController {
    @Autowired
    private MessageService messageService;

    @PostMapping("/send")
    public ResponseEntity<@NotNull Void> send(@RequestBody @Validated SendMessageRequest request) {
        return messageService.send(request);
    }

    @GetMapping
    public ResponseEntity<@NotNull List<MessageResponse>> getMessages() {
        return messageService.getMessages();
    }
}

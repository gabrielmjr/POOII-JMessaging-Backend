package com.feng.ei.grupo.pooii_trabalho_final;

import com.feng.ei.grupo.pooii_trabalho_final.domain.Message;
import com.feng.ei.grupo.pooii_trabalho_final.repository.MessageRepository;
import com.feng.ei.grupo.pooii_trabalho_final.repository.UserRepository;
import com.feng.ei.grupo.pooii_trabalho_final.request.SendMessageRequest;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;

@Service
public class MessageService {
    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthenticationService authenticationService;

    public ResponseEntity<@NotNull Void> send(@NotNull SendMessageRequest request) {
        var sender = authenticationService.getAuthenticatedUser();
        var receiver = userRepository.findByEmail(request.toEmail()).get();

        if (sender.equals(receiver))
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();

        var message = Message.builder()
                .from(sender)
                .to(receiver)
                .timestamp(OffsetDateTime.now())
                .content(request.content())
                .delivered(false)
                .build();

        messageRepository.save(message);
        return ResponseEntity.ok().build();
    }
}

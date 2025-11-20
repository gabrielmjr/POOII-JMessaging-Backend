package com.feng.ei.grupo.pooii_trabalho_final;

import com.feng.ei.grupo.pooii_trabalho_final.domain.Message;
import com.feng.ei.grupo.pooii_trabalho_final.repository.MessageRepository;
import com.feng.ei.grupo.pooii_trabalho_final.repository.UserRepository;
import com.feng.ei.grupo.pooii_trabalho_final.request.SendMessageRequest;
import com.feng.ei.grupo.pooii_trabalho_final.response.MessageResponse;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

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
                .fromId(sender.getId())
                .toId(receiver.getId())
                .timestamp(OffsetDateTime.now())
                .content(request.content())
                .delivered(false)
                .build();

        messageRepository.save(message);
        return ResponseEntity.ok().build();
    }

    public ResponseEntity<@NotNull List<MessageResponse>> getMessages() {
        var currentUser = authenticationService.getAuthenticatedUser();
        var responseMessages = new ArrayList<MessageResponse>();
        var notReadMessages = messageRepository.getAllNotReceivedByUser(currentUser.getId());

        for (var message : notReadMessages) {
            var senderEmail = userRepository.getById(message.getFromId()).getEmail();

            responseMessages.add(MessageResponse.builder()
                    .fromEmail(senderEmail)
                    .timestamp(message.getTimestamp())
                    .content(message.getContent())
                    .build());

            messageRepository.markAsRead(message.getId());
        }

        return ResponseEntity.ok(responseMessages);
    }
}

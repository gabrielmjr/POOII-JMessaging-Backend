package com.feng.ei.grupo.pooii_trabalho_final;

import com.feng.ei.grupo.pooii_trabalho_final.domain.Session;
import com.feng.ei.grupo.pooii_trabalho_final.domain.User;
import com.feng.ei.grupo.pooii_trabalho_final.repository.UserRepository;
import com.feng.ei.grupo.pooii_trabalho_final.response.UserResponse;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public ResponseEntity<@NotNull List<UserResponse>> getAll() {
        var session = (Session) Objects.requireNonNull(SecurityContextHolder.getContext().getAuthentication()).getPrincipal();
        var users = userRepository.findAll();
        var responseUsers = new ArrayList<UserResponse>();

        for (var user : users) {
            assert session != null;
            if (!session.getUserId().equals(user.getId()))
                responseUsers.add(UserResponse.builder()
                        .username(user.getUsername())
                        .email(user.getEmail())
                        .lastSeen(user.getLastSeenOn())
                        .build()
                );
        }

        return ResponseEntity.ok(responseUsers);
    }
}

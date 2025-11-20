package com.feng.ei.grupo.pooii_trabalho_final;

import com.feng.ei.grupo.pooii_trabalho_final.domain.Session;
import com.feng.ei.grupo.pooii_trabalho_final.domain.User;
import com.feng.ei.grupo.pooii_trabalho_final.repository.SessionRepository;
import com.feng.ei.grupo.pooii_trabalho_final.repository.UserRepository;
import com.feng.ei.grupo.pooii_trabalho_final.request.LoginRequest;
import com.feng.ei.grupo.pooii_trabalho_final.request.SignupRequest;
import com.feng.ei.grupo.pooii_trabalho_final.response.LoginResponse;
import com.feng.ei.grupo.pooii_trabalho_final.response.SignupResponse;
import com.feng.ei.grupo.pooii_trabalho_final.security.JWTService;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.Objects;
import java.util.UUID;

@Service
public class AuthenticationService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SessionRepository sessionRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JWTService jwtService;

    public ResponseEntity<@NotNull SignupResponse> signup(@NotNull SignupRequest request) {
        var userOptional = userRepository.findByEmail(request.email());
        if (userOptional.isPresent())
            return ResponseEntity.status(HttpStatus.ALREADY_REPORTED)
                    .build();

        var user = User.builder()
                .username(request.username())
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .verified(true)
                .lastSeenOn("NEVER")
                .build();

        var session = Session.builder()
                .creationDate(OffsetDateTime.now())
                .status("ACTIVE")
                .userId(user.getId())
                .build();

        userRepository.save(user);
        var sessionId = sessionRepository.save(session);
        var authToken = jwtService.createToken(sessionId.getId());

        return ResponseEntity.ok(SignupResponse
                .builder()
                .authToken(authToken)
                .build());
    }

    public ResponseEntity<@NotNull LoginResponse> login(@NotNull LoginRequest request) {
        var userOptional = userRepository.findByEmail(request.email());
        if (userOptional.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .build();
        var user = userOptional.get();
        if (!passwordEncoder.matches(request.password(), user.getPassword()))
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .build();

        var session = Session.builder()
                .creationDate(OffsetDateTime.now())
                .status("ACTIVE")
                .userId(user.getId())
                .build();
        var sessionId = sessionRepository.save(session);
        var authToken = jwtService.createToken(sessionId.getId());
        var response = LoginResponse.builder()
                .username(user.getUsername())
                .email(user.getEmail())
                .verified(user.isVerified())
                .authToken(authToken)
                .build();
        return ResponseEntity.ok(response);
    }

    public User getAuthenticatedUser() {
        var userSession = (Session) Objects.requireNonNull(SecurityContextHolder.getContext().getAuthentication()).getPrincipal();
        assert userSession != null;
        return userRepository.findById(userSession.getUserId()).get();
    }
}

package com.feng.ei.grupo.pooii_trabalho_final;

import com.feng.ei.grupo.pooii_trabalho_final.domain.User;
import com.feng.ei.grupo.pooii_trabalho_final.repository.UserRepository;
import com.feng.ei.grupo.pooii_trabalho_final.request.LoginRequest;
import com.feng.ei.grupo.pooii_trabalho_final.request.SignupRequest;
import com.feng.ei.grupo.pooii_trabalho_final.response.LoginResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public ResponseEntity<Void> signup(SignupRequest request) {
        var userOptional = userRepository.findByEmail(request.email());
        if (userOptional.isPresent())
            return ResponseEntity.status(HttpStatus.ALREADY_REPORTED)
                    .build();

        var user = User.builder()
                .username(request.username())
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .verified(true)
                .build();

        userRepository.save(user);
        return ResponseEntity.ok().build();
    }

    public ResponseEntity<LoginResponse> login(LoginRequest request) {
        var userOptional = userRepository.findByEmail(request.email());
        if (userOptional.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .build();
        var user = userOptional.get();
        var response = LoginResponse.builder()
                .username(user.getUsername())
                .email(user.getEmail())
                .verified(user.isVerified())
                .build();
        return ResponseEntity.ok(response);
    }
}

package com.feng.ei.grupo.pooii_trabalho_final.security;

import com.feng.ei.grupo.pooii_trabalho_final.repository.SessionRepository;
import com.feng.ei.grupo.pooii_trabalho_final.utils.CoreUtils;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.codec.Hex;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.UUID;

@Slf4j
@Service
public class JWTService {
    @Value("${spring.jwt.secret}")
    private String JWT_SECRET;

    @Autowired
    private SessionRepository sessionRepository;

    public boolean validateSessionToken(String sessionToken) {
        try {
            var sessionId = getSessionId(sessionToken);
            if (sessionId == null) {
                log.error("Empty session id, probably the token is tampered");
                return false;
            }

            var sessionOptional = sessionRepository.findById(sessionId);
            return sessionOptional.isPresent();
        } catch (Exception e) {
            log.error("Some exception thrown: {}", e.getMessage());
            return false;
        }
    }

    public String createToken(@NotNull UUID sessionId) {
        return Jwts.builder()
                .subject(sessionId.toString())
                .issuedAt(new Date())
                .expiration(CoreUtils.fifteenDaysFromNow())
                .signWith(getSignKey())
                .compact();
    }

    @Nullable
    public UUID getSessionId(String token) {
        try {
            var claims = Jwts.parser()
                    .verifyWith(getSignKey())
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
            return UUID.fromString(claims.getSubject());
        } catch (Exception e) {
            return null;
        }
    }

    @Contract(" -> new")
    private @NotNull SecretKey getSignKey() {
        return Keys.hmacShaKeyFor(Hex.decode(JWT_SECRET));
    }
}

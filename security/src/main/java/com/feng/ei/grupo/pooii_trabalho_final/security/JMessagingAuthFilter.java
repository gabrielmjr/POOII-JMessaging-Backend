package com.feng.ei.grupo.pooii_trabalho_final.security;

import com.feng.ei.grupo.pooii_trabalho_final.repository.SessionRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JMessagingAuthFilter extends OncePerRequestFilter {
    @Autowired
    private SessionRepository sessionRepository;

    @Autowired
    private JWTService jwtService;

    @Override
    protected void doFilterInternal(@NotNull HttpServletRequest request,
                                    @NotNull HttpServletResponse response,
                                    @NotNull FilterChain filterChain) throws ServletException, IOException {

        String authorization = request.getHeader("Authorization");
        if (authorization == null || !authorization.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        authorization = authorization.substring(7);

        var session = jwtService.validateSessionToken(authorization);
        if (session == null) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.flushBuffer();
            return;
        }

        if (!session.getStatus().equals("ACTIVE")) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.flushBuffer();
            return;
        }

        var authToken = new UsernamePasswordAuthenticationToken(session, null, null);
        authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authToken);
        filterChain.doFilter(request, response);
    }
}
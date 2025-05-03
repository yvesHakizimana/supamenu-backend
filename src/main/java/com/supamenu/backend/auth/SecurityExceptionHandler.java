package com.supamenu.backend.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ProblemDetail;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.URI;
import java.time.Instant;

@Component
@Slf4j
@RequiredArgsConstructor
public class SecurityExceptionHandler implements AuthenticationEntryPoint, AuthenticationFailureHandler, AccessDeniedHandler {

    private final ObjectMapper objectMapper;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        log.debug("Unauthorized access attempt: {}", authException.getMessage());
        handleSecurityExceptions(request, response, authException, HttpStatus.UNAUTHORIZED);
    }

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        log.debug("Authentication failure: {}", exception.getMessage());
        handleSecurityExceptions(request, response, exception, HttpStatus.FORBIDDEN);
    }

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        log.debug("Access denied: {}", accessDeniedException.getMessage());
        var problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.FORBIDDEN, accessDeniedException.getMessage());
        problemDetail.setInstance(URI.create(request.getRequestURI()));
        problemDetail.setProperty("timestamp", Instant.now().toString());

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpStatus.FORBIDDEN.value());
        objectMapper.writeValue(response.getWriter(), problemDetail);
    }

    private void handleSecurityExceptions(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException, HttpStatus status) throws IOException {
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        ProblemDetail problemDetail = ProblemDetail.forStatus(status);
        problemDetail.setDetail(authException.getMessage());
        problemDetail.setProperty("timestamp", Instant.now().toString());
        problemDetail.setInstance(URI.create(request.getRequestURI() + "?error"));

        response.setStatus(status.value());
        objectMapper.writeValue(response.getWriter(), problemDetail);
    }
}
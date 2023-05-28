package com.anilozmen.blogjwt.service.impl;

import com.anilozmen.blogjwt.entity.Role;
import com.anilozmen.blogjwt.entity.Token;
import com.anilozmen.blogjwt.entity.TokenType;
import com.anilozmen.blogjwt.entity.User;
import com.anilozmen.blogjwt.entity.dto.request.LoginRequest;
import com.anilozmen.blogjwt.entity.dto.request.RefreshToken;
import com.anilozmen.blogjwt.entity.dto.request.RegisterRequest;
import com.anilozmen.blogjwt.entity.dto.response.AuthenticationDto;
import com.anilozmen.blogjwt.exception.CustomDataNotFoundException;
import com.anilozmen.blogjwt.repository.TokenRepository;
import com.anilozmen.blogjwt.repository.UserRepository;
import com.anilozmen.blogjwt.service.AuthenticationService;
import com.anilozmen.blogjwt.service.JwtService;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    @Override
    public AuthenticationDto login(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        User user = userRepository
                .findByEmail(request.getEmail())
                .orElseThrow(() ->
                        new CustomDataNotFoundException("User with email [" + request.getEmail() + "] not found")
                );

        revokeAllUserTokens(user);

        return saveUserTokenAndReturnAuthResponse(user);
    }

    @Override
    public AuthenticationDto register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail()))
            throw new RuntimeException("User with email [" + request.getEmail() + "] already exists.");

        User user = userRepository.save(buildUser(request));
        return saveUserTokenAndReturnAuthResponse(user);
    }

    @Override
    public AuthenticationDto refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {

//        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        ServletInputStream stream = request.getInputStream();

        String refreshToken = getRefreshTokenFromRequestBody(stream);

        String userEmail;

        try {
            userEmail = jwtService.extractUsername(refreshToken);
        } catch (ExpiredJwtException e) {
            Token tokenData = tokenRepository.findByRefreshTokenAndExpiredFalseAndRevokedFalse(refreshToken)
                    .orElseThrow(() -> new RuntimeException("The refresh token is not valid!"));

            userEmail = tokenData.getUser().getEmail();
        }

        if (userEmail != null) {
            User user = userRepository.findByEmail(userEmail).orElseThrow();

            boolean isRefreshTokenValid = jwtService.isTokenValid(refreshToken, user);

            if (!isRefreshTokenValid)
                refreshToken = jwtService.generateRefreshToken(user);

            String accessToken = jwtService.generateToken(user);
            revokeAllUserTokens(user);
            saveToken(user, accessToken, refreshToken);

            return AuthenticationDto.builder()
                    .accessToken(accessToken)
                    .refreshToken(refreshToken)
                    .build();
        }

        throw new IOException("Something went wrong");
    }

    private User buildUser(RegisterRequest request) {
        User user = new User();
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(Role.USER);
        return user;
    }

    private AuthenticationDto saveUserTokenAndReturnAuthResponse(User user) {
        String jwtToken = jwtService.generateToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);

        saveToken(user, jwtToken, refreshToken);

        return AuthenticationDto.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .build();
    }

    private void saveToken(User user, String jwtToken, String refreshToken) {
        Token token = Token.builder()
                .user(user)
                .token(jwtToken)
                .refreshToken(refreshToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();

        tokenRepository.save(token);
    }

    private void revokeAllUserTokens(User user) {
        List<Token> validUserTokens = tokenRepository.findTokensByUserIdAndExpiredFalseAndRevokedFalse(user.getId());

        if (validUserTokens.isEmpty()) return;

        validUserTokens.forEach(t -> {
            t.setRevoked(true);
            t.setExpired(true);
        });

        tokenRepository.saveAll(validUserTokens);
    }

    private String getRefreshTokenFromRequestBody(ServletInputStream stream) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        RefreshToken myRefreshToken = objectMapper.readValue(stream, RefreshToken.class);
        return myRefreshToken.getRefreshToken();
    }
}

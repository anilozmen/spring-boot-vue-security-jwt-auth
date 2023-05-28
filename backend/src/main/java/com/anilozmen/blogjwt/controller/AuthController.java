package com.anilozmen.blogjwt.controller;

import com.anilozmen.blogjwt.entity.dto.request.LoginRequest;
import com.anilozmen.blogjwt.entity.dto.request.RegisterRequest;
import com.anilozmen.blogjwt.entity.dto.response.AuthenticationDto;
import com.anilozmen.blogjwt.service.AuthenticationService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationService authenticationService;

    @PostMapping("/login")
    public ResponseEntity<AuthenticationDto> login(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(authenticationService.login(request));
    }

    @PostMapping("/register")
    public ResponseEntity<AuthenticationDto> register(@RequestBody RegisterRequest request) {
        return new ResponseEntity<>(authenticationService.register(request), HttpStatus.CREATED);
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<AuthenticationDto> refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        return new ResponseEntity<>(authenticationService.refreshToken(request, response), HttpStatus.CREATED);
    }
}

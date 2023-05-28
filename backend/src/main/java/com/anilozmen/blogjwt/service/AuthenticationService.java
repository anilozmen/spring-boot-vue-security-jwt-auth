package com.anilozmen.blogjwt.service;

import com.anilozmen.blogjwt.entity.dto.request.LoginRequest;
import com.anilozmen.blogjwt.entity.dto.request.RegisterRequest;
import com.anilozmen.blogjwt.entity.dto.response.AuthenticationDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public interface AuthenticationService {
    AuthenticationDto login(LoginRequest request);

    AuthenticationDto register(RegisterRequest request);

    AuthenticationDto refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException;
}

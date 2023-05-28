package com.anilozmen.blogjwt.service;

import com.anilozmen.blogjwt.entity.dto.request.RegisterRequest;
import com.anilozmen.blogjwt.entity.dto.response.PostDto;
import com.anilozmen.blogjwt.entity.dto.response.UserDetailsDto;
import com.anilozmen.blogjwt.entity.dto.response.UserDto;

import java.util.List;

public interface UserService {

    List<UserDto> findAll();

    UserDetailsDto findById(Long id);

    UserDto create(RegisterRequest request);

    UserDto update(Long id, RegisterRequest request);

    void delete(Long id);

    UserDto findByEmail(String email);

    void addPostToUser(Long id, PostDto request);
}

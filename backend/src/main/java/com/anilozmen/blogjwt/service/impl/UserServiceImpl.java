package com.anilozmen.blogjwt.service.impl;

import com.anilozmen.blogjwt.entity.Post;
import com.anilozmen.blogjwt.entity.Role;
import com.anilozmen.blogjwt.entity.User;
import com.anilozmen.blogjwt.entity.dto.request.RegisterRequest;
import com.anilozmen.blogjwt.entity.dto.response.PostDto;
import com.anilozmen.blogjwt.entity.dto.response.UserDetailsDto;
import com.anilozmen.blogjwt.entity.dto.response.UserDto;
import com.anilozmen.blogjwt.exception.CustomDataNotFoundException;
import com.anilozmen.blogjwt.repository.UserRepository;
import com.anilozmen.blogjwt.service.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public List<UserDto> findAll() {
        return userRepository.findAll()
                .stream()
                .map(u -> modelMapper.map(u, UserDto.class))
                .toList();
    }

    @Override
    public UserDetailsDto findById(Long id) {

        User user = findOrThrowById(id);

        List<PostDto> posts = user.getPosts()
                .stream()
                .map(post -> modelMapper.map(post, PostDto.class))
                .toList();

        user.setPosts(List.of());

        UserDetailsDto userDetailsDto = modelMapper.map(user, UserDetailsDto.class);
        userDetailsDto.setPosts(posts);

        return userDetailsDto;
    }

    @Override
    public UserDto create(RegisterRequest request) {
        User build = buildUser(modelMapper.map(request, User.class));
        User save = userRepository.save(build);
        return modelMapper.map(save, UserDto.class);
    }

    @Override
    public UserDto update(Long id, RegisterRequest request) {
        User user = findOrThrowById(id);

        if (request.getFirstName() != null && !request.getFirstName().equals(user.getFirstName()))
            user.setFirstName(request.getFirstName());

        if (request.getLastName() != null && !request.getLastName().equals(user.getLastName()))
            user.setLastName(request.getLastName());

        if (request.getEmail() != null && !request.getEmail().equals(user.getEmail()))
            user.setEmail(request.getEmail());

        if (request.getPassword() != null)
            user.setPassword(passwordEncoder.encode(request.getPassword()));

        User update = userRepository.save(user);

        return modelMapper.map(update, UserDto.class);
    }

    @Override
    public void delete(Long id) {
        User user = findOrThrowById(id);
        userRepository.delete(user);
    }

    @Override
    public UserDto findByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new CustomDataNotFoundException("User with email [" + email + "] not found")
                );
        return modelMapper.map(user, UserDto.class);
    }

    @Override
    public void addPostToUser(Long id, PostDto request) {
        User user = findOrThrowById(id);
        Post post = modelMapper.map(request, Post.class);
        user.getPosts().add(post);
        userRepository.save(user);
    }

    private User findOrThrowById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(
                        () -> new CustomDataNotFoundException("User with ID [" + id + "] not found")
                );
    }

    private User buildUser(User request) {
        User user = new User();

        if (request.getFirstName() != null)
            user.setFirstName(request.getFirstName());

        if (request.getLastName() != null)
            user.setLastName(request.getLastName());

        if (request.getEmail() != null)
            user.setEmail(request.getEmail());

        if (request.getPassword() != null)
            user.setPassword(passwordEncoder.encode(request.getPassword()));

        user.setRole(Role.USER);

        return user;
    }
}

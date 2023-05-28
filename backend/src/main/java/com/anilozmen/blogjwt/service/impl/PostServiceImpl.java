package com.anilozmen.blogjwt.service.impl;

import com.anilozmen.blogjwt.entity.Comment;
import com.anilozmen.blogjwt.entity.Post;
import com.anilozmen.blogjwt.entity.User;
import com.anilozmen.blogjwt.entity.dto.response.CommentDto;
import com.anilozmen.blogjwt.entity.dto.response.PostDetailsDto;
import com.anilozmen.blogjwt.entity.dto.response.PostDto;
import com.anilozmen.blogjwt.exception.CustomDataNotFoundException;
import com.anilozmen.blogjwt.repository.CommentRepository;
import com.anilozmen.blogjwt.repository.PostRepository;
import com.anilozmen.blogjwt.repository.UserRepository;
import com.anilozmen.blogjwt.service.PostService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Override
    public Map<String, Object> findAll(Pageable pageable) {

        Page<Post> posts = postRepository.findAll(pageable);
        List<PostDto> postDtoList = posts.getContent().stream().map(post -> modelMapper.map(post, PostDto.class)).toList();

        SortedMap<String, Object> response = new TreeMap<>();
        response.put("data", postDtoList);

        SortedMap<String, Number> metaData = new TreeMap<>();

        metaData.put("current_page", posts.getNumber() + 1);
        metaData.put("total_items", posts.getTotalElements());
        metaData.put("total_pages", posts.getTotalPages());

        response.put("meta", metaData);

        return response;
    }

    @Override
    public PostDetailsDto findById(Long id) {
        Post post = findOrThrowById(id);
        return modelMapper.map(post, PostDetailsDto.class);
    }

    @Override
    public PostDto create(PostDto request, Authentication authentication) {

        User user = userRepository.findByEmail(authentication.getName())
                .orElseThrow(() ->
                        new CustomDataNotFoundException(
                                "User with email [" + authentication.getName() + "] not found"
                        )
                );

        Post post = modelMapper.map(request, Post.class);

        post.setUser(user);

        Post save = postRepository.save(post);

        user.getPosts().add(save);

        return modelMapper.map(save, PostDto.class);
    }

    @Override
    public PostDto update(Long id, PostDto request) {
        Post post = findOrThrowById(id);

        if (request.getTitle() != null && !request.getTitle().equals(post.getTitle())) {
            post.setTitle(request.getTitle());
        }

        if (request.getContent() != null && !request.getContent().equals(post.getContent()))
            post.setContent(request.getContent());

        Post update = postRepository.save(post);
        return modelMapper.map(update, PostDto.class);
    }

    @Override
    public void delete(Long id) {
        Post post = findOrThrowById(id);
        postRepository.delete(post);
    }

    @Override
    public CommentDto addCommentToPost(Long id, CommentDto request, Authentication authentication) {
        User user = userRepository.findByEmail(authentication.getName())
                .orElseThrow(() ->
                        new CustomDataNotFoundException(
                                "User with email [" + authentication.getName() + "] not found"
                        )
                );


        Post post = findOrThrowById(id);

        Comment comment = modelMapper.map(request, Comment.class);
        comment.setUser(user);

        Comment saved = commentRepository.save(comment);

        post.getComments().add(saved);
        user.getComments().add(saved);
        postRepository.save(post);

        return modelMapper.map(saved, CommentDto.class);
    }

    private Post findOrThrowById(Long id) {
        return postRepository.findById(id)
                .orElseThrow(() ->
                        new CustomDataNotFoundException("Post with ID [" + id + "] not found")
                );
    }
}

package com.anilozmen.blogjwt.service;

import com.anilozmen.blogjwt.entity.dto.response.CommentDto;
import com.anilozmen.blogjwt.entity.dto.response.PostDetailsDto;
import com.anilozmen.blogjwt.entity.dto.response.PostDto;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;

import java.util.Map;

public interface PostService {
    Map<String, Object> findAll(Pageable pageable);

    PostDetailsDto findById(Long id);

    PostDto create(PostDto request, Authentication authentication);

    PostDto update(Long id, PostDto request);

    void delete(Long id);

    CommentDto addCommentToPost(Long id, CommentDto request, Authentication authentication);
}
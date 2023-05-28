package com.anilozmen.blogjwt.service;

import com.anilozmen.blogjwt.entity.dto.response.CommentDto;

import java.util.List;

public interface CommentService {

    List<CommentDto> findAll();

    CommentDto findById(Long id);

    CommentDto create(CommentDto request);

    CommentDto update(Long id, CommentDto request);

    void delete(Long id);
}


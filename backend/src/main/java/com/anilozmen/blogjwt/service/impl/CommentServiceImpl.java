package com.anilozmen.blogjwt.service.impl;

import com.anilozmen.blogjwt.entity.Comment;
import com.anilozmen.blogjwt.entity.dto.response.CommentDto;
import com.anilozmen.blogjwt.exception.CustomDataNotFoundException;
import com.anilozmen.blogjwt.repository.CommentRepository;
import com.anilozmen.blogjwt.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final ModelMapper modelMapper;

    @Override
    public List<CommentDto> findAll() {
        return commentRepository.findAll()
                .stream()
                .map(c -> modelMapper.map(c, CommentDto.class))
                .toList();
    }

    @Override
    public CommentDto findById(Long id) {
        Comment comment = findOrThrowById(id);
        return modelMapper.map(comment, CommentDto.class);
    }

    @Override
    public CommentDto create(CommentDto request) {
        Comment save = commentRepository.save(modelMapper.map(request, Comment.class));
        return modelMapper.map(save, CommentDto.class);
    }

    @Override
    public CommentDto update(Long id, CommentDto request) {
        Comment comment = findOrThrowById(id);
        comment.setComment(request.getComment());
        Comment update = commentRepository.save(comment);
        return modelMapper.map(update, CommentDto.class);
    }

    @Override
    public void delete(Long id) {
        Comment comment = findOrThrowById(id);
        commentRepository.delete(comment);
    }

    private Comment findOrThrowById(Long id) {
        return commentRepository.findById(id)
                .orElseThrow(() ->
                        new CustomDataNotFoundException("Comment with ID [" + id + "] not found")
                );
    }
}

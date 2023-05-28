package com.anilozmen.blogjwt.controller;

import com.anilozmen.blogjwt.entity.dto.response.CommentDto;
import com.anilozmen.blogjwt.entity.dto.response.PostDetailsDto;
import com.anilozmen.blogjwt.entity.dto.response.PostDto;
import com.anilozmen.blogjwt.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @GetMapping
    public ResponseEntity<Map<String, Object>> findAll(@RequestParam(defaultValue = "1") int page) {
        if (page <= 0) page = 1;
        Pageable pageable = PageRequest.of(--page, 10, Sort.by("createdAt").descending());
        return ResponseEntity.ok(postService.findAll(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostDetailsDto> findById(@PathVariable Long id) {
        return ResponseEntity.ok(postService.findById(id));
    }

    @PostMapping
    public ResponseEntity<PostDto> create(@RequestBody PostDto request, Authentication authentication) {
        return new ResponseEntity<>(postService.create(request, authentication), HttpStatus.CREATED);
    }

    @PostMapping("/{id}/comments")
    public ResponseEntity<CommentDto> addCommentToPost(@PathVariable Long id, @RequestBody CommentDto request, Authentication authentication) {
        return new ResponseEntity<>(postService.addCommentToPost(id, request, authentication), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PostDto> update(@PathVariable Long id, @RequestBody PostDto request) {
        return new ResponseEntity<>(postService.update(id, request), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        postService.delete(id);
        return ResponseEntity.noContent().build();
    }
}

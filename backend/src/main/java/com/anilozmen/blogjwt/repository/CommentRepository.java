package com.anilozmen.blogjwt.repository;

import com.anilozmen.blogjwt.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}

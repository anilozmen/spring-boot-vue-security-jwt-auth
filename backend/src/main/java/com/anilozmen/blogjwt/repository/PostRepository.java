package com.anilozmen.blogjwt.repository;

import com.anilozmen.blogjwt.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}

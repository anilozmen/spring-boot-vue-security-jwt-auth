package com.anilozmen.blogjwt.entity.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Transient;
import lombok.*;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostDto {
    private Long id;
    private String title;
    private String content;

    @Transient
    private String author;

    @Getter(AccessLevel.PRIVATE)
    private UserDto user;

    @JsonProperty("created_at")
    private Instant createdAt;
    @JsonProperty("updated_at")
    private Instant updatedAt;

    public String getAuthor() {
        return user.getFirstName() + " " + user.getLastName();
    }
}

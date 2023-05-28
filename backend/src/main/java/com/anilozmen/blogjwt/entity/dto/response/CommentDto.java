package com.anilozmen.blogjwt.entity.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Transient;
import lombok.*;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentDto {
    private Long id;

    private String comment;

    @Transient
    @JsonProperty("full_name")
    private String fullName;

    @Getter(AccessLevel.NONE)
    private UserDto user;

    @JsonProperty("created_at")
    private Instant createdAt;

    public String getFullName() {
        return user.getFirstName() + " " + user.getLastName();
    }
}

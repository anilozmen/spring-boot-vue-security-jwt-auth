package com.anilozmen.blogjwt.entity.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RefreshToken {
    @JsonProperty("refresh_token")
    private String refreshToken;
}

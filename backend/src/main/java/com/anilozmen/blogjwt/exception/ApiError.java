package com.anilozmen.blogjwt.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApiError {
    private int statusCode;
    private String message;

    public ApiError(String message, int statusCode) {
        this.message = message;
        this.statusCode = statusCode;
    }
}

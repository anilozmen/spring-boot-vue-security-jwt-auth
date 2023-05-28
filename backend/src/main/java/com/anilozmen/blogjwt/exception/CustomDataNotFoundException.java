package com.anilozmen.blogjwt.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class CustomDataNotFoundException extends RuntimeException {
    public CustomDataNotFoundException(String message) {
        super(message);
    }
}

package com.anilozmen.blogjwt.exception;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class DefaultExceptionHandler {

    @ExceptionHandler(CustomDataNotFoundException.class)
    public ResponseEntity<ApiError> handleResourceNotFoundException(
            CustomDataNotFoundException exception
    ) {
        return build(exception, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ApiError> handleDataIntegrityViolationException(
            DataIntegrityViolationException exception
    ) {
        return build(exception, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ApiError> handleRuntimeException(
            RuntimeException exception
    ) {
        return build(exception, HttpStatus.BAD_REQUEST);
    }

    private ResponseEntity<ApiError> build(Exception exception, HttpStatus status) {
        ApiError apiError = new ApiError(exception.getMessage(), status.value());
        return new ResponseEntity<>(apiError, status);
    }
}

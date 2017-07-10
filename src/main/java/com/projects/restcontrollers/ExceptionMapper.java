package com.projects.restcontrollers;

import com.projects.api.response.GenericErrorMessageResponse;
import com.projects.exceptions.MyResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * Gracefully returns the user an error if an error occurs whilst processing
 */
@ControllerAdvice
public class ExceptionMapper extends ResponseEntityExceptionHandler {

    @ExceptionHandler(MyResourceNotFoundException.class)
    public ResponseEntity<GenericErrorMessageResponse> resourceNotFound(final Exception exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                             .contentType(MediaType.APPLICATION_JSON)
                             .body(new GenericErrorMessageResponse(exception.getMessage()));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<GenericErrorMessageResponse> illegalArgument(final Exception exception) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                             .contentType(MediaType.APPLICATION_JSON)
                             .body(new GenericErrorMessageResponse(exception.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<GenericErrorMessageResponse> catchAll(final Exception exception) {
        exception.printStackTrace();

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                             .contentType(MediaType.APPLICATION_JSON)
                             .body(new GenericErrorMessageResponse("Internal server error."));
    }
}
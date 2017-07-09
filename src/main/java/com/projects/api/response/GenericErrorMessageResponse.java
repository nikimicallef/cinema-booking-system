package com.projects.api.response;

import org.springframework.web.bind.annotation.RestController;

/**
 * Generic response for a {@link RestController} failure
 */
public class GenericErrorMessageResponse {
    private String error;

    public GenericErrorMessageResponse(final String error) {
        this.error = error;
    }

    public String getError() {
        return error;
    }
}

package com.projects.exceptions;

/**
 * Custom exception to be used when the provided URI Parameter is not found
 */
public class MyResourceNotFoundException extends RuntimeException {
    public MyResourceNotFoundException() {
        super();
    }

    public MyResourceNotFoundException(final String message) {
        super(message);
    }

    public MyResourceNotFoundException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public MyResourceNotFoundException(final Throwable cause) {
        super(cause);
    }

    protected MyResourceNotFoundException(final String message, final Throwable cause, final boolean enableSuppression, final boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

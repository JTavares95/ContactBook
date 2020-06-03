package org.example.exception;

/**
 * Exception to throw when a resource can't be found
 */

public class ResourceNotFoundException extends Exception {

    public ResourceNotFoundException(String msg) {
        super(msg);
    }

    public ResourceNotFoundException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public ResourceNotFoundException(Throwable cause) {
        super(cause);
    }
}

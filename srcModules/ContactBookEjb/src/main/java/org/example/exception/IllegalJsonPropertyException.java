package org.example.exception;

/**
 * Exception to throw when the given information is invalid
 */
public class IllegalJsonPropertyException extends Exception {

    public IllegalJsonPropertyException(String msg) {
        super(msg);
    }

    public IllegalJsonPropertyException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public IllegalJsonPropertyException(Throwable cause) {
        super(cause);
    }

}

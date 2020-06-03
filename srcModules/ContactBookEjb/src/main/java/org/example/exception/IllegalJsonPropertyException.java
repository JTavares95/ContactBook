package org.example.exception;

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

package org.example.exception.handler;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import org.example.exception.IllegalJsonPropertyException;
import org.example.exception.ResourceNotFoundException;
import org.example.model.Error;

public abstract class BasicExceptionHandler {

    protected BasicExceptionHandler() {

    }

    public abstract Error buildDto(Exception exception);

    public Error initDto(Exception exception) {
        Error error = new Error();
        addStackTrace(error, exception);
        return error;
    }

    private void addStackTrace(Error error, Exception exception) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(byteArrayOutputStream);
        exception.printStackTrace(printStream);
        error.setStackTrace(byteArrayOutputStream.toString());
    }

    public static Error mapError(Exception ex) {
        BasicExceptionHandler handler;
        if(ex instanceof ResourceNotFoundException) {
            handler = new ResourceNotFoundExceptionHandler();
        } else if(ex instanceof IllegalJsonPropertyException) {
            handler = new IllegalJsonPropertyExceptionHandler();
        } else if(ex instanceof IllegalArgumentException) {
            handler = new IllegalArgumentExceptionHandler();
        } else {
            handler = new ExceptionHandler();
        }
        return handler.buildDto(ex);
    }


}

package org.example.exception.handler;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import org.example.exception.IllegalJsonPropertyException;
import org.example.model.Error;

@Provider
public class IllegalJsonPropertyExceptionHandler extends BasicExceptionHandler implements ExceptionMapper<IllegalJsonPropertyException> {

    @Override
    public Response toResponse(IllegalJsonPropertyException exception) {
        return Response.status(Status.BAD_REQUEST).entity(buildDto(exception)).build();
    }

    @Override
    public Error buildDto(Exception exception) {
        IllegalJsonPropertyException ex = (IllegalJsonPropertyException) exception;
        Error error = initDto(ex);
        error.setMessage(exception.getMessage());
        return error;
    }
}

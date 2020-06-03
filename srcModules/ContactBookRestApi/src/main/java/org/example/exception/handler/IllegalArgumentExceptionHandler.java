package org.example.exception.handler;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import org.example.model.Error;

@Provider
public class IllegalArgumentExceptionHandler extends BasicExceptionHandler implements ExceptionMapper<IllegalArgumentException> {

    @Override
    public Response toResponse(IllegalArgumentException exception) {
        return Response.status(Status.BAD_REQUEST).entity(buildDto(exception)).build();
    }

    @Override
    public Error buildDto(Exception exception) {
        IllegalArgumentException ex = (IllegalArgumentException) exception;
        Error error = initDto(ex);
        error.setMessage(exception.getMessage());
        return error;
    }
}

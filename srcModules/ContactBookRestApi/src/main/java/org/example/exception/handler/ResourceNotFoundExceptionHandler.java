package org.example.exception.handler;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import org.example.exception.ResourceNotFoundException;
import org.example.model.Error;

@Provider
public class ResourceNotFoundExceptionHandler extends BasicExceptionHandler implements ExceptionMapper<ResourceNotFoundException> {

    @Override
    public Response toResponse(ResourceNotFoundException exception) {
        return Response.status(Status.NOT_FOUND).entity(buildDto(exception)).build();
    }

    @Override
    public Error buildDto(Exception exception) {
        ResourceNotFoundException ex = (ResourceNotFoundException) exception;
        Error error = initDto(ex);
        error.setMessage(exception.getMessage());
        return error;
    }
}

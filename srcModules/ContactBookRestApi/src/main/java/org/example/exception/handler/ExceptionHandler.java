package org.example.exception.handler;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.ForbiddenException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import org.apache.http.HttpStatus;
import org.example.model.Error;

@Provider
public class ExceptionHandler extends BasicExceptionHandler implements ExceptionMapper<Exception> {

    @Override
    public Response toResponse(Exception exception) {
        int status = HttpStatus.SC_INTERNAL_SERVER_ERROR;
        if(exception instanceof BadRequestException) {
            status = HttpStatus.SC_BAD_REQUEST;
        } else if( exception instanceof ForbiddenException) {
            status = HttpStatus.SC_FORBIDDEN;
        }
        return Response.status(status).entity(buildDto(exception)).build();
    }

    @Override
    public Error buildDto(Exception exception) {
        Error error = initDto(exception);
        error.setMessage(exception.getMessage());
        return error;
    }
}

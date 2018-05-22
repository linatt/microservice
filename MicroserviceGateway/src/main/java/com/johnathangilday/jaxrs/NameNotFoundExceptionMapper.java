package com.johnathangilday.jaxrs;

import com.johnathangilday.NameNotFoundException;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 * Maps custom exceptions which do not extend {@link javax.ws.rs.WebApplicationException}
 */
@Provider
public class NameNotFoundExceptionMapper implements ExceptionMapper<NameNotFoundException> {

    @Override
    public Response toResponse(final NameNotFoundException e) {
        return Response
                .status(Response.Status.NOT_FOUND)
                .type(MediaType.TEXT_PLAIN)
                .entity("There exists no greeting with the id " + e.id)
                .build();
    }
}

package soa.collectionservice.collectionservice.controller.exception;

import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import soa.collectionservice.collectionservice.exception.InvalidQueryParamException;
import soa.collectionservice.collectionservice.model.response.ErrorResponse;

@Provider
@Produces(MediaType.APPLICATION_XML)
public class OtherErrorHandler implements ExceptionMapper<InvalidQueryParamException> {

    @Override
    public Response toResponse(InvalidQueryParamException e) {
        return Response.status(Response.Status.NOT_FOUND)
                .entity(ErrorResponse.builder()
                        .message(e.getError().getMessage())
                        .time(e.getError().getTime())
                        .build())
                .build();
    }
}

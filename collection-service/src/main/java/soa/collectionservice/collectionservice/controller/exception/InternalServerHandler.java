package soa.collectionservice.collectionservice.controller.exception;

import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import soa.collectionservice.collectionservice.exception.InternalServerException;
import soa.collectionservice.collectionservice.model.response.ErrorResponse;

@Provider
@Produces(MediaType.APPLICATION_XML)
public class InternalServerHandler implements ExceptionMapper<InternalServerException> {

    @Override
    public Response toResponse(InternalServerException e) {
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity(ErrorResponse.builder()
                        .message(e.getError().getMessage())
                        .time(e.getError().getTime())
                        .build())
                .build();
    }
}

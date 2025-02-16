package soa.collectionservice.collectionservice.controller.exception;

import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import soa.collectionservice.collectionservice.exception.InvalidInputException;
import soa.collectionservice.collectionservice.model.response.InvalidParamsResponse;

@Provider
@Produces(MediaType.APPLICATION_XML)
public class InvalidBodyParamHandler implements ExceptionMapper<InvalidInputException> {

    @Override
    public Response toResponse(InvalidInputException e) {
        return Response.status(Response.Status.fromStatusCode(422))
                .entity(InvalidParamsResponse.builder()
                        .invalidParams(e.getInvalidParams())
                        .error(e.getError())
                        .build())
                .build();
    }
}

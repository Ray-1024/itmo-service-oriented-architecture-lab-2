package soa.collectionservice.collectionservice;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/routes")
public class RouteResource {
    @GET
    @Consumes(MediaType.APPLICATION_XML)
    @Produces({MediaType.APPLICATION_XML})
    public Response hello() {
        return Response.ok("Hello World!").build();
    }
}
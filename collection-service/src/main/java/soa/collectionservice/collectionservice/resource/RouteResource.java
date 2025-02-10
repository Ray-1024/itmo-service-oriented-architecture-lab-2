package soa.collectionservice.collectionservice.resource;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import soa.collectionservice.collectionservice.exception.InvalidQueryParamException;
import soa.collectionservice.collectionservice.model.dto.RouteDto;
import soa.collectionservice.collectionservice.service.RouteService;

import java.util.List;

@Path("/routes")
@Consumes({MediaType.APPLICATION_XML})
@Produces({MediaType.APPLICATION_XML})
public class RouteResource {

    @Inject
    private RouteService routeService;

    @GET
    public Response getAll(@QueryParam("size") @DefaultValue(value = "5") int size,
                           @QueryParam("page") @DefaultValue(value = "1") int page,
                           @QueryParam("sort") String sort,
                           @QueryParam("filter") String filter) {
        if (size < 1 || size > 100) throw new InvalidQueryParamException("Page size must be between 1 and 100");
        if (page < 1) throw new InvalidQueryParamException("Page number must be greater than 0");

        List<RouteDto> routes = routeService.getAllRoutes(size, page, sort, filter);
        return null;
    }

    @GET
    @Path("/{routeId}")
    public Response getRoute() {
        return null;

    }

    @PUT
    @Path("/{routeId}")
    public Response modifyRoute() {
        return null;

    }

    @DELETE
    @Path("/{routeId}")
    public Response deleteRoute() {
        return null;

    }

    @POST
    public Response createRoute() {
        return null;
    }

    @GET
    @Path("/name-groups-info")
    public Response getGroupsInfo() {
        return null;
    }

    @GET
    @Path("/with-distance-count")
    public Response getEqualDistanceRoutesCount(int distance) {
        return null;
    }
}
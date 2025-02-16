package soa.collectionservice.collectionservice.controller;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import soa.collectionservice.collectionservice.exception.InvalidQueryParamException;
import soa.collectionservice.collectionservice.model.dto.ErrorDto;
import soa.collectionservice.collectionservice.model.dto.InvalidParamDto;
import soa.collectionservice.collectionservice.model.dto.RouteDto;
import soa.collectionservice.collectionservice.model.response.CountResponse;
import soa.collectionservice.collectionservice.model.response.GroupsInfoResponse;
import soa.collectionservice.collectionservice.model.response.RouteCollectionResponse;
import soa.collectionservice.collectionservice.service.RouteService;

import java.time.Instant;
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
                           @QueryParam("sort") @DefaultValue(value = "") String sort,
                           @QueryParam("filter") @DefaultValue(value = "") String filter) {
        return Response.ok(RouteCollectionResponse.builder()
                        .routes(routeService.getAllRoutes(page, size, sort, filter))
                        .currentPageNumber(page)
                        .build())
                .build();
    }

    @POST
    public Response createRoute(RouteDto routeDto) {
        return Response.ok(routeService.createRoute(routeDto)).build();
    }

    @GET
    @Path("/{routeId}")
    public Response getRoute(@PathParam("routeId") long routeId) {
        return Response.ok(routeService.getRoute(routeId)).build();
    }

    @PUT
    @Path("/{routeId}")
    public Response modifyRoute(@PathParam("routeId") long routeId,
                                RouteDto routeDto) {
        if (routeId != routeDto.getId()) throw InvalidQueryParamException.builder()
                .invalidParams(List.of(
                        InvalidParamDto.builder()
                                .paramName("routeId")
                                .message("routeId are not equal to route.id from body")
                                .build()
                ))
                .error(ErrorDto.builder().message("Different id")
                        .time(Instant.now())
                        .build())
                .build();
        return Response.ok(routeService.updateRoute(routeDto)).build();
    }

    @DELETE
    @Path("/{routeId}")
    public Response deleteRoute(@PathParam("routeId") long routeId) {
        routeService.deleteRoute(routeId);
        return Response.noContent().build();
    }

    @GET
    @Path("/name-groups-info")
    public Response getGroupsInfo() {
        return Response.ok(
                GroupsInfoResponse.builder()
                        .groups(routeService.getGroupsInfo())
                        .build()
        ).build();
    }

    @GET
    @Path("/with-distance-count")
    public Response getEqualDistanceRoutesCount(@QueryParam("distance") float distance) {
        return Response.ok(
                CountResponse.builder()
                        .count((int) routeService.getEqualDistanceRoutesCount(distance))
                        .build()
        ).build();
    }
}
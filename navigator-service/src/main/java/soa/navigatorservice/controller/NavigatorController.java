package soa.navigatorservice.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import soa.navigatorservice.client.RouteCollectionClient;
import soa.navigatorservice.exception.InvalidInputException;
import soa.navigatorservice.model.dto.ErrorDto;
import soa.navigatorservice.model.dto.InvalidParamDto;
import soa.navigatorservice.model.dto.LocationDto;
import soa.navigatorservice.model.dto.RouteDto;
import soa.navigatorservice.model.entity.LocationEntity;
import soa.navigatorservice.model.request.CreateRouteRequest;
import soa.navigatorservice.model.response.CreateRouteResponse;
import soa.navigatorservice.model.response.GetRoutesResponse;
import soa.navigatorservice.repository.LocationRepository;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@AllArgsConstructor
@RestController
public class NavigatorController {
    private final RouteCollectionClient routeCollectionClient;
    private final LocationRepository locationRepository;

    @GetMapping(value = "/api/v1/navigator/routes/{nameFrom}/{nameTo}/{orderBy}",
            consumes = MediaType.APPLICATION_XML_VALUE,
            produces = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<GetRoutesResponse> getRoutes(@PathVariable String nameFrom,
                                                       @PathVariable String nameTo,
                                                       @PathVariable String orderBy) {
        final ArrayList<RouteDto> routes = new ArrayList<>();
        for (int pageNumber = 1; ; ++pageNumber) {
            List<RouteDto> page = routeCollectionClient.getAllRoutes(100, pageNumber, orderBy, null);
            if (Objects.isNull(page) || page.isEmpty()) break;
            routes.addAll(page.stream().filter(routeDto ->
                    Objects.equals(Objects.isNull(routeDto.getFrom()) ? null : routeDto.getFrom().getName(), nameFrom) &&
                            Objects.equals(routeDto.getTo().getName(), nameTo)).collect(Collectors.toList()));
        }
        return ResponseEntity.ok(
                GetRoutesResponse.builder()
                        .routes(routes)
                        .build());
    }

    @PostMapping(value = "/api/v1/navigator/route/add/{name-from}/{name-to}/{distance}",
            consumes = MediaType.APPLICATION_XML_VALUE,
            produces = MediaType.APPLICATION_XML_VALUE)
    public CreateRouteResponse addRoute(
            @PathVariable("name-from") String nameFrom,
            @PathVariable("name-to") String nameTo,
            @PathVariable float distance,
            @RequestBody CreateRouteRequest createRouteRequest
    ) {
        LocationEntity fromLocation = locationRepository.findByName(nameFrom).orElseThrow(() ->
                InvalidInputException.builder()
                        .invalidParams(List.of(InvalidParamDto.builder()
                                .paramName("name-from")
                                .message("Location with name " + nameFrom + "doesn't exists")
                                .build()
                        ))
                        .error(ErrorDto.builder()
                                .message("Can't create route with unreal location")
                                .time(Instant.now())
                                .build())
                        .build());
        LocationEntity toLocation = locationRepository.findByName(nameTo).orElseThrow(() ->
                InvalidInputException.builder()
                        .invalidParams(List.of(InvalidParamDto.builder()
                                .paramName("name-to")
                                .message("Location with name " + nameTo + "doesn't exists")
                                .build()
                        ))
                        .error(ErrorDto.builder()
                                .message("Can't create route with unreal location")
                                .time(Instant.now())
                                .build())
                        .build());


        return CreateRouteResponse.builder()
                .route(routeCollectionClient.createRoute(RouteDto.builder()
                        .coordinates(createRouteRequest.getCoordinates())
                        .name(createRouteRequest.getName())
                        .from(LocationDto.builder()
                                .x(fromLocation.getX())
                                .y(fromLocation.getY())
                                .z(fromLocation.getZ())
                                .name(fromLocation.getName())
                                .build())
                        .to(LocationDto.builder()
                                .x(toLocation.getX())
                                .y(toLocation.getY())
                                .z(toLocation.getZ())
                                .name(toLocation.getName())
                                .build())
                        .creationDate(new Date())
                        .distance((int) distance)
                        .build()
                ))
                .build();
    }
}

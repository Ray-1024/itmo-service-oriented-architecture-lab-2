package soa.collectionservice.collectionservice.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import soa.collectionservice.collectionservice.exception.InvalidQueryParamException;
import soa.collectionservice.collectionservice.model.dto.ErrorDto;
import soa.collectionservice.collectionservice.model.dto.GroupInfoDto;
import soa.collectionservice.collectionservice.model.dto.InvalidParamDto;
import soa.collectionservice.collectionservice.model.dto.RouteDto;
import soa.collectionservice.collectionservice.repository.RouteRepository;

import java.time.Instant;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@ApplicationScoped
public class RouteService {

    @Inject
    private RouteRepository routeRepository;

    private boolean isRouteField(String fieldName) {
        return Set.of(
                "id", "name", "coordinates.x", "coordinates.y", "creationDate",
                "from.x", "from.y", "from.z", "from.name",
                "to.x", "to.y", "to.z", "to.name",
                "distance"
        ).contains(fieldName);
    }

    private void validateSorting(String sorting) {
        if (Objects.isNull(sorting) || sorting.isEmpty()) return;
        if (isRouteField(sorting) || (sorting.charAt(0) == '<' && isRouteField(sorting.substring(1)))) return;
        throw InvalidQueryParamException.builder()
                .invalidParams(List.of(InvalidParamDto.builder()
                        .paramName("sort")
                        .message("sort must be '' or '{fieldName}' or '<{fieldName}'")
                        .build()))
                .error(ErrorDto.builder()
                        .message("Wrong query parameter")
                        .time(Instant.now())
                        .build())
                .build();
    }

    private void validateFiltering(String filtering) {
        if (filtering == null || filtering.isEmpty()) return;
        String[] split = filtering.split("<|>|<=|>=|=|!=");
        if (split.length == 2 && isRouteField(split[0])) return;
        throw InvalidQueryParamException.builder()
                .invalidParams(List.of(InvalidParamDto.builder()
                        .paramName("filter")
                        .message("filter must be {fieldName} =/!=/</>/<=/>= {value}")
                        .build()))
                .error(ErrorDto.builder()
                        .message("Wrong query parameter")
                        .time(Instant.now())
                        .build())
                .build();
    }

    public List<RouteDto> getAllRoutes(int page, int size, String sort, String filter) {
        validateSorting(sort);
        validateFiltering(filter);

        boolean ascending = true;
        String sortBy = null;
        String filterPredicate = null;
        String filterBy = null;
        String filterValue = null;

        if (Objects.nonNull(sort) && !sort.isEmpty()) {
            ascending = !sort.startsWith("<");
            if (ascending) sortBy = sort;
            else sortBy = sort.substring(1);
        }

        if (Objects.nonNull(filter) && !filter.isEmpty()) {
            String[] split = filter.split("<|>|<=|>=|=|!=");
            filterBy = split[0];
            filterValue = split[1];
            filterPredicate = filter.substring(filterBy.length(), filter.length() - filterValue.length());
        }

        return routeRepository.getAll(
                page, size, sortBy, ascending, filterBy, filterPredicate, filterValue
        ).stream().map(RouteDto::fromEntity).collect(Collectors.toList());
    }

    public RouteDto getRoute(long routeId) {
        return RouteDto.fromEntity(routeRepository.getById(routeId));
    }

    public RouteDto createRoute(RouteDto route) {
        return RouteDto.fromEntity(routeRepository.create(route));
    }

    public RouteDto updateRoute(RouteDto route) {
        return RouteDto.fromEntity(routeRepository.update(route));
    }

    public void deleteRoute(long routeId) {
        routeRepository.deleteById(routeId);
    }

    public List<GroupInfoDto> getGroupsInfo() {
        return routeRepository.getGroupsInfo();
    }

    public long getEqualDistanceRoutesCount(float distance) {
        return routeRepository.getEqualDistanceRoutesCount((int) distance);
    }
}

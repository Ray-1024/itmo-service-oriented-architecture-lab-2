package soa.collectionservice.collectionservice.service;

import soa.collectionservice.collectionservice.exception.InvalidQueryParamException;
import soa.collectionservice.collectionservice.model.dto.GroupInfoDto;
import soa.collectionservice.collectionservice.model.dto.RouteDto;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class RouteService {

    private enum RouteFields {
        ID("id", Long.class),
        NAME("name", String.class),
        COORDINATES_X("coordinates.x", Long.class),
        COORDINATES_Y("coordinates.y", Long.class),
        CREATION_DATE("creationDate", Date.class),
        FROM_X("from.x", Integer.class),
        FROM_Y("from.y", Integer.class),
        FROM_Z("from.z", Double.class),
        FROM_NAME("from.name", String.class),
        TO_X("to.x", Integer.class),
        TO_Y("to.y", Integer.class),
        TO_Z("to.z", Double.class),
        TO_NAME("to.name", String.class),
        DISTANCE("distance", Integer.class);

        private final String fieldName;
        private final Class<?> fieldType;

        RouteFields(String fieldName, Class<?> fieldType) {
            this.fieldName = fieldName;
            this.fieldType = fieldType;
        }
    }

    private boolean isRouteField(String fieldName) {
        return Arrays.stream(RouteFields.values()).anyMatch(routeField -> routeField.fieldName.equals(fieldName));
    }

    private void isSortStringValid(String sortString) {
        if (Objects.isNull(sortString)) throw new InvalidQueryParamException("sort parameter is null");
        if (isRouteField(sortString)) return true;
        return sortString.length() > 1 && sortString.charAt(0) == '<' && isRouteField(sortString.substring(1));
    }

    private void validateFilter(String filterString) {
        if (Objects.isNull(filterString)) return;
        RouteFields field = Arrays.stream(RouteFields.values()).filter(fieldName -> filterString.startsWith(fieldName.fieldName)).findFirst().orElse(null);
        if ()
    }

    public List<RouteDto> getAllRoutes(int page, int size, String sort, String filter) {

    }

    public RouteDto getRoute(long routeId) {

    }

    public RouteDto createRoute(RouteDto route) {

    }

    public RouteDto updateRoute(RouteDto route) {

    }

    public void deleteRoute(long routeId) {

    }

    public GroupInfoDto getGroupsInfo() {

    }

    public long getEqualDistanceRoutesCount(int distance) {

    }
}

package soa.navigatorservice.client;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import soa.navigatorservice.exception.CollectionServiceAccessException;
import soa.navigatorservice.exception.InternalServerException;
import soa.navigatorservice.exception.InvalidQueryParamException;
import soa.navigatorservice.exception.OtherErrorException;
import soa.navigatorservice.model.dto.ErrorDto;
import soa.navigatorservice.model.dto.RouteDto;
import soa.navigatorservice.model.request.RouteRequest;
import soa.navigatorservice.model.response.ErrorResponse;
import soa.navigatorservice.model.response.InvalidParamsResponse;
import soa.navigatorservice.model.response.RouteCollectionResponse;
import soa.navigatorservice.model.response.RouteResponse;

import java.time.Instant;
import java.util.List;
import java.util.Objects;

@Component
public class RouteCollectionClient {
    private final static String COLLECTION_SERVICE_BASE_URL = "http://localhost:22400/api/v1/routes";


    public List<RouteDto> getAllRoutes(int pageSize, int pageNumber, String sort, String filter) {
        final RestTemplate restTemplate = new RestTemplate();
        try {
            RouteCollectionResponse response = restTemplate.getForObject(
                    COLLECTION_SERVICE_BASE_URL +
                            "?pageSize=" +
                            pageSize +
                            "&pageNumber=" +
                            pageNumber +
                            "&sort=" +
                            sort +
                            "&filter=" +
                            filter,
                    RouteCollectionResponse.class
            );
            return response.getRoutes();
        } catch (HttpClientErrorException e) {
            if (e.getStatusCode() == HttpStatus.BAD_REQUEST) {
                InvalidParamsResponse response = e.getResponseBodyAs(InvalidParamsResponse.class);
                if (Objects.isNull(response)) {
                    throw CollectionServiceAccessException.builder()
                            .error(ErrorDto.builder()
                                    .message("Broken collection service response format")
                                    .time(Instant.now())
                                    .build())
                            .build();
                }
                throw InvalidQueryParamException.builder()
                        .invalidParams(response.getInvalidParams())
                        .error(response.getError())
                        .build();
            } else if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
                ErrorResponse response = e.getResponseBodyAs(ErrorResponse.class);
                if (Objects.isNull(response)) {
                    throw CollectionServiceAccessException.builder()
                            .error(ErrorDto.builder()
                                    .message("Broken collection service response format")
                                    .time(Instant.now())
                                    .build())
                            .build();
                }
                throw OtherErrorException.builder()
                        .error(ErrorDto.builder()
                                .message(response.getMessage())
                                .time(response.getTime())
                                .build())
                        .build();
            } else {
                throw CollectionServiceAccessException.builder()
                        .error(ErrorDto.builder()
                                .message("Unknown response status: " + e.getStatusCode())
                                .time(Instant.now())
                                .build())
                        .build();
            }
        } catch (HttpServerErrorException e) {
            if (e.getStatusCode() == HttpStatus.INTERNAL_SERVER_ERROR) {
                ErrorResponse response = e.getResponseBodyAs(ErrorResponse.class);
                if (Objects.isNull(response)) {
                    throw CollectionServiceAccessException.builder()
                            .error(ErrorDto.builder()
                                    .message("Broken collection service response format")
                                    .time(Instant.now())
                                    .build())
                            .build();
                }
                throw InternalServerException.builder()
                        .error(ErrorDto.builder()
                                .message(response.getMessage())
                                .time(response.getTime())
                                .build())
                        .build();
            } else {
                throw CollectionServiceAccessException.builder()
                        .error(ErrorDto.builder()
                                .message("Unknown response status: " + e.getStatusCode())
                                .time(Instant.now())
                                .build())
                        .build();
            }
        } catch (Exception e) {
            throw CollectionServiceAccessException.builder()
                    .error(ErrorDto.builder()
                            .message("Can't call other service to get routes")
                            .time(Instant.now())
                            .build())
                    .build();
        }
    }

    public RouteDto createRoute(RouteDto routeDto) {
        final RestTemplate restTemplate = new RestTemplate();
        try {
            RouteResponse response = restTemplate.postForObject(
                    COLLECTION_SERVICE_BASE_URL,
                    RouteRequest.builder()
                            .route(routeDto)
                            .build(),
                    RouteResponse.class
            );
            return response.getRoute();
        } catch (HttpClientErrorException e) {
            if (e.getStatusCode() == HttpStatus.UNPROCESSABLE_ENTITY) {
                InvalidParamsResponse response = e.getResponseBodyAs(InvalidParamsResponse.class);
                if (Objects.isNull(response)) {
                    throw CollectionServiceAccessException.builder()
                            .error(ErrorDto.builder()
                                    .message("Broken collection service response format")
                                    .time(Instant.now())
                                    .build())
                            .build();
                }
                throw InvalidQueryParamException.builder()
                        .invalidParams(response.getInvalidParams())
                        .error(response.getError())
                        .build();
            } else {
                throw CollectionServiceAccessException.builder()
                        .error(ErrorDto.builder()
                                .message("Unknown response status: " + e.getStatusCode())
                                .time(Instant.now())
                                .build())
                        .build();
            }
        } catch (HttpServerErrorException e) {
            if (e.getStatusCode() == HttpStatus.INTERNAL_SERVER_ERROR) {
                ErrorResponse response = e.getResponseBodyAs(ErrorResponse.class);
                if (Objects.isNull(response)) {
                    throw CollectionServiceAccessException.builder()
                            .error(ErrorDto.builder()
                                    .message("Broken collection service response format")
                                    .time(Instant.now())
                                    .build())
                            .build();
                }
                throw InternalServerException.builder()
                        .error(ErrorDto.builder()
                                .message(response.getMessage())
                                .time(response.getTime())
                                .build())
                        .build();
            } else {
                throw CollectionServiceAccessException.builder()
                        .error(ErrorDto.builder()
                                .message("Unknown response status: " + e.getStatusCode())
                                .time(Instant.now())
                                .build())
                        .build();
            }
        } catch (Exception e) {
            throw CollectionServiceAccessException.builder()
                    .error(ErrorDto.builder()
                            .message("Can't call other service to get routes")
                            .time(Instant.now())
                            .build())
                    .build();
        }
    }
}

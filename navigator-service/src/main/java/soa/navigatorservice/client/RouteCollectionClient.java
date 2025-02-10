package soa.navigatorservice.client;

import org.springframework.stereotype.Component;
import soa.navigatorservice.model.dto.RouteDto;

import java.util.List;

@Component
public class RouteCollectionClient {
    public List<RouteDto> getAllRoutes(int pageSize, int pageNumber, String sort, String filter) {
        return null;
    }

    public RouteDto createRoute(RouteDto RouteDto) {
        return null;
    }
}

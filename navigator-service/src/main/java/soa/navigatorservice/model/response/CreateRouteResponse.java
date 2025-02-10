package soa.navigatorservice.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import soa.navigatorservice.model.dto.RouteDto;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class CreateRouteResponse {
    private RouteDto route;
}

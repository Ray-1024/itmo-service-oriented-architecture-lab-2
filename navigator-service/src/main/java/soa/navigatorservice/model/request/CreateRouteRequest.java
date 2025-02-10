package soa.navigatorservice.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import soa.navigatorservice.model.dto.CoordinatesDto;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class CreateRouteRequest {
    private CoordinatesDto coordinates;
    private String name;
}

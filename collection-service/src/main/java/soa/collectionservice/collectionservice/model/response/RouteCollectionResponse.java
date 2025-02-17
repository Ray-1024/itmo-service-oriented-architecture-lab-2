package soa.collectionservice.collectionservice.model.response;

import jakarta.xml.bind.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import soa.collectionservice.collectionservice.model.dto.RouteDto;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@XmlRootElement(name = "response")
@XmlAccessorType(XmlAccessType.FIELD)
public class RouteCollectionResponse {
    @XmlElementWrapper(name = "routes")
    @XmlElement(name = "route")
    private List<RouteDto> routes;
    @XmlElement
    private int currentPageNumber;
}

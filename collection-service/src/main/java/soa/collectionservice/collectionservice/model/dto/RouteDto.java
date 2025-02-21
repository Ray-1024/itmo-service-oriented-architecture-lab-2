package soa.collectionservice.collectionservice.model.dto;

import jakarta.json.bind.annotation.JsonbDateFormat;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import soa.collectionservice.collectionservice.model.entity.RouteEntity;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@XmlRootElement(name = "route")
@XmlAccessorType(XmlAccessType.FIELD)
public class RouteDto {
    @XmlElement
    private Long id; //Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    @XmlElement(required = true)
    private String name; //Поле не может быть null, Строка не может быть пустой
    @XmlElement(required = true)
    private CoordinatesDto coordinates; //Поле не может быть null
    @XmlElement
    private String creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    @XmlElement
    private LocationDto from; //Поле может быть null
    @XmlElement(required = true)
    private LocationDto to; //Поле не может быть null
    @XmlElement(required = true)
    private int distance; //Значение поля должно быть больше 1

    public static RouteDto fromEntity(RouteEntity routeEntity) {
        if (routeEntity == null) return null;
        return RouteDto.builder()
                .id(routeEntity.getId() <= 0 ? null : routeEntity.getId())
                .name(routeEntity.getName())
                .coordinates(CoordinatesDto.fromEntity(routeEntity.getCoordinates()))
                .creationDate(routeEntity.getCreationDate())
                .from(LocationDto.fromEntity(routeEntity.getFrom()))
                .to(LocationDto.fromEntity(routeEntity.getTo()))
                .distance(routeEntity.getDistance())
                .build();
    }
}

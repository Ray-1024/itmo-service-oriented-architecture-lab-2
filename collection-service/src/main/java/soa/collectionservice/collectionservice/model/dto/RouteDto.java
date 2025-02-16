package soa.collectionservice.collectionservice.model.dto;

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
public class RouteDto {
    private Long id; //Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name; //Поле не может быть null, Строка не может быть пустой
    private CoordinatesDto coordinates; //Поле не может быть null
    private Date creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private LocationDto from; //Поле может быть null
    private LocationDto to; //Поле не может быть null
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

package soa.collectionservice.collectionservice.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import soa.collectionservice.collectionservice.model.entity.LocationEntity;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class LocationDto {
    private int x;
    private int y;
    private double z;
    private String name; //Длина строки не должна быть больше 452, Поле не может быть null

    public static LocationDto fromEntity(LocationEntity locationEntity) {
        if (locationEntity == null) return null;
        return LocationDto.builder()
                .x(locationEntity.getX())
                .y(locationEntity.getY())
                .z(locationEntity.getZ())
                .name(locationEntity.getName())
                .build();
    }
}

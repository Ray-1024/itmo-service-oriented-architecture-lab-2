package soa.collectionservice.collectionservice.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import soa.collectionservice.collectionservice.model.entity.CoordinatesEntity;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class CoordinatesDto {
    private long x; //Максимальное значение поля: 510
    private Long y; //Поле не может быть null

    public static CoordinatesDto fromEntity(CoordinatesEntity coordinatesEntity) {
        if (coordinatesEntity == null) return null;
        return CoordinatesDto.builder()
                .x(coordinatesEntity.getX())
                .y(coordinatesEntity.getY())
                .build();
    }
}

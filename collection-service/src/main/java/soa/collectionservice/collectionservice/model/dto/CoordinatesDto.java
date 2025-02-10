package soa.collectionservice.collectionservice.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class CoordinatesDto {
    private long x; //Максимальное значение поля: 510
    private Long y; //Поле не может быть null
}

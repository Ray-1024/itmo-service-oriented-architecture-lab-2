package soa.collectionservice.collectionservice.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
@Table(name = "routes")
public class RouteEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Min(value = 1)
    private long id; //Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически

    @NotNull
    @NotBlank
    private String name; //Поле не может быть null, Строка не может быть пустой

    @NotNull
    @ManyToOne
    private CoordinatesEntity coordinates; //Поле не может быть null

    @NotNull
    private Date creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически

    @ManyToOne
    private LocationEntity from; //Поле может быть null

    @NotNull
    @ManyToOne
    private LocationEntity to; //Поле не может быть null

    @Min(value = 1)
    private int distance; //Значение поля должно быть больше 1
}

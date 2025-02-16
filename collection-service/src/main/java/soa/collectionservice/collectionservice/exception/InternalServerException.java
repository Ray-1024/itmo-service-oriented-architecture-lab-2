package soa.collectionservice.collectionservice.exception;

import lombok.*;
import soa.collectionservice.collectionservice.model.dto.ErrorDto;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class InternalServerException extends RuntimeException {
    private ErrorDto error;
}

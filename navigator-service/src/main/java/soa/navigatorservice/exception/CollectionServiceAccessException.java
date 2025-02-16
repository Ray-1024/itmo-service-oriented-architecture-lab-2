package soa.navigatorservice.exception;

import lombok.*;
import soa.navigatorservice.model.dto.ErrorDto;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class CollectionServiceAccessException extends RuntimeException {
    private ErrorDto error;
}

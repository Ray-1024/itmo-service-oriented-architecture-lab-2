package soa.navigatorservice.exception;

import lombok.*;
import soa.navigatorservice.model.dto.ErrorDto;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class OtherErrorException extends RuntimeException {
    private ErrorDto error;
}

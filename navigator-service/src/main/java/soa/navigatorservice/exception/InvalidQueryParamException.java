package soa.navigatorservice.exception;

import lombok.*;
import soa.navigatorservice.model.dto.ErrorDto;
import soa.navigatorservice.model.dto.InvalidParamDto;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class InvalidQueryParamException extends RuntimeException {
    private List<InvalidParamDto> invalidParams;
    private ErrorDto error;
}
